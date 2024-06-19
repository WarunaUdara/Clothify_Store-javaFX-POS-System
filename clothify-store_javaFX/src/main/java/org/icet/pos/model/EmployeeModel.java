package org.icet.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role="Employee";
    private Double salary;
    private String address;
    private String phoneNumber;

}
