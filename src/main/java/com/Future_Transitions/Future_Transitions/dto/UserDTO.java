package com.Future_Transitions.Future_Transitions.dto;

import com.Future_Transitions.Future_Transitions.model.Province;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String surname;
    private String address;
    private Province province;
    private String password;
    private String email;
    private int age;
    private int phoneNumber;
}
