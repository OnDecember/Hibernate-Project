package org.example.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "film_text", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmText implements EntityClass {

    @Id
    @Column(name = "film_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Film film;
}
