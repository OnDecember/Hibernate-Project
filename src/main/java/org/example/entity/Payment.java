package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "payment", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, columnDefinition = "smallint")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Staff staff;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Rental rental;

    @Column(name = "amount", nullable = false, precision = 5, scale = 2, columnDefinition = "decimal(5,2)")
    private Double amount;

    @Column(name = "payment_date", nullable = false)
    private ZonedDateTime paymentDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    @EqualsAndHashCode.Exclude
    private ZonedDateTime lastUpdate;
}