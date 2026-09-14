package com.photographerplatform.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photographerplatform.backend.entity.Post;
import com.photographerplatform.backend.service.PostService;

@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://lensconnect-frontend.vercel.app"
	})
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ========================================
    // CREATE POST
    // ========================================

    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestBody Post post) {

        Post createdPost = postService.createPost(post);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPost);
    }

    // ========================================
    // GET PHOTOGRAPHER POSTS
    // ========================================

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Post>> getPostsByPhotographer(
            @PathVariable Long photographerId) {

        return ResponseEntity.ok(
                postService.getPostsByPhotographer(
                        photographerId
                )
        );
    }

    // ========================================
    // GET POST BY ID
    // ========================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(
            @PathVariable Long id) {

        Post post = postService.getPostById(id);

        if (post == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Post not found");
        }

        return ResponseEntity.ok(post);
    }

    // ========================================
    // UPDATE POST
    // ========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody Post post) {

        Post updatedPost =
                postService.updatePost(id, post);

        if (updatedPost == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Post not found");
        }

        return ResponseEntity.ok(updatedPost);
    }

    // ========================================
    // DELETE POST
    // ========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id) {

        boolean deleted = postService.deletePost(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Post not found");
        }

        return ResponseEntity.ok(
                "Post deleted successfully"
        );
    }
}