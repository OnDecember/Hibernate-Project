package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rental", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
}
