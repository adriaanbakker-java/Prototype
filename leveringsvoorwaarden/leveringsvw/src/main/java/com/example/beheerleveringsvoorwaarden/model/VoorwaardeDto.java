package com.example.beheerleveringsvoorwaarden.model;

public class VoorwaardeDto {
    private long id;
    private String berichtnaam;
    private String leveringsdoel;
    private String padnaargegeven;

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