package com.example.checkleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Request {
    private String OID;
    private String leveringsdoel;
    private String berichtnaam;
    ArrayList <String> berichtelementen;
}
