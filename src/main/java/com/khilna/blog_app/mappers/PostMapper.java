package com.khilna.blog_app.mappers;

import com.khilna.blog_app.domain.CreatePostRequest;
import com.khilna.blog_app.domain.UpdatePostRequest;
import com.khilna.blog_app.domain.dto.CreatePostRequestDto;
import com.khilna.blog_app.domain.dto.PostDto;
import com.khilna.blog_app.domain.dto.UpdatePostRequestDto;
import com.khilna.blog_app.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);


}
