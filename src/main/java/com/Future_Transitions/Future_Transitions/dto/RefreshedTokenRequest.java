package com.Future_Transitions.Future_Transitions.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class RefreshedTokenRequest {


    private String refreshedToken;

    public String getRefreshedToken() {
        return refreshedToken;
    }

    public void setRefreshedToken(String refreshedToken) {
        this.refreshedToken = refreshedToken;
    }


}
