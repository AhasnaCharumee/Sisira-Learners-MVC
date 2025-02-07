package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.StudentRegistrationDto;
import lk.ijse.gdse72.sisiralearners.dto.tm.StudentRegistrationTM;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRegistrationModel {

    public boolean saveStudentRegistration(StudentRegistrationDto studentRegistrationDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO StudentRegistration (registration_id, student_id, course_id, registration_date, status) VALUES (?, ?, ?, ?,?)",
                studentRegistrationDto.getRegistration_id(),
                studentRegistrationDto.getStudent_id(),
                studentRegistrationDto.getCourse_id(),
                studentRegistrationDto.getRegistration_date(),
                studentRegistrationDto.getStatus()
        );
    }

    public boolean updateStudentRegistration(StudentRegistrationDto studentRegistrationDto) throws SQLException {
        return SQLUtil.execute("UPDATE StudentRegistration SET student_id=?, course_id=?, registration_date=?, status=? WHERE registration_id=?",
                studentRegistrationDto.getStudent_id(),
                studentRegistrationDto.getCourse_id(),
                studentRegistrationDto.getRegistration_date(),
                studentRegistrationDto.getRegistration_id(),
                studentRegistrationDto.getStatus()
        );
    }

    public boolean deleteStudentRegistration(String registrationId) throws SQLException {
        return SQLUtil.execute("DELETE FROM StudentRegistration WHERE registration_id=?", registrationId);
    }

    public String getNextStudentRegistrationId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT registration_id FROM StudentRegistration ORDER BY registration_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("SR%03d", newIdIndex);
        }
        return "SR001";
    }

    public List<StudentRegistrationTM> getAllStudentRegistrations() throws SQLException {
        List<StudentRegistrationTM> studentRegistrations = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("""
                SELECT sr.registration_id, sr.status, s.name , c.name, s.email, s.contact, s.pay_balance
                    FROM StudentRegistration sr
                    JOIN Student s ON sr.student_id = s.student_id
                    JOIN Course c ON sr.course_id = c.course_id"""
        );

        while (resultSet.next()) {
            studentRegistrations.add(new StudentRegistrationTM(
                    resultSet.getString("registration_id"),
                    resultSet.getString("status"),
                    resultSet.getString("name"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("contact"),
                    resultSet.getDouble("pay_balance")
            ));
        }
        return studentRegistrations;
    }

//    public boolean existStudentRegistration(String registrationId) throws SQLException {
//        return SQLUtil.execute("SELECT StudentRegistrationId FROM StudentRegistration WHERE StudentRegistrationId=?", registrationId).next();
//    }
}