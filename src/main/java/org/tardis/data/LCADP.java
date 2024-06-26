package org.tardis.data;


import jakarta.persistence.*;

@Entity
@Table(name = "LCA_Data")
public class LCADP extends DataPoint {

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


    public LCADP(char[] ISO3, int year, double value, char[] indicator){
        super(ISO3, year, value);
        this.indicator = indicator;
    }

}
