package com.example.api.model.entitats;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class Rana {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String ubication;
    private String color;
    private String size;

}
