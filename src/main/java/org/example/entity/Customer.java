package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.interfaces.EntityClass;

@Entity
@Table(name = "customer", schema = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements EntityClass {
}
