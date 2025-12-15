package com.madcode.blog.services;

import com.madcode.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
