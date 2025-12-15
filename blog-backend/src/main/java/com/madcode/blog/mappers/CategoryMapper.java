package com.madcode.blog.mappers;

import com.madcode.blog.domain.PostStatus;
import com.madcode.blog.domain.dtos.CategoryDto;
import com.madcode.blog.domain.dtos.CreateCategoryRequest;
import com.madcode.blog.domain.entities.Category;
import com.madcode.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    Category toEntitiy(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts){
        if(posts == null) return 0;
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED
                .equals(post.getStatus()))
                .count();
    }
}
