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
@Table(name = "inventory", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id", nullable = false)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Film film;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id", nullable = false)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Store store;

    @OneToMany(mappedBy = "inventory")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Rental> rentals;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private ZonedDateTime lastUpdate;
}