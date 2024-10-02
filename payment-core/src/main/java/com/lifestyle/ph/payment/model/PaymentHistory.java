package com.lifestyle.ph.payment.model;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

@Document(collectionName = "PaymentHistory")
public class PaymentHistory {
    @Id
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private Date created;
    @Getter
    @Setter
    private Date updated;

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}