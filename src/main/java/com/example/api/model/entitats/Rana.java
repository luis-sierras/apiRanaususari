package com.example.api.model.entitats;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Rana {
    @Id
    private int id;
    private String cientificName;
    private String ubication;
    private String color;
    private String size;


}
