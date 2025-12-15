package com.madcode.blog.services.implementations;

import com.madcode.blog.domain.CreatePostRequest;
import com.madcode.blog.domain.PostStatus;
import com.madcode.blog.domain.UpdatePostRequest;
import com.madcode.blog.domain.entities.Category;
import com.madcode.blog.domain.entities.Post;
import com.madcode.blog.domain.entities.Tag;
import com.madcode.blog.domain.entities.User;
import com.madcode.blog.repositories.PostRepository;
import com.madcode.blog.services.CategoryService;
import com.madcode.blog.services.PostService;
import com.madcode.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    public Post getPost(UUID id) {
        return postRepository.findByIdWithRelations(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("No post exists with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {

        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);

            return postRepository.findAllByCategoryAndTag(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByCategory(PostStatus.PUBLISHED, category);
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByTag(PostStatus.PUBLISHED, tag);
        }

        return postRepository.findAllByStatusWithRelations(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllDraftsByAuthor(user.getId());
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest request) {

        Post newPost = new Post();
        newPost.setTitle(request.getTitle());
        newPost.setContent(request.getContent());
        newPost.setStatus(request.getStatus());
        newPost.setAuthor(user);

        Category category = categoryService.getCategoryById(request.getCategoryId());
        newPost.setCategory(category);

        List<Tag> tags = tagService.getTagByIds(request.getTagIds());
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest request, UUID userId) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No post exist with id:" + id));

        if (!existingPost.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this post");
        }

        existingPost.setTitle(request.getTitle());
        existingPost.setContent(request.getContent());
        existingPost.setStatus(request.getStatus());

        if (!existingPost.getCategory().getId().equals(request.getCategoryId())) {
            Category newCategory = categoryService.getCategoryById(request.getCategoryId());
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags()
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        if (!existingTagIds.equals(request.getTagIds())) {
            List<Tag> newTags = tagService.getTagByIds(request.getTagIds());
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void deletePost(UUID id, UUID userId) {

        Post post = getPost(id); // already N+1 safe

        if (!post.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this post");
        }

        postRepository.delete(post);
    }
}
