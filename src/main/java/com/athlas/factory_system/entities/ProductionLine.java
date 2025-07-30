package com.athlas.factory_system.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "production_lines")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductionLine
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    @ToString.Exclude
    Facility facility;

    @Column(name = "produced_per_month")
    private BigDecimal producedPerMonth;
}
