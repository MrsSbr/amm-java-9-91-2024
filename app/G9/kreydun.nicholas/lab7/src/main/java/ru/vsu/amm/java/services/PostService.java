package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class PostService {
    private final PostRepository postRepository;

    public PostService() {
        this.postRepository = new PostRepository();
    }

    public UUID create(String content, String userIdParam) {
        UUID userId;
        try {
            userId = UUID.fromString(userIdParam);
        } catch (IllegalArgumentException e) {
            return null;
        }

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(userId);
        post.setContent(content);
        post.setCreatedAt(LocalTime.now());

        return postRepository.create(post);
    }

    public Post getById(UUID id) {
        return postRepository.getById(id);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public List<Post> getPostsByUserId(UUID userId) {
        return postRepository.getPostsByUserId(userId);
    }

    public boolean update(UUID postId, String newContent) {
        Post existingPost = postRepository.getById(postId);
        if (existingPost != null) {
            existingPost.setContent(newContent);
            return postRepository.update(existingPost);
        }
        return false;
    }

    public boolean delete(UUID postId) {
        return postRepository.delete(postId);
    }
}
