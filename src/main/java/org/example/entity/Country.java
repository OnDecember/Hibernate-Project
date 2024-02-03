package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country implements EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false, columnDefinition = "smallint")
    private Long id;

    @Column(name = "country", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<City> cities = new HashSet<>();

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    @EqualsAndHashCode.Exclude
    private ZonedDateTime lastUpdate;
}