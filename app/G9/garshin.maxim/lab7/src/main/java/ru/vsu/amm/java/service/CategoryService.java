package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.entities.Note;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.CategoryRepository;
import ru.vsu.amm.java.repository.NoteRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService() {
        noteRepository = new NoteRepository();
        categoryRepository = new CategoryRepository();
        userRepository = new UserRepository();
    }

    public List<Category> getUserCategories(String username) {
        try {
            Long userId = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"))
                    .getUserId();

            return categoryRepository.findByUserId(userId);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get user categories", e);
        }
    }

    public List<Note> getNotesByCategory(long userId, long categoryId) {
        logger.info("Fetching notes for user ID: {} and category ID: {}", userId, categoryId);
        try {
            logger.info("Found notes in category ID: {} for user ID: {}", categoryId, userId);
            return noteRepository.findByUserIdAndCategory(userId, categoryId);
        } catch (SQLException e) {
            logger.error("Failed to fetch notes by category. User ID: {}, Category ID: {}. Error: {}",
                    userId, categoryId, e.getMessage());
            throw new DatabaseException("Failed to retrieve notes by category", e);
        }
    }

    public void addCategory(String username, String title) throws DatabaseException {
        try {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            long userId;
            if (user.isPresent()) {
                userId = user.get().getUserId();
                categoryRepository.addCategory((new Category(title, userId)));
                logger.info("Created new category: {} for user: {}", title, userId);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to add category", e);
        }
    }

    public void deleteCategory(String username, long categoryId) throws DatabaseException {
        try {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            long userId;
            if (user.isPresent()) {
                userId = user.get().getUserId();
                boolean deleted = categoryRepository.deleteCategory(userId, categoryId);
                if (!deleted) {
                    throw new DatabaseException("Category not found or not owned by user");
                }
                logger.info("Deleted category with ID: {}", categoryId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete category", e);
        }
    }
}
