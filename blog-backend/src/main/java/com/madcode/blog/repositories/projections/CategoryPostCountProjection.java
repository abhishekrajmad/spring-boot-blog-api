package com.madcode.blog.repositories.projections;

import java.util.UUID;

public interface CategoryPostCountProjection {
    UUID getId();
    String getName();
    long getPostCount();

}
