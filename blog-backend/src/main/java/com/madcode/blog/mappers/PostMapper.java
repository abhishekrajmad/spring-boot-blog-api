package com.madcode.blog.mappers;

import com.madcode.blog.domain.CreatePostRequest;
import com.madcode.blog.domain.UpdatePostRequest;
import com.madcode.blog.domain.dtos.CreatePostRequestDto;
import com.madcode.blog.domain.dtos.PostDto;
import com.madcode.blog.domain.dtos.UpdatePostRequestDto;
import com.madcode.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);
}
