package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "customer", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Store store;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean active;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Payment> payments;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rental> rentals;

    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;
}