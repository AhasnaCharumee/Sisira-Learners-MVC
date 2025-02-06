package lk.ijse.gdse72.sisiralearners.dto;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionDto {
    String session_id;
    String instructor_id;
    String vehicle_id;
    String day;
    String start_time;
    String end_time;
}
