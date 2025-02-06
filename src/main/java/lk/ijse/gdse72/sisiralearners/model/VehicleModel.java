package lk.ijse.gdse72.sisiralearners.model;

import lk.ijse.gdse72.sisiralearners.dto.VehicleDto;
import lk.ijse.gdse72.sisiralearners.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleModel {

    public ArrayList<VehicleDto> getAllVehicles() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Vehicle");
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();

        while (rst.next()) {
            VehicleDto vehicleDto = new VehicleDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)

            );
            vehicleDtos.add(vehicleDto);
        }

        return vehicleDtos;
    }

    public boolean saveVehicle(VehicleDto vehicleDto) throws SQLException {
        return SQLUtil.execute("INSERT INTO Vehicle VALUES (?,?,?,?,?,?)",
                vehicleDto.getVehicle_id(),
                vehicleDto.getVehicle_name(),
                vehicleDto.getVehicle_number(),
                vehicleDto.getEngine_number(),
                vehicleDto.getVehicle_class(),
                vehicleDto.getStatus()
        );
    }
    public boolean updateVehicle(VehicleDto vehicleDto) throws SQLException {
        return SQLUtil.execute("UPDATE Vehicle SET vehicle_name=?, vehicle_number=?, engine_number=?, vehicle_class=?, status=? WHERE vehicle_id=?",
                vehicleDto.getVehicle_name(),
                vehicleDto.getVehicle_number(),
                vehicleDto.getEngine_number(),
                vehicleDto.getVehicle_class(),
                vehicleDto.getStatus(),
                vehicleDto.getVehicle_id()
        );
    }

    public boolean deleteVehicle(String vehicleId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Vehicle WHERE vehicle_id=?", vehicleId);
    }

    public boolean existVehicle(String id) throws SQLException{
        return SQLUtil.execute("SELECT vehicle_id FROM Vehicle WHERE vehicle_id=?", id);
    }
    public String getNextVehicleId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT vehicle_id FROM Vehicle ORDER BY vehicle_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("V%03d", newIdIndex);
        }
        return "V001";
    }

    public List<String> getAllVehicleNames() throws SQLException {
        List<String> vehicleNames = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT vehicle_name FROM Vehicle");
        while (resultSet.next()) {
            vehicleNames.add(resultSet.getString("vehicle_name"));
        }
        return vehicleNames;
    }
}
