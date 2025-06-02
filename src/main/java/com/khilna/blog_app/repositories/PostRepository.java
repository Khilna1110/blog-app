package com.khilna.blog_app.repositories;

import com.khilna.blog_app.domain.PostStatus;
import com.khilna.blog_app.domain.entities.Category;
import com.khilna.blog_app.domain.entities.Post;
import com.khilna.blog_app.domain.entities.Tag;
import com.khilna.blog_app.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatusAndCategoryAndTagsContaining
            (PostStatus postStatus, Category category, Tag tag);

    List<Post> findAllByStatusAndCategory(PostStatus postStatus, Category category);

    List<Post> findAllByStatusAndTagsContaining(PostStatus postStatus, Tag tag);

    List<Post> findAllByStatus(PostStatus postStatus);

    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
