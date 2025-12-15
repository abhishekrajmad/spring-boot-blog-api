package com.madcode.blog.repositories;

import com.madcode.blog.domain.PostStatus;
import com.madcode.blog.domain.entities.Category;
import com.madcode.blog.domain.entities.Post;
import com.madcode.blog.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {


    // FETCH SINGLE POST WITH RELATIONS

    @Query("""
        SELECT DISTINCT p FROM Post p
        JOIN FETCH p.author
        JOIN FETCH p.category
        LEFT JOIN FETCH p.tags
        WHERE p.id = :id
    """)
    Optional<Post> findByIdWithRelations(UUID id);


    // FETCH ALL PUBLISHED POSTS

    @Query("""
        SELECT DISTINCT p
        FROM Post p
        JOIN FETCH p.author
        JOIN FETCH p.category
        LEFT JOIN FETCH p.tags
        WHERE p.status = :status
    """)
    List<Post> findAllByStatusWithRelations(PostStatus status);


    // FILTER: CATEGORY + TAG

    @Query("""
        SELECT DISTINCT p FROM Post p
        JOIN FETCH p.author
        JOIN FETCH p.category
        LEFT JOIN FETCH p.tags t
        WHERE p.status = :status
          AND p.category = :category
          AND t = :tag
    """)
    List<Post> findAllByCategoryAndTag(PostStatus status, Category category, Tag tag);


    // FILTER: CATEGORY ONLY

    @Query("""
        SELECT DISTINCT p FROM Post p
        JOIN FETCH p.author
        JOIN FETCH p.category
        LEFT JOIN FETCH p.tags
        WHERE p.status = :status
          AND p.category = :category
    """)
    List<Post> findAllByCategory(PostStatus status, Category category);


    // FILTER: TAG ONLY

    @Query("""
        SELECT DISTINCT p FROM Post p
        JOIN FETCH p.author
        JOIN FETCH p.category
        LEFT JOIN FETCH p.tags t
        WHERE p.status = :status
          AND t = :tag
    """)
    List<Post> findAllByTag(PostStatus status, Tag tag);


    // USER'S DRAFT POSTS

    @Query("""
        SELECT DISTINCT p FROM Post p
        JOIN FETCH p.author
        JOIN FETCH p.category
        LEFT JOIN FETCH p.tags
        WHERE p.status = 'DRAFT'
          AND p.author.id = :authorId
    """)
    List<Post> findAllDraftsByAuthor(UUID authorId);
}
