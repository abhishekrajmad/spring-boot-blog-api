package com.madcode.blog.services;

import com.madcode.blog.domain.UpdatePostRequest;
import com.madcode.blog.domain.entities.Post;
import com.madcode.blog.domain.entities.User;
import com.madcode.blog.repositories.PostRepository;
import com.madcode.blog.services.implementations.PostServiceImpl;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void shouldThrowExceptionWhenUserIsNotAuthor(){
        UUID postId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        User author = User.builder()
                .id(authorId)
                .email("author@test.com")
                .build();

        Post existingPost = Post.builder()
                .id(postId)
                .author(author)
                .build();


        when(postRepository.findById(postId))
                .thenReturn(Optional.of(existingPost));


        UpdatePostRequest request = UpdatePostRequest.builder()
                .title("Updated title")
                .content("Updated content")
                .build();


        assertThrows(AccessDeniedException.class,
                () -> postService.updatePost(postId, request, otherUserId));

        verify(postRepository, never()).save(any());
    }

















}