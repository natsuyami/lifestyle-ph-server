package com.lifestyle.ph.payment.repository;

import com.lifestyle.ph.payment.model.PaymentHistory;
import org.springframework.stereotype.Repository;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

@Repository
public interface UserDataRepository extends FirestoreReactiveRepository<PaymentHistory> {
}
