package com.example.beheerleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/* NB Voorwaarde een entiteit die de strings bevat voor berichtnaam, leveringsdoel en pad
   Dit om ervoor te zorgen dat er via een natural query de voorwaarden in de vorm van een lijst van objecten met deze strings
   uit de database kunnen worden gehaald via de repository VoorwaardenRepository
*/

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
