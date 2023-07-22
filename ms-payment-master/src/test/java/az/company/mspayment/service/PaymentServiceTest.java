package az.company.mspayment.service;

import az.company.mspayment.client.CountryClient;
import az.company.mspayment.entity.Payment;
import az.company.mspayment.mapper.PaymentMapper;
import az.company.mspayment.model.client.CountryDto;
import az.company.mspayment.model.request.PaymentRequest;
import az.company.mspayment.repository.PaymentRepository;
import liquibase.pro.packaged.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private CountryClient countryClient;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void savePayment() {
        var request = new PaymentRequest();
        request.setCurrency("UK");

        var payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(3405));

        CountryDto countryDto = new CountryDto();
        countryDto.setName("UK");
        countryDto.setRemainingLimit(BigDecimal.valueOf(3405));

        List<CountryDto> countryDtos = new ArrayList<>();
        countryDtos.add(countryDto);

        when(countryClient.getAllAvailableCountries(request.getCurrency()))
                .thenReturn(countryDtos);


        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        verify(countryClient, times(1)).getAllAvailableCountries(request.getCurrency());
        verify(paymentRepository, times(1)).save(any(Payment.class));

    }

}