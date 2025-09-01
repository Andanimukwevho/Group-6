package com.Future_Transitions.Future_Transitions.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="application-table")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long Id;
    private Province province;
}
