package com.madcode.blog.services;

import com.madcode.blog.domain.dtos.RegisterRequest;

public interface RegisterUserService {
    void register(RegisterRequest request);
}
