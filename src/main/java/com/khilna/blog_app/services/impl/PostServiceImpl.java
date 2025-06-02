package com.khilna.blog_app.services.impl;

import com.khilna.blog_app.domain.CreatePostRequest;
import com.khilna.blog_app.domain.PostStatus;
import com.khilna.blog_app.domain.UpdatePostRequest;
import com.khilna.blog_app.domain.entities.Category;
import com.khilna.blog_app.domain.entities.Post;
import com.khilna.blog_app.domain.entities.Tag;
import com.khilna.blog_app.domain.entities.User;
import com.khilna.blog_app.repositories.PostRepository;
import com.khilna.blog_app.services.CategoryService;
import com.khilna.blog_app.services.PostService;
import com.khilna.blog_app.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Transactional(readOnly = true)
    @Override
    public List<Post> getPosts(UUID categoryId, UUID tagId) {

        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag);

        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category);
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag);
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Transactional
    @Override
    public Post createPost(User user, CreatePostRequest createPostRequest) {

        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        List<Tag> tags = tagService.getTagsByIds(createPostRequest.getTagIds());
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Transactional
    @Override
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postContent));

        UUID categoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(categoryId)) {
            Category newCategory = categoryService.getCategoryById(categoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        Set<UUID> tagIds = updatePostRequest.getTagIds();

        if (!existingTagIds.equals(tagIds)) {
            List<Tag> newTags = tagService.getTagsByIds(tagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public Post getPost(UUID id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {

        if (content == null || content.isEmpty())
            return 0;

        int wordCount = content.trim().split("\\s+").length;

        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }

}
