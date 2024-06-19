package org.icet.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order")
public class OrderEntity {
    @Id
    private String id;
    private String name;
}
