package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(name = "store", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "manager_staff_id", nullable = false, unique = true)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Staff manager;

    @OneToOne
    @JoinColumn(name = "address_id")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    @OneToMany(mappedBy = "store")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Staff> employees;

    @OneToMany(mappedBy = "store")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Inventory> inventories;

    @UpdateTimestamp
    @Column(name = "last_update")
    private ZonedDateTime lastUpdate;
}