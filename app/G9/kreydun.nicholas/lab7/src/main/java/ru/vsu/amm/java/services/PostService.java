package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(Post post) {
        if (post != null) {
            postRepository.create(post);
        }
    }
}
