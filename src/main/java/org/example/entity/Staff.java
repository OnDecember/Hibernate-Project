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
@Table(name = "staff", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    @Lob
    @Column(name = "picture", columnDefinition = "BLOB")
    @ToString.Exclude
    private byte[] picture;

    @Column(name = "email", length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Store store;

    @Column(name = "active", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean active = true;

    @Column(name = "username", length = 16, nullable = false)
    private String username;

    @Column(name = "password", length = 40)
    private String password;

    @OneToMany(mappedBy = "staff")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Payment> payments;

    @OneToMany(mappedBy = "staff")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Rental> rentals;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private ZonedDateTime lastUpdate;
}