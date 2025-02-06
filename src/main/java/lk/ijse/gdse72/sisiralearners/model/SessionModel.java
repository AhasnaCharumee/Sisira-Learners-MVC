package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.SessionDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionModel {

    public ArrayList<SessionDto> getAllSessions() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Session");
        ArrayList<SessionDto> sessionDtos = new ArrayList<>();

        while (rst.next()) {
            SessionDto sessionDto = new SessionDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            sessionDtos.add(sessionDto);
        }

        return sessionDtos;
    }

    public boolean saveSession(SessionDto sessionDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Session VALUES (?,?,?,?,?,?)",
                sessionDto.getSession_id(),
                sessionDto.getInstructor_id(),
                sessionDto.getVehicle_id(),
                sessionDto.getDay(),
                sessionDto.getStart_time(),
                sessionDto.getEnd_time()
        );
    }

    public boolean updateSession(SessionDto sessionDto) throws SQLException {
        return SQLUtil.execute("UPDATE Session SET instructor_id=?, vehicle_id=?, day=?, start_time=?, end_time=? WHERE session_id=?",
                sessionDto.getInstructor_id(),
                sessionDto.getVehicle_id(),
                sessionDto.getDay(),
                sessionDto.getStart_time(),
                sessionDto.getEnd_time(),
                sessionDto.getSession_id()
        );
    }

    public boolean deleteSession(String sessionId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Session WHERE session_id=?", sessionId);
    }

    public boolean existSession(String id) throws SQLException {
        return SQLUtil.execute("SELECT session_id FROM Session WHERE session_id=?", id);
    }

    public String getNextSessionId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT session_id FROM Session ORDER BY session_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("SE%03d", newIdIndex);
        }
        return "SE001";
    }

    public List<String> getAllSessionIds() throws SQLException {
        List<String> sessionIds = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT session_id FROM Session");
        while (resultSet.next()) {
            sessionIds.add(resultSet.getString("session_id"));
        }
        return sessionIds;
    }
}