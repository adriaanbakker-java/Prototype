package com.example.beheerleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Leveringsdoel {
    @Id
    @GeneratedValue
    private long id;
    private String leveringsdoel = "leveringsdoel invullen";

}
