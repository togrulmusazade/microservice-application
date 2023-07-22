package az.company.mspayment.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {

    @Min(value = 1)
    @Max(value = 1000)
    BigDecimal amount;

    @NotBlank(message = "Description cannot be empty")
    String description;

    @NotBlank(message = "Currency cannot be empty")
    String currency;

}
