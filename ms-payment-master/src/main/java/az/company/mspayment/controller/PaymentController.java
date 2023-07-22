package az.company.mspayment.controller;

import az.company.mspayment.model.request.PaymentCriteria;
import az.company.mspayment.model.request.PaymentRequest;
import az.company.mspayment.model.response.PageablePaymentResponse;
import az.company.mspayment.model.response.PaymentResponse;
import az.company.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(CREATED) // RESPONSE STATUS IS 201
    public void savePayment(@Valid @RequestBody PaymentRequest request) {
        paymentService.savePayment(request);
    }

    @GetMapping
    public PageablePaymentResponse getAllPayments(@RequestParam int page,
                                                  @RequestParam int count,
                                                  PaymentCriteria paymentCriteria) {

        return paymentService.getAllPayments(page,count,paymentCriteria);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @PutMapping("/edit/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody PaymentRequest request) {
            paymentService.updatePayment(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
    }

}
