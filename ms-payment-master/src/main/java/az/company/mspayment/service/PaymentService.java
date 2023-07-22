package az.company.mspayment.service;

import az.company.mspayment.client.CountryClient;
import az.company.mspayment.entity.Payment;
import az.company.mspayment.exception.NotFoundException;
import az.company.mspayment.mapper.PaymentMapper;
import az.company.mspayment.model.request.PaymentCriteria;
import az.company.mspayment.model.request.PaymentRequest;
import az.company.mspayment.model.response.PageablePaymentResponse;
import az.company.mspayment.model.response.PaymentResponse;
import az.company.mspayment.repository.PaymentRepository;
import az.company.mspayment.service.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static az.company.mspayment.mapper.PaymentMapper.mapEntityToResponse;
import static az.company.mspayment.mapper.PaymentMapper.mapRequestToEntity;
import static az.company.mspayment.model.constant.ExceptionConstants.COUNTRY_NOT_FOUND_CODE;
import static az.company.mspayment.model.constant.ExceptionConstants.COUNTRY_NOT_FOUND_MESSAGE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CountryClient countryClient;
    private final PaymentRepository paymentRepository;

    public void savePayment(PaymentRequest request) {
        log.info("savePayment.started");
        countryClient.getAllAvailableCountries(request.getCurrency())
                .stream()
                .filter(country -> country.getRemainingLimit().compareTo(request.getAmount()) > 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE,
                        request.getAmount(),
                        request.getCurrency()),
                        COUNTRY_NOT_FOUND_CODE));

        paymentRepository.save(mapRequestToEntity(request));
        log.info("savePayment.success");
    }

    public PageablePaymentResponse getAllPayments(int page, int count, PaymentCriteria paymentCriteria) {
        log.info("getAllPayments.started");

        var pageable = PageRequest.of(page, count, Sort.by(DESC, "id"));

        var pageablePayments = paymentRepository.findAll(
                new PaymentSpecification(paymentCriteria), pageable);

        var payments = pageablePayments.getContent()
                .stream()
                .map(PaymentMapper::mapEntityToResponse).collect(Collectors.toList());

        log.info("getAllPayments.success");

        return PageablePaymentResponse.builder()
                .payments(payments)
                .hasNextPage(pageablePayments.hasNext())
                .totalElements(pageablePayments.getTotalElements())
                .totalPages(pageablePayments.getTotalPages())
                .build();
    }

    public PaymentResponse getPaymentById(Long id) {
        log.info("getPayment.start id: {}", id);
        Payment payment = fetchPaymentIfExist(id);
        log.info("getPayment.success id:{}", id);
        return mapEntityToResponse(payment);
    }

    public void updatePayment(Long id, PaymentRequest request) {
        log.info("updatePayment.start id:{}", id);
        Payment payment = fetchPaymentIfExist(id);
        payment.setDescription(request.getDescription());
        payment.setAmount(request.getAmount());
        paymentRepository.save(payment);
        log.info("updatePayment.success id:{}", id);
    }

    public void deletePaymentById(Long id) {
        log.info("deletePayment.start id: {}", id);
        paymentRepository.deleteById(id);
        log.info("deletePayment.start success: {}", id);
    }

    private Payment fetchPaymentIfExist(Long id) {
        return paymentRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
