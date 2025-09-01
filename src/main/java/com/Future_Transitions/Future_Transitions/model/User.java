package com.Future_Transitions.Future_Transitions.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user-table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long Id;



    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Surname is required")
    private String surname;
    @NotBlank(message = "Address is required")
    private String address;
    @Enumerated(EnumType.STRING)
    private Province province;
    //    @NotNull(message = "student result are needed")
//    private double universityresult;
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a digit, and a special character")
    private String password;
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    @NotNull(message = "Age is required")
    private Integer age;
    @NotBlank(message = "Phone number is required")
    private String phonenumber;


}
