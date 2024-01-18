package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.converter.RatingConverter;
import org.example.converter.SpecialFeatureConverter;
import org.example.enums.Rating;
import org.example.enums.SpecialFeature;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "film", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film implements EntityClass {

    @Id
    @Column(name = "film_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Language originalLanguage;

    @Column(name = "rental_duration", nullable = false)
    private Integer rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private Double rentalRate;

    @Column(name = "length")
    private Integer length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private Double replacementCost;

    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating;

    @Convert(converter = SpecialFeatureConverter.class)
    @Column(name = "special_features")
    private Set<SpecialFeature> features;

    @OneToOne(mappedBy = "film", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FilmText filmText;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Actor> actors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Category> categories;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private ZonedDateTime lastUpdate;
}