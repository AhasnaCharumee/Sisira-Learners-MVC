package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.UserDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public ArrayList<UserDto> getAllUsers() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM User");
        ArrayList<UserDto> userDtos = new ArrayList<>();

        while (rst.next()) {
            UserDto userDto = new UserDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            userDtos.add(userDto);
        }

        return userDtos;
    }

    public boolean saveUser(UserDto userDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO User VALUES (?,?,?,?,?)",
                userDto.getUser_id(),
                userDto.getUser_name(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }

    public boolean updateUser(UserDto userDto) throws SQLException {
        return SQLUtil.execute("UPDATE User SET user_name=?, email=?, password=?, role=? WHERE user_id=?",
                userDto.getUser_name(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(),
                userDto.getUser_id()
        );
    }

    public boolean deleteUser(String userId) throws SQLException {
        return SQLUtil.execute("DELETE FROM User WHERE user_id=?", userId);
    }

    public boolean existUser(String id) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM User WHERE user_id=?", id);
        return rst.next();
    }

    public String getNextUserId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM User ORDER BY user_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(2);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    public List<String> getAllUserIds() throws SQLException {
        List<String> userIds = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT user_id FROM User");
        while (resultSet.next()) {
            userIds.add(resultSet.getString("user_id"));
        }
        return userIds;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM User WHERE user_name=? AND password=?", username, password);
        return rst.next();
    }


}