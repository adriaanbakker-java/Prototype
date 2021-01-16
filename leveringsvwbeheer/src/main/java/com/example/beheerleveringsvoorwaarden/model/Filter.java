package com.example.beheerleveringsvoorwaarden.model;

public class Filter {
    public Filter(String berichtnaam) {
        this.berichtnaam = berichtnaam;
    }

    private String berichtnaam;

    public String getBerichtnaam() {
        return berichtnaam;
    }

    public void setBerichtnaam(String berichtnaam) {
        this.berichtnaam = berichtnaam;
    }

}
