package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "address", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "address2", length = 50, nullable = false)
    private String address2;

    @Column(name = "district", length = 20, nullable = false)
    private String district;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private City city;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private ZonedDateTime lastUpdate;
}