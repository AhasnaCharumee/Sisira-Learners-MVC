package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.InstructorDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorModel {

    public ArrayList<InstructorDto> getAllInstructors() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Instructor");
        ArrayList<InstructorDto> instructorDtos = new ArrayList<>();

        while (rst.next()) {
            InstructorDto instructorDto = new InstructorDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            instructorDtos.add(instructorDto);
        }

        return instructorDtos;
    }

    public boolean saveInstructor(InstructorDto instructorDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Instructor VALUES (?,?,?,?,?)",
                instructorDto.getInstructor_id(),
                instructorDto.getName(),
                instructorDto.getEmail(),
                instructorDto.getContact(),
                instructorDto.getVehicle_class()
        );
    }

    public boolean updateInstructor(InstructorDto instructorDto) throws SQLException {
        return SQLUtil.execute("UPDATE Instructor SET name=?, email=?, contact=?, vehicle_class=? WHERE instructor_id=?",
                instructorDto.getName(),
                instructorDto.getEmail(),
                instructorDto.getContact(),
                instructorDto.getVehicle_class(),
                instructorDto.getInstructor_id()
        );
    }

    public boolean deleteInstructor(String instructorId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Instructor WHERE instructor_id=?", instructorId);
    }

    public boolean existInstructor(String id) throws SQLException {
        return SQLUtil.execute("SELECT instructor_id FROM Instructor WHERE instructor_id=?", id);
    }

    public String getNextInstructorId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT instructor_id FROM Instructor ORDER BY instructor_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    public List<String> getAllInstructorNames() throws SQLException {
        List<String> instructorNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT name FROM Instructor");
        while (resultSet.next()) {
            instructorNames.add(resultSet.getString("name"));
        }
        return instructorNames;
    }
}