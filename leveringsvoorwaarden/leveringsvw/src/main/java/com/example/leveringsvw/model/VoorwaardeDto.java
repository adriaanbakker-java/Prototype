package com.example.leveringsvw.model;

public class VoorwaardeDto {
    private long id;
    private String berichtnaam;
    private String leveringsdoel;

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