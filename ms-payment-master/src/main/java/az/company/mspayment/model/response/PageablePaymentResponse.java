package az.company.mspayment.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageablePaymentResponse {

    List<PaymentResponse> payments;
    Long totalElements;
    int totalPages;
    boolean hasNextPage;

}
