package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.BookingDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {

    public ArrayList<BookingDto> getAllBookings() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Booking");
        ArrayList<BookingDto> bookingDtos = new ArrayList<>();

        while (rst.next()) {
            BookingDto bookingDto = new BookingDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5)
            );
            bookingDtos.add(bookingDto);
        }

        return bookingDtos;
    }

    public boolean saveBooking(BookingDto bookingDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Booking VALUES (?,?,?,?,?)",
                bookingDto.getBooking_id(),
                bookingDto.getStudent_id(),
                bookingDto.getSession_id(),
                bookingDto.getBooking_date(),
                bookingDto.getPractise_session()
        );
    }

    public boolean updateBooking(BookingDto bookingDto) throws SQLException {
        return SQLUtil.execute("UPDATE Booking SET student_id=?, session_id=?, booking_date=?, practise_session=? WHERE booking_id=?",
                bookingDto.getStudent_id(),
                bookingDto.getSession_id(),
                bookingDto.getBooking_date(),
                bookingDto.getPractise_session(),
                bookingDto.getBooking_id()
        );
    }

    public boolean deleteBooking(String bookingId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Booking WHERE booking_id=?", bookingId);
    }

    public boolean existBooking(String id) throws SQLException {
        return SQLUtil.execute("SELECT booking_id FROM Booking WHERE booking_id=?", id);
    }

    public String getNextBookingId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT booking_id FROM Booking ORDER BY booking_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("B%03d", newIdIndex);
        }
        return "B001";
    }

    public List<String> getAllBookingIds() throws SQLException {
        List<String> bookingIds = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT booking_id FROM Booking");
        while (resultSet.next()) {
            bookingIds.add(resultSet.getString("booking_id"));
        }
        return bookingIds;
    }
}