package com.madcode.blog.services;

import com.madcode.blog.domain.CreatePostRequest;
import com.madcode.blog.domain.UpdatePostRequest;
import com.madcode.blog.domain.entities.Post;
import com.madcode.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest request, UUID userId);
    void deletePost(UUID id, UUID userId);

}
