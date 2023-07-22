package az.company.mspayment.service.specification;

import az.company.mspayment.entity.Payment;
import az.company.mspayment.model.request.PaymentCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PaymentSpecification implements Specification<Payment> {

    private final PaymentCriteria paymentCriteria;
    private static final String AMOUNT = "amount";
    private static final String DESCRIPTION = "description";

    @Override
    public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (paymentCriteria != null) {
            if (paymentCriteria.getAmountFrom() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get(AMOUNT), paymentCriteria.getAmountFrom()));
            }

            if (paymentCriteria.getAmountTo() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get(AMOUNT), paymentCriteria.getAmountTo()));
            }

            if (StringUtils.hasText(paymentCriteria.getDescription())) {
                predicates.add(
                        criteriaBuilder.like(root.get(DESCRIPTION), "%" + paymentCriteria.getDescription()+"%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
