package com.photographerplatform.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.photographerplatform.backend.entity.Post;
import com.photographerplatform.backend.repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // ========================================
    // CREATE POST / SERVICE
    // ========================================

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // ========================================
    // GET PHOTOGRAPHER POSTS
    // ========================================

    public List<Post> getPostsByPhotographer(Long photographerId) {
        return postRepository.findByPhotographerId(photographerId);
    }

    // ========================================
    // GET POST BY ID
    // ========================================

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    // ========================================
    // UPDATE POST / SERVICE
    // ========================================

    public Post updatePost(Long id, Post updatedPost) {

        Post existingPost = postRepository.findById(id).orElse(null);

        if (existingPost == null) {
            return null;
        }

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setDescription(updatedPost.getDescription());
        existingPost.setServiceType(updatedPost.getServiceType());
        existingPost.setPrice(updatedPost.getPrice());

        return postRepository.save(existingPost);
    }

    // ========================================
    // DELETE POST / SERVICE
    // ========================================

    public boolean deletePost(Long id) {

        if (!postRepository.existsById(id)) {
            return false;
        }

        postRepository.deleteById(id);

        return true;
    }
}