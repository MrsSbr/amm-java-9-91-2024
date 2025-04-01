package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.BookUpdates;
import ru.vsu.amm.java.enums.UpdateType;

import java.sql.*;

public class BookUpdatesMapper implements Mapper<BookUpdates> {
    public BookUpdatesMapper() {
    }

    @Override
    public BookUpdates mapRowToObject(ResultSet resultSet) throws SQLException {
        BookUpdates bookUpdates = new BookUpdates();

        bookUpdates.setUpdateId(resultSet.getInt("Id_update"));
        bookUpdates.setBookId(resultSet.getInt("Id_book"));
        bookUpdates.setUserId(resultSet.getInt("Id_user"));
        bookUpdates.setUpdateTime(resultSet.getTimestamp("UpdateTime").toLocalDateTime());
        bookUpdates.setUpdateType(UpdateType.valueOf(resultSet.getString("UpdateType")));

        return bookUpdates;
    }

    @Override
    public PreparedStatement mapObjectToRow(BookUpdates bookUpdates,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, bookUpdates.getBookId());
        ps.setInt(2, bookUpdates.getUserId());
        ps.setTimestamp(3, Timestamp.valueOf(bookUpdates.getUpdateTime()));
        ps.setString(4, bookUpdates.getUpdateType().name());

        return ps;
    }
}
