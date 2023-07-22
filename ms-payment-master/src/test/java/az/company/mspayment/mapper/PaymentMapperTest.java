package az.company.mspayment.mapper;

import az.company.mspayment.entity.Payment;
import az.company.mspayment.model.request.PaymentRequest;
import az.company.mspayment.model.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentMapperTest {

    @Test
    void toEntityTest() {

        //Arrange
        var request = new PaymentRequest();
        request.setDescription("description");
        request.setAmount(BigDecimal.TEN);

        var expected = new Payment();
        expected.setDescription("description");
        expected.setAmount(BigDecimal.TEN);

        //Act(Actual)
        var actual =  PaymentMapper.mapRequestToEntity(request);

        //Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void toResponseTest() {
        //Arrange
        var request = new Payment();
        request.setDescription("description");
        request.setAmount(BigDecimal.TEN);

        var expected = new PaymentResponse();
        expected.setDescription("description");
        expected.setAmount(BigDecimal.TEN);

        //Act
        var actual = PaymentMapper.mapEntityToResponse(request);
        actual.setResponseAt(null);
        //Assert
        assertThat(actual).isEqualTo(expected);
    }

}