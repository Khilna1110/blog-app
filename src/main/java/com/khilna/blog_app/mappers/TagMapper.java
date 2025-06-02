package com.khilna.blog_app.mappers;

import com.khilna.blog_app.domain.PostStatus;
import com.khilna.blog_app.domain.dto.TagDto;
import com.khilna.blog_app.domain.entities.Post;
import com.khilna.blog_app.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(source = "posts", target = "postCount", qualifiedByName = "calculatePostCount")
    TagDto toDto(Tag tag);

    @Named("calculatePostCount")
    default long calculatePostCount(Set<Post> posts) {
        if (posts == null)
            return 0;

        return posts.stream()
                .filter(post -> post.getStatus() == PostStatus.PUBLISHED)
                .count();
    }
}
