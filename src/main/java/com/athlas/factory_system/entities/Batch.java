package com.athlas.factory_system.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "batches")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Batch
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "production_date")
    private LocalDate productionDate;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "production_line_id")
    private ProductionLine productionLine;

    @OneToMany(mappedBy = "batch", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<ManufacturedProduct> products = new ArrayList<>();
}
