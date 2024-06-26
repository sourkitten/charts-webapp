package org.tardis.data;

import jakarta.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "ISO3", discriminatorType = DiscriminatorType.CHAR)
public class DataPoint {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISO3", columnDefinition = "char")
    protected char[] ISO3;
    @Column(name = "Year")
    protected int year;
    @Column(name = "Value", columnDefinition = "int")
    protected double value;

    public DataPoint(char[] ISO3, int year, double value) {
        this.ISO3 = ISO3;
        this.year = year;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + this.year + ", " + this.value + "]";
    }

    public char[] getISO3() {
        return ISO3;
    }

    public int getYear() {
        return year;
    }

    public double getValue() {
        return value;
    }
}
