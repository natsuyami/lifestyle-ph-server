package com.lifestyle.ph.payment.service;

import com.lifestyle.ph.common.exception.PaymentException;
import com.lifestyle.ph.payment.model.PaymentHistory;
import com.lifestyle.ph.payment.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private UserDataRepository userDataRepository;

    public String subscribePayment(PaymentHistory userData) {
        try {
            userDataRepository.save(userData).subscribe();
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<PaymentHistory> getPayment(String id) {
        try {
            LOGGER.info("Execute getPayment method, getting data of {}", id);

            return userDataRepository.findById(id).doOnSuccess(paymentDetails -> {
                            LOGGER.info("validating the status of user's payment");
                            if (null == paymentDetails || !"PAID".equals(paymentDetails.getStatus())) {
                                throw new PaymentException("user_not_paid");
                            }
                    })
                    .doOnError(error -> LOGGER.error("failed to emitted value", error));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
