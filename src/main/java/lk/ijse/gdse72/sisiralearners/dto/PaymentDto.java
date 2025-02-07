package lk.ijse.gdse72.sisiralearners.dto;

import java.util.Date;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    String payment_id;
    String student_id;
    String note;
    double amount;
    Date payment_date;
}
