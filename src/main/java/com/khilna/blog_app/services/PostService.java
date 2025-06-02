package com.khilna.blog_app.services;

import com.khilna.blog_app.domain.CreatePostRequest;
import com.khilna.blog_app.domain.UpdatePostRequest;
import com.khilna.blog_app.domain.entities.Post;
import com.khilna.blog_app.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getPosts(UUID categoryId, UUID tagId);

    List<Post> getDraftPosts(User user);

    Post createPost(User user, CreatePostRequest createPostRequest);

    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);

    Post getPost(UUID id);

    void deletePost(UUID id);
}
