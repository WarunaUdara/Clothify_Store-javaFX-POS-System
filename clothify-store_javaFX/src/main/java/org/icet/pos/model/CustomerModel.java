package org.icet.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerModel {
    private String id;
    private String name;
    private Date dob;
    private String address;
    private String city;
    private String province;
    private String postalCode;

}
