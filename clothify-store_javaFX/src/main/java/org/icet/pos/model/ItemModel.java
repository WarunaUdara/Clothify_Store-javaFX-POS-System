package org.icet.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemModel {
    private String id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private Integer qty;
}
