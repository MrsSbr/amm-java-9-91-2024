package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Note;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.NoteRepository;
import ru.vsu.amm.java.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService() {
        this.userRepository = new UserRepository();
        this.noteRepository = new NoteRepository();
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

    public long createNote(String username, String content) throws DatabaseException, SQLException {
        try {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            long userId;
            Note note = new Note();

            if (user.isPresent()) {
                userId = user.get().getUserId();
                note.setContent(content);
                note.setUserId(userId);
            }

            long noteId = noteRepository.create(note);

            logger.info("Created new note with ID: {} for user: {}", noteId, username);
            return noteId;
        } catch (SQLException e) {
            logger.error("Failed to create note for user {}: {}", username, e.getMessage());
            throw new DatabaseException("Failed to create note", e);
        }
    }

    public void updateNoteCategory(long noteId, long categoryId, String username)
            throws DatabaseException, SQLException {
        try {
            logger.info("Updating category for note ID: {}, new category ID: {}, user: {}",
                    noteId, categoryId, username);

            Optional<Note> optionalNote = noteRepository.getNoteById(noteId, username);
            if (optionalNote.isEmpty()) {
                logger.error("Note not found: {}", noteId);
                throw new DatabaseException("Note not found");
            }

            noteRepository.updateNoteCategory(noteId, categoryId);
            logger.debug("Successfully updated category for note {}", noteId);
        } catch (SQLException e) {
            logger.error("SQL error updating category for note {}", noteId, e);
            throw new DatabaseException("Error updating note category", e);
        }
    }

    public void removeNoteCategory(long noteId, String username)
            throws DatabaseException, SQLException {
        try {
            logger.info("Removing category from note ID: {}, user: {}", noteId, username);

            Optional<Note> optionalNote = noteRepository.getNoteById(noteId, username);
            if (optionalNote.isEmpty()) {
                logger.error("Note not found: {}", noteId);
                throw new DatabaseException("Note not found");
            }

            noteRepository.removeNoteCategory(noteId);
            logger.debug("Removed category from note {}", noteId);

        } catch (SQLException e) {
            logger.error("SQL error removing category from note {}", noteId, e);
            throw new DatabaseException("Error removing note category", e);
        }
    }
}