package com.desknet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private int quantity;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
