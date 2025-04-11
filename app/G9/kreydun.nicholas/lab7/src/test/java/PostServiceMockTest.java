import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;
import ru.vsu.amm.java.services.PostService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

import java.time.LocalTime;

class PostServiceMockTest {

    private PostRepository postRepository;
    private PostService postService;
    private Post testPost;
    private UUID postId;
    private UUID userId;
    private final String testContent = "Test Post Content";

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postService = new PostService(postRepository);

        postId = UUID.randomUUID();
        userId = UUID.randomUUID();

        testPost = new Post();
        testPost.setId(postId);
        testPost.setUserId(userId);
        testPost.setContent(testContent);
        testPost.setCreatedAt(LocalTime.now());
    }

    @Test
    void testGetPostByIdExists() {
        when(postRepository.getById(postId)).thenReturn(testPost);

        boolean postNotExists = postService.getById(postId);

        assertFalse(postNotExists);
        verify(postRepository, times(1)).getById(postId);
    }

    @Test
    void testGetPostByIdNotExists() {
        when(postRepository.getById(postId)).thenReturn(null);

        boolean postNotExists = postService.getById(postId);

        assertTrue(postNotExists);
        verify(postRepository, times(1)).getById(postId);
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = Arrays.asList(testPost, new Post());
        when(postRepository.getAll()).thenReturn(posts);

        List<Post> result = postService.getAll();

        assertEquals(2, result.size());
        verify(postRepository, times(1)).getAll();
    }

    @Test
    void testCreatePostSuccess() {
        when(postRepository.create(any(Post.class))).thenReturn(postId);

        UUID newPostId = postService.create(testContent, userId);

        assertNotNull(newPostId);
        assertEquals(postId, newPostId);
        verify(postRepository, times(1)).create(any(Post.class));
    }

    @Test
    void testUpdatePostSuccess() {
        String newContent = "Updated Content";
        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setUserId(userId);
        updatedPost.setContent(newContent);
        updatedPost.setCreatedAt(LocalTime.now());

        when(postRepository.getById(postId)).thenReturn(testPost);
        when(postRepository.update(any(Post.class))).thenReturn(true);

        boolean result = postService.update(postId, newContent);

        assertTrue(result);
        verify(postRepository, times(1)).getById(postId);
        verify(postRepository, times(1)).update(any(Post.class));
        assertEquals(newContent, testPost.getContent()); //Проверяем, что контент поста изменился
    }

    @Test
    void testUpdatePostNotFound() {
        when(postRepository.getById(postId)).thenReturn(null);

        boolean result = postService.update(postId, "New Content");

        assertFalse(result);
        verify(postRepository, times(1)).getById(postId);
        verify(postRepository, never()).update(any(Post.class));
    }



    @Test
    void testDeletePostSuccess() {
        when(postRepository.delete(postId)).thenReturn(true);

        boolean result = postService.delete(postId);

        assertTrue(result);
        verify(postRepository, times(1)).delete(postId);
    }

    @Test
    void testDeletePostFailure() {
        when(postRepository.delete(postId)).thenReturn(false);

        boolean result = postService.delete(postId);

        assertFalse(result);
        verify(postRepository, times(1)).delete(postId);
    }


    @Test
    void testGetPostsByUserId() {
        List<Post> posts = Arrays.asList(testPost, new Post());
        when(postRepository.getPostsByUserId(userId)).thenReturn(posts);

        List<Post> result = postService.getPostsByUserId(userId);

        assertEquals(2, result.size());
        verify(postRepository, times(1)).getPostsByUserId(userId);
    }
}