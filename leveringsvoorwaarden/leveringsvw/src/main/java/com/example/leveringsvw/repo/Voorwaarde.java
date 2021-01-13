package com.example.leveringsvw.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Voorwaarde {
    @Id
    @GeneratedValue
    private long id;
    private String berichtnaam = "berichtnaam invullen";
    private int age;
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
