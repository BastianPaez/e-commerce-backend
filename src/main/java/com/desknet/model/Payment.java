package com.desknet.model;

import com.desknet.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentType;

    @NotNull
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToOne(mappedBy = "payment")
    private PurchaseOrder purchaseOrder;
}
