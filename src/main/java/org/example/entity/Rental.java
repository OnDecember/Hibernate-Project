package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "rental", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rental implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Long id;

    @Column(name = "rental_date", nullable = false)
    private ZonedDateTime rentalDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Inventory inventory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @Column(name = "return_date")
    private ZonedDateTime returnDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Staff staff;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private ZonedDateTime lastUpdate;
}