package com.madcode.blog.repositories.projections;

import java.util.UUID;

public interface TagPostCountProjection {
    UUID getId();
    String getName();
    long getPostCount();
}
