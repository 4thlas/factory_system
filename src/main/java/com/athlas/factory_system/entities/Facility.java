package com.athlas.factory_system.entities;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.Manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facilities")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Facility
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "location")
    private String location;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Worker manager;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "facility")
    @Builder.Default
    @ToString.Exclude
    private List<Worker> workers = new ArrayList<>();

    @OneToMany(mappedBy = "facility", fetch = FetchType.EAGER)
    @Builder.Default
    //@ToString.Exclude
    private List<ProductionLine> productionLines = new ArrayList<>();
}
