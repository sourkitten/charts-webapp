package org.tardis.model;

import jakarta.persistence.*;

//@Entity
//@Table(name="Currencies")
public class Currencies {

    //@Id
    //@GeneratedValue(strategy=GenerationType.IDENTITY)
    //@Column(name="ISO3", columnDefinition = "char")
    private char[] ISO3;

    private int year;

    private double value;


    public Currencies(char[] iso3, int year, double value) {
        ISO3 = iso3;
        this.year = year;
        this.value = value;
    }
}
