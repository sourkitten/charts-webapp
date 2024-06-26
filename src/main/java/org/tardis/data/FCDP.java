package org.tardis.data;


import jakarta.persistence.*;

@Entity
@Table(name = "FC_Data")
public class FCDP extends DataPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISO3", columnDefinition = "char")
    protected char[] ISO3;
    @Column(name = "Year", columnDefinition = "int")
    private int year;
    @Column(name = "Value", columnDefinition = "double")
    private double value;
    @Column(name = "Indicator", columnDefinition = "char")
    private char[] indicator;


    public FCDP(char[] ISO3, int year, double value, char[] indicator){
        super(ISO3, year, value);
        this.indicator = indicator;
    }

}
