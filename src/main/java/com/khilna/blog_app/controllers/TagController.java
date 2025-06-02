package com.khilna.blog_app.controllers;

import com.khilna.blog_app.domain.dto.CreateTagsRequest;
import com.khilna.blog_app.domain.dto.TagDto;
import com.khilna.blog_app.domain.entities.Tag;
import com.khilna.blog_app.mappers.TagMapper;
import com.khilna.blog_app.services.TagService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getTags()
                .stream().map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(
            @RequestBody CreateTagsRequest createTagsRequest) {
        List<TagDto> tags = tagService.createTags(createTagsRequest.getNames())
                .stream()
                .map(tagMapper::toDto)
                .toList();

        return new ResponseEntity<>(tags, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
