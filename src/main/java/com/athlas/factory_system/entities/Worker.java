package com.athlas.factory_system.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "workers")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Worker
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "paycheck")
    private BigDecimal paycheck;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    @ToString.Exclude
    private Facility facility;
}
