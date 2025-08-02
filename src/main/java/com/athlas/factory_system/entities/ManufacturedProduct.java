package com.athlas.factory_system.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "manufactured_products")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManufacturedProduct
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "defective")
    private boolean defective;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    @ToString.Exclude
    private Batch batch;
}
