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
    @Column(name = "film_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Film film;

    @Override
    @SuppressWarnings("all")
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}