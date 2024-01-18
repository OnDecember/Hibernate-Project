package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "film_actor", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmActor implements EntityClass, Serializable {

    @Id
    @Column(name = "actor_id", nullable = false)
    private Long actorId;

    @Id
    @Column(name = "film_id", nullable = false)
    private Long filmId;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    private ZonedDateTime lastUpdate;
}
