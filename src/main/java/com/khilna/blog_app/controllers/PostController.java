package com.khilna.blog_app.controllers;

import com.khilna.blog_app.domain.CreatePostRequest;
import com.khilna.blog_app.domain.UpdatePostRequest;
import com.khilna.blog_app.domain.dto.CreatePostRequestDto;
import com.khilna.blog_app.domain.dto.PostDto;
import com.khilna.blog_app.domain.dto.UpdatePostRequestDto;
import com.khilna.blog_app.domain.entities.User;
import com.khilna.blog_app.mappers.PostMapper;
import com.khilna.blog_app.services.PostService;
import com.khilna.blog_app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {

        List<PostDto> posts = postService.getPosts(categoryId, tagId)
                .stream()
                .map(postMapper::toDto)
                .toList();

        return ResponseEntity.ok(posts);

    }


    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        List<PostDto> draftPosts = postService.getDraftPosts(loggedInUser)
                .stream()
                .map(postMapper::toDto)
                .toList();

        return ResponseEntity.ok(draftPosts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") UUID id) {

        PostDto post = postMapper.toDto(postService.getPost(id));
        return ResponseEntity.ok(post);

    }


    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute("userId") UUID userId
    ) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);

        PostDto createdPost = postMapper.toDto(postService.createPost(loggedInUser, createPostRequest));

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable("id") UUID postId,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {

        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        PostDto updatedPost = postMapper.toDto(postService.updatePost(postId, updatePostRequest));

        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


}
