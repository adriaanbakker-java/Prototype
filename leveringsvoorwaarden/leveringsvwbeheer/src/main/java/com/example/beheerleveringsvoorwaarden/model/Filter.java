package com.example.beheerleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Filter {
    public Filter(String berichtnaam, String leveringsdoel) {
        this.berichtnaam = berichtnaam;
    }

    private String berichtnaam;
    private String leveringsdoel;


}
