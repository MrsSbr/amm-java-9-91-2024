package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Category;
import ru.vsu.amm.java.entities.Note;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.CategoryRepository;
import ru.vsu.amm.java.repository.NoteRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public NoteService() {
        this.userRepository = new UserRepository();
        this.noteRepository = new NoteRepository();
        this.categoryRepository = new CategoryRepository();
    }

    public long getUserIdByUsername(String username) throws SQLException {
        logger.info("Fetching userId for username: {}", username);
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"))
                    .getUserId();
        } catch (SQLException e) {
            logger.error("Failed to fetch userId for username: {}. Error: {}", username, e.getMessage());
            throw new DatabaseException("Failed to retrieve user notes for user", e);
        }
    }

    public List<Note> getUserNotes(String username) {
        try {
            Long userId = userRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"))
                    .getUserId();

            return noteRepository.findByUserId(userId);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get user notes", e);
        }
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

    public Note getNoteById(long noteId, String username) throws DatabaseException, SQLException {
        logger.info("Fetching note with noteId: {} for username: {}", noteId, username);
        try {
            return noteRepository.getNoteById(noteId, username)
                    .orElseThrow(() -> new DatabaseException("User not found"));
        } catch (SQLException e) {
            logger.error("Failed to fetch note with noteId: {} for username: {}. Error: {}", noteId, username, e.getMessage());
            throw new DatabaseException("Failed to retrieve user notes for user", e);
        }
    }

    public void updateNoteContent(long noteId, String content, String username)
            throws DatabaseException, SQLException {
        logger.info("Updating note content for noteId: {} for username: {}", noteId, username);
        try {
            noteRepository.updateNoteContent(noteId, content, username);
            logger.info("Successfully updated note content for noteId: {}", noteId);
        } catch (SQLException e) {
            logger.error("Failed to update note content for noteId: {} for username: {}. Error: {}", noteId, username, e.getMessage());
            throw new DatabaseException("Failed to update note content", e);
        }
    }

    public void deleteNote(long noteId, String username) throws DatabaseException, SQLException {
        logger.info("Deleting note with noteId: {} for username: {}", noteId, username);
        try {
            noteRepository.deleteNote(noteId, username);
            logger.info("Successfully deleted note with noteId: {}", noteId);
        } catch (SQLException e) {
            logger.error("Failed to delete note with noteId: {} for username: {}. Error: {}", noteId, username, e.getMessage());
            throw new DatabaseException("Failed to delete note", e);
        }
    }

}