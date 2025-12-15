package com.madcode.blog.services.implementations;

import com.madcode.blog.domain.entities.User;
import com.madcode.blog.repositories.UserRepository;
import com.madcode.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        return userRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found with ID: "+id));
    }
}
