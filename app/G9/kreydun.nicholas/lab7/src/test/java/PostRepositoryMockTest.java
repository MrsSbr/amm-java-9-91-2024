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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

class PostRepositoryMockTest {

    private PostRepository postRepository;
    private Post testPost;
    private UUID postId;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);

        postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        testPost = new Post();
        testPost.setId(postId);
        testPost.setUserId(userId);
        testPost.setContent("Test Post");
    }

    @Test
    void testGetPostById() {
        when(postRepository.getById(postId)).thenReturn(testPost);
        PostService postService = new PostService(postRepository);

        //тестируем пост сервис
        // проверяем логику сервиса
        Post foundPost = postRepository.getById(postId);

        assertNotNull(foundPost);
        assertEquals("Test Post", foundPost.getContent());
        verify(postRepository, times(1)).getById(postId);
    }

    @Test
    void testGetAllPosts() {
        List<Post> posts = Arrays.asList(testPost, new Post());
        when(postRepository.getAll()).thenReturn(posts);

        List<Post> result = postRepository.getAll();

        assertEquals(2, result.size());
        verify(postRepository, times(1)).getAll();
    }

    @Test
    void testCreatePost() {
        when(postRepository.create(any(Post.class))).thenReturn(postId);

        UUID newPostId = postRepository.create(testPost);

        assertNotNull(newPostId);
        verify(postRepository, times(1)).create(testPost);
    }

    @Test
    void testUpdatePost() {
        when(postRepository.update(testPost)).thenReturn(true);

        boolean result = postRepository.update(testPost);

        assertTrue(result);
        verify(postRepository, times(1)).update(testPost);
    }

    @Test
    void testDeletePost() {
        when(postRepository.delete(postId)).thenReturn(true);

        boolean result = postRepository.delete(postId);

        assertTrue(result);
        verify(postRepository, times(1)).delete(postId);
    }
}
