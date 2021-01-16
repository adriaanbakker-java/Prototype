package com.example.beheerleveringsvoorwaarden.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Voorwaarde {
    @Id
    @GeneratedValue
    private long id;
    private String berichtnaam = "berichtnaam invullen";
    private String leveringsdoel = "leveringsdoel invullen";
    private String padnaargegeven = "pad naar gegeven invullen";

    public String getPadnaargegeven() {
        return padnaargegeven;
    }

    public void setPadnaargegeven(String padnaargegeven) {
        this.padnaargegeven = padnaargegeven;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getBerichtnaam() {
        return berichtnaam;
    }
    public void setBerichtnaam(String berichtnaam) {
        this.berichtnaam = berichtnaam;
    }

    public String getLeveringsdoel() {
        return leveringsdoel;
    }
    public void setLeveringsdoel(String leveringsdoel) {
        this.leveringsdoel = leveringsdoel;
    }


}
