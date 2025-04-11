import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.repository.PostRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostRepositoryTest {

    private PostRepository postRepository;
    private UUID testPostId;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        postRepository = new PostRepository();
        testUserId = UUID.fromString("bea5cec5-0abe-4bcb-9adb-c110faf9dd9a");
    }

    @Test
    @Order(1)
    void testCreatePost() {
        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(testUserId);
        post.setContent("Test post content");

        testPostId = postRepository.create(post);
        assertNotNull(testPostId);

        Post createdPost = postRepository.getById(testPostId);
        assertNotNull(createdPost);
        assertEquals("Test post content", createdPost.getContent());
    }

    @Test
    @Order(2)
    void testGetPostById() {
        Post post = postRepository.getById(testPostId);
        assertNotNull(post);
        assertEquals(testUserId, post.getUserId());
    }

    @Test
    @Order(3)
    void testUpdatePost() {
        Post post = postRepository.getById(testPostId);
        assertNotNull(post);

        post.setContent("Updated content");
        boolean isUpdated = postRepository.update(post);

        assertTrue(isUpdated);

        Post updatedPost = postRepository.getById(testPostId);
        assert updatedPost != null;
        assertEquals("Updated content", updatedPost.getContent());
    }

    @Test
    @Order(4)
    void testGetPostsByUserId() {
        List<Post> posts = postRepository.getPostsByUserId(testUserId);
        assertFalse(posts.isEmpty());

        assertEquals(testUserId, posts.get(0).getUserId());
    }

    @Test
    @Order(5)
    void testDeletePost() {
        boolean isDeleted = postRepository.delete(testPostId);
        assertTrue(isDeleted);

        Post deletedPost = postRepository.getById(testPostId);
        assertNull(deletedPost);
    }
}
