package ru.vsu.amm.java.Repository;

import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Booking;
import ru.vsu.amm.java.Enums.Status;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingRepository implements Repository<Booking> {

    private static final Logger logger = Logger.getLogger(BookingRepository.class.getName());
    private final DataSource dataSource;

    public BookingRepository() { dataSource = DbConfiguration.getDataSource(); }

    @Override
    public Optional<Booking> findById(int id) throws SQLException {

        final String sql = "SELECT id_booking, id_tour, id_user, Date_booking, Count_participation," +
                "Total_price, Status  FROM Booking WHERE id_booking = ?";

        //подключение скл
        try (var conn = dataSource.getConnection()) {
            //подстановка выражения
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id); //подстановка id
            ps.execute(); //выполнение запроса

            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                Status status = Status.valueOf(rs.getString("Status"));
                return Optional.of(new Booking(
                        rs.getInt("id_booking"),
                        rs.getInt("id_tour"),
                        rs.getInt("id_user"),
                        rs.getDate("Date_booking").toLocalDate(),
                        rs.getInt("Count_participation"),
                        rs.getInt("Total_price"),
                        status
                ));

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        final String sql = "SELECT id_booking, id_tour, id_user, Date_booking, " +
                "Count_participation, Total_price, Status FROM Booking";

        try (var conn = dataSource.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();

            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                Status status = Status.valueOf(rs.getString("Status"));
                bookings.add(new Booking(
                        rs.getInt("id_booking"),
                        rs.getInt("id_tour"),
                        rs.getInt("id_user"),
                        rs.getDate("Date_booking").toLocalDate(),
                        rs.getInt("Count_participation"),
                        rs.getInt("Total_price"),
                        status
                ));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
        return bookings;
    }

    @Override
    public void update(Booking entity) throws SQLException {
        final String sql = "UPDATE Booking SET id_tour = ?, id_user = ?, Date_booking = ?, Count_participation = ?, " +
                "Total_price = ?, Status = ? WHERE id_booking = ?";

        try (var conn = dataSource.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(entity.getDate()));
            ps.setInt(2, entity.getCountParticipants());
            ps.setInt(3, entity.getTotalPrice());
            ps.setString(4, String.valueOf(entity.getStatus()));
            ps.setInt(5, entity.getId());

            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void save(Booking entity) throws SQLException {
        final String sql = "INSERT INTO Booking (id_tour, id_user, Date, Count_participantions," +
                " Total_price, Status) VALUES (?, ?, ?, ?, ?, ?)";

        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, Date.valueOf(entity.getDate()));
            ps.setInt(2, entity.getCountParticipants());
            ps.setInt(3, entity.getTotalPrice());
            ps.setString(4, String.valueOf(entity.getStatus()));

            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public void delete(Booking entity) throws SQLException {
        final String sql = "DELETE FROM Booking WHERE id_booking = ?";
        try (var conn = dataSource.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.execute();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new SQLException(ex.getMessage());
        }
    }
}
