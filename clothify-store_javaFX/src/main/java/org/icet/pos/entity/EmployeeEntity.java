package org.icet.pos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employee")
public class EmployeeEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role="Employee";
    private Double salary;
    private String address;
    private String phoneNumber;
}
