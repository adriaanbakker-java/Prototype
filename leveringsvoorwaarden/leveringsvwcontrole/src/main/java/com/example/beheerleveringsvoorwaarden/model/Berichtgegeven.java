package com.example.beheerleveringsvoorwaarden.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Berichtgegeven {
    @Id
    @GeneratedValue
    private long id;
    private String padnaargegeven = "pad naar gegeven invullen";

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bericht_id", nullable = false)
    private Bericht bericht;
}
