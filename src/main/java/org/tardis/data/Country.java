package org.tardis.data;

import jakarta.persistence.*;

@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @Column(name = "ISO3", columnDefinition = "char")
    private String ISO3;

    @Column(name = "Display_Name", nullable = false)
    private String displayName;

    @Column(name = "Area_SqKm", columnDefinition = "decimal")
    private Double areaSqKm;

    @Column(name = "Population")
    private Integer population;

    @Column(name = "Capital")
    private String capital;

    @Column(name = "Continent")
    private String continent;

    public Country() {
    }

    public Country(String ISO3, String displayName, Double areaSqKm, Integer population, String capital, String continent) {
        this.ISO3 = ISO3;
        this.displayName = displayName;
        this.areaSqKm = areaSqKm;
        this.population = population;
        this.capital = capital;
        this.continent = continent;
    }

    public String getISO3() {
        return ISO3;
    }

    public void setISO3(String ISO3) {
        this.ISO3 = ISO3;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Double getAreaSqKm() {
        return areaSqKm;
    }

    public void setAreaSqKm(Double areaSqKm) {
        this.areaSqKm = areaSqKm;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
