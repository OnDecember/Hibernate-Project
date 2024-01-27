package org.example.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;

@Entity
@Table(name = "film_text", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmText implements EntityClass {

    @Id
    @Column(name = "film_id", nullable = false, columnDefinition = "smallint")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false, columnDefinition = "smallint")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Film film;
}