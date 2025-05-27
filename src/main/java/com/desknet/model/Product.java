package com.desknet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    private int stock;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItemList;

    @OneToMany(mappedBy = "product")
    private List<PurchaseOrderItem> purchaseOrderItemList;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;




}
