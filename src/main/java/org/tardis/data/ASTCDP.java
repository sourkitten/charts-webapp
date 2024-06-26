package org.tardis.data;


import jakarta.persistence.*;

@Entity
@Table(name = "ASTC_Data")
public class ASTCDP extends DataPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ISO3", columnDefinition = "char")
    private char[] ISO3;
    @Column(name = "Year", columnDefinition = "int")
    private int year;
    @Column(name = "Value", columnDefinition = "double")
    private double value;


    public ASTCDP(char[] ISO3, int year, double value){
        super(ISO3, year, value);
    }

}
