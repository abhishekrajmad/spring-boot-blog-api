package com.madcode.blog.repositories;

import com.madcode.blog.domain.entities.Tag;
import com.madcode.blog.repositories.projections.TagPostCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("""
    SELECT t.id AS id, t.name AS name, COUNT(p) AS postCount
    FROM Tag t
    LEFT JOIN t.posts p
    GROUP BY t.id, t.name
    """)
    List<TagPostCountProjection> findAllWithPostCount();


    List<Tag> findByNameIn(Set<String> names);
}
