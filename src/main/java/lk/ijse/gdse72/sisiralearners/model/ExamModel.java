package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.ExamDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamModel {

    public ArrayList<ExamDto> getAllExams() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Exam");
        ArrayList<ExamDto> examDtos = new ArrayList<>();

        while (rst.next()) {
            ExamDto examDto = new ExamDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5)
            );
            examDtos.add(examDto);
        }

        return examDtos;
    }

    public boolean saveExam(ExamDto examDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Exam VALUES (?,?,?,?,?)",
                examDto.getExam_id(),
                examDto.getExam_name(),
                examDto.getStudent_id(),
                examDto.getExam_date(),
                examDto.getResult()
        );
    }

    public boolean updateExam(ExamDto examDto) throws SQLException {
        return SQLUtil.execute("UPDATE Exam SET exam_name=?, student_id=?, exam_date=?, result=? WHERE exam_id=?",
                examDto.getExam_name(),
                examDto.getStudent_id(),
                examDto.getExam_date(),
                examDto.getResult(),
                examDto.getExam_id()
        );
    }

    public boolean deleteExam(String examId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Exam WHERE exam_id=?", examId);
    }

    public boolean existExam(String id) throws SQLException {
        return SQLUtil.execute("SELECT exam_id FROM Exam WHERE exam_id=?", id);
    }

    public String getNextExamId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT exam_id FROM Exam ORDER BY exam_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public List<String> getAllExamNames() throws SQLException {
        List<String> examNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT exam_name FROM Exam");
        while (resultSet.next()) {
            examNames.add(resultSet.getString("exam_name"));
        }
        return examNames;
    }
}