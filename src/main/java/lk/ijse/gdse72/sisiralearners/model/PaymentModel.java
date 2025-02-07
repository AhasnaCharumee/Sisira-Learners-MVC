package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.PaymentDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentModel {

    public ArrayList<PaymentDto> getAllPayments() throws SQLException {
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        try (ResultSet rst = SQLUtil.execute("SELECT * FROM Payment")) {
            while (rst.next()) {
                String paymentId = rst.getString(1);
                String studentId = rst.getString(2);
                String note = rst.getString(3);
                double amount = rst.getDouble(4);
                Date paymentDate = rst.getDate(5);

                PaymentDto paymentDto = new PaymentDto(paymentId, studentId, note, amount, paymentDate);
                paymentDtos.add(paymentDto);
            }
        }
        return paymentDtos;
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Payment (payment_id, student_id, note, amount, payment_date) VALUES (?,?,?,?,?)",
                paymentDto.getPayment_id(),
                paymentDto.getStudent_id(),
                paymentDto.getNote(),
                paymentDto.getAmount(),
                new java.sql.Date(paymentDto.getPayment_date().getTime())
        );
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        return SQLUtil.execute("UPDATE Payment SET student_id=?, note=?, amount=?, payment_date=? WHERE payment_id=?",
                paymentDto.getStudent_id(),
                paymentDto.getNote(),
                paymentDto.getAmount(),
                new java.sql.Date(paymentDto.getPayment_date().getTime()),
                paymentDto.getPayment_id()
        );
    }

    public boolean deletePayment(String paymentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Payment WHERE payment_id=?", paymentId);
    }

    public boolean existPayment(String id) throws SQLException {
        try (ResultSet rst = SQLUtil.execute("SELECT payment_id FROM Payment WHERE payment_id=?", id)) {
            return rst.next();
        }
    }

    public String getNextPaymentId() throws SQLException {
        try (ResultSet rst = SQLUtil.execute("SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1")) {
            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring(1);
                int i = Integer.parseInt(substring);
                int newIdIndex = i + 1;
                return String.format("P%03d", newIdIndex);
            }
        }
        return "P001";
    }

    public List<String> getAllPaymentIds() throws SQLException {
        List<String> paymentIds = new ArrayList<>();
        try (ResultSet resultSet = SQLUtil.execute("SELECT payment_id FROM Payment")) {
            while (resultSet.next()) {
                paymentIds.add(resultSet.getString("payment_id"));
            }
        }
        return paymentIds;
    }

    public boolean deletePaymentByRegistrationId(String studentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Payment WHERE student_id=?", studentId);
    }
}