package org.icet.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerModel {
    private String id;
    private String name;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private String email;
    private String contact;

}
