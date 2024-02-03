package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.interfaces.EntityClass;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "language", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Language implements EntityClass {

    @Id
    @Column(name = "language_id", nullable = false, columnDefinition = "tinyint")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false)
    @EqualsAndHashCode.Exclude
    private ZonedDateTime lastUpdate;
}