package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.StudentDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    public ArrayList<StudentDto> getAllStudents() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Student");
        ArrayList<StudentDto> studentDtos = new ArrayList<>();

        while (rst.next()) {
            StudentDto studentDto = new StudentDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            studentDtos.add(studentDto);
        }

        return studentDtos;
    }

    public boolean saveStudent(StudentDto studentDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Student VALUES (?,?,?,?,?)",
                studentDto.getStudent_id(),
                studentDto.getName(),
                studentDto.getEmail(),
                studentDto.getNic(),
                studentDto.getContact()
        );
    }

    public boolean updateStudent(StudentDto studentDto) throws SQLException {
        return SQLUtil.execute("UPDATE Student SET name=?, email=?, nic=?, contact=? WHERE student_id=?",
                studentDto.getName(),
                studentDto.getEmail(),
                studentDto.getNic(),
                studentDto.getContact(),
                studentDto.getStudent_id()
        );
    }

    public boolean deleteStudent(String studentId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Student WHERE student_id=?", studentId);
    }

    public boolean existStudent(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT student_id FROM Student WHERE student_id=?", id);
        return rst.next();
    }

    public String getNextStudentId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT student_id FROM Student ORDER BY student_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    public List<String> getAllStudentNames() throws SQLException {
        List<String> studentNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM Student");
        while (resultSet.next()) {
            studentNames.add(resultSet.getString("name"));
        }
        return studentNames;
    }
}