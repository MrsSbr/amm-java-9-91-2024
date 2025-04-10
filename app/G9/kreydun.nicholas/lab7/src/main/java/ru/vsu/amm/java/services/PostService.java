package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;

import java.util.List;
import java.util.UUID;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public UUID create(Post post) {
        if (post != null) {
            return postRepository.create(post);
        }
        return null;
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

    public boolean update(Post post) {
        if (post != null) {
            return postRepository.update(post);
        }
        return false;
    }

    public boolean delete(UUID id) {
        return postRepository.delete(id);
    }
}

