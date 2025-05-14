package com.taron.stocks.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stocks", schema = "projet_gestion_stock")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock")
    private int id;

    @Column(name = "product", nullable = false)
    private int productId;

    @Column(name = "id_enterprise", nullable = false)
    private int enterpriseId;

    @Column(name = "address", nullable = false, length = 50)
    private String address;
}
