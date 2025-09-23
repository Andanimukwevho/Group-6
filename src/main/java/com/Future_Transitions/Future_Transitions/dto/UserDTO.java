package com.Future_Transitions.Future_Transitions.dto;

import com.Future_Transitions.Future_Transitions.model.Province;
import com.Future_Transitions.Future_Transitions.model.Role;
import java.util.List;
import lombok.Data;

@Data
public class UserDTO {

    private String name;
    private String surname;
    private String address;
    private Province province;
    private String password;
    private String email;
    private Integer age;
    private String phoneNumber;
    private Role role;
    private List<ApplicationDTO> appliedJobs;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ApplicationDTO> getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(List<ApplicationDTO> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

}
