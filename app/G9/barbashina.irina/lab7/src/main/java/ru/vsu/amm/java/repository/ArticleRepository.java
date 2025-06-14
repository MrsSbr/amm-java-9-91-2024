package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.entities.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleRepository implements CrudRepository<Article> {
    private final DataSource dataSource;

    public ArticleRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Article> findById(Long id) throws SQLException {
        final String query = "SELECT " +
                "art.id_article, art.title, art.article_content, art.date_publication, " +
                "c.id_category AS category_id, c.name_category AS category_name, " +
                "aut.id_author AS author_id, aut.surname, aut.name_author, aut.patronymic, aut.registration_date " +
                "FROM " +
                "Article art " +
                "JOIN Category c ON art.ref_category = c.id_category " +
                "JOIN Author aut ON art.ref_author = aut.id_author " +
                "WHERE art.id_article = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToArticle(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() throws SQLException {
        final String query = "SELECT " +
                "art.id_article, art.title, art.article_content, art.date_publication, " +
                "c.id_category AS category_id, c.name_category AS category_name, " +
                "aut.id_author AS author_id, aut.surname, aut.name_author, aut.patronymic, aut.registration_date " +
                "FROM " +
                "Article art " +
                "JOIN Category c ON art.ref_category = c.id_category " +
                "JOIN Author aut ON art.ref_author = aut.id_author";

        List<Article> articles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                articles.add(mapResultSetToArticle(resultSet));
            }
        }
        return articles;
    }

    private Article mapResultSetToArticle(ResultSet resultSet) throws SQLException {
        Category category = new Category(
                resultSet.getLong("category_id"),
                resultSet.getString("category_name")
        );

        Author author = new Author(
                resultSet.getLong("author_id"),
                resultSet.getString("surname"),
                resultSet.getString("name_author"),
                resultSet.getString("patronymic"),
                resultSet.getObject("registration_date", LocalDate.class)
        );

        return new Article(
                resultSet.getLong("id_article"),
                resultSet.getString("title"),
                resultSet.getString("article_content"),
                resultSet.getObject("date_publication", LocalDate.class),
                category,
                author
        );
    }

    @Override
    public void save(Article entity) throws SQLException {
        final String query = "INSERT INTO Article(title, article_content, date_publication, " +
                "ref_category, ref_author) " +
                "VALUES(?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getContent());
            preparedStatement.setObject(3, entity.getDatePublication());
            preparedStatement.setLong(4, entity.getCategory().getId());
            preparedStatement.setLong(5, entity.getAuthor().getId());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(Article entity) throws SQLException {
        final String query = "UPDATE Article " +
                "SET title = ?, article_content = ?, date_publication = ?, " +
                "ref_category = ?, ref_author = ? " +
                "WHERE id_article = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getContent());
            preparedStatement.setObject(3, entity.getDatePublication());
            preparedStatement.setLong(4, entity.getCategory().getId());
            preparedStatement.setLong(5, entity.getAuthor().getId());
            preparedStatement.setLong(6, entity.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Article entity) throws SQLException {
        final String query = "DELETE FROM Article WHERE id_article = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, entity.getId());
            preparedStatement.executeUpdate();
        }
    }
}