package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.CourseDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseModel {

    public ArrayList<CourseDto> getAllCourses() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Course");
        ArrayList<CourseDto> courseDtos = new ArrayList<>();

        while (rst.next()) {
            CourseDto courseDto = new CourseDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)
            );
            courseDtos.add(courseDto);
        }

        return courseDtos;
    }

    public boolean saveCourse(CourseDto courseDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Course VALUES (?,?,?,?,?)",
                courseDto.getCourse_id(),
                courseDto.getStatus(),
                courseDto.getName(),
                courseDto.getDuration(),
                courseDto.getPrice()
        );
    }

    public boolean updateCourse(CourseDto courseDto) throws SQLException {
        return SQLUtil.execute("UPDATE Course SET status=?, name=?, duration=?, price=? WHERE course_id=?",
                courseDto.getStatus(),
                courseDto.getName(),
                courseDto.getDuration(),
                courseDto.getPrice(),
                courseDto.getCourse_id()
        );
    }

    public boolean deleteCourse(String courseId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Course WHERE course_id=?", courseId);
    }

    public boolean existCourse(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT course_id FROM Course WHERE course_id=?", id);
        return rst.next();
    }

    public String getNextCourseId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT course_id FROM Course ORDER BY course_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }

    public List<String> getAllCourseIds() throws SQLException {
        List<String> courseIds = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT course_id FROM Course");
        while (resultSet.next()) {
            courseIds.add(resultSet.getString("course_id"));
        }
        return courseIds;
    }
}