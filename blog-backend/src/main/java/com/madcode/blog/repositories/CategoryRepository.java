package com.madcode.blog.repositories;

import com.madcode.blog.domain.entities.Category;
import com.madcode.blog.repositories.projections.CategoryPostCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

//    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.posts")
//    List<Category> findAllWithPostCount();

    @Query("""
    SELECT c.id AS id, c.name AS name, COUNT(p) AS postCount
    FROM Category c
    LEFT JOIN c.posts p
    GROUP BY c.id, c.name
    """)
    List<CategoryPostCountProjection> findAllWithPostCount();

    boolean existsByNameIgnoreCase(String name);

}
