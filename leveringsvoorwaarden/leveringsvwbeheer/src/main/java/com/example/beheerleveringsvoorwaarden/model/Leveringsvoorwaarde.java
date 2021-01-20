package com.example.beheerleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Leveringsvoorwaarde {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "leveringsdoel_id", nullable = false)
    private Leveringsdoel leveringsdoel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "berichtgegeven_id", nullable = false)
    private Berichtgegeven berichtgegeven;

}
