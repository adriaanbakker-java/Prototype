package com.example.beheerleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Voorwaarde {
    @Id
    @GeneratedValue
    private long id;
    private String berichtnaam = "berichtnaam invullen";
    private String leveringsdoel = "leveringsdoel invullen";
    private String padnaargegeven = "pad naar gegeven invullen";

}
