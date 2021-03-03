package com.example.matrix_countriestable_noambrauner.Entity;

public class Country implements Comparable<Country>{
    String name;
    String nativeName;
    String[] borders;
    String alpha3Code;
    String area;

    public Country() {
        name = "";
        nativeName = "";
        borders = new String[1];
        alpha3Code = "";
        area = "";

    }

    public Country(String name1, String nativeName1, String[] borders1, String alpha3Code1, String area1) {
        name = name1;
        nativeName = nativeName1;
        borders = borders1;
        alpha3Code = alpha3Code1;
        area = area1;
    }

    public String getName() {
       return name;
    }
    public String getNativeName() {
        return nativeName;
    }
    public String getArea() {
        return area;
    }
    public String getAlpha3Code()
    {
        return alpha3Code;
    }

    public String[] getBorders() {
        return borders;
    }

    @Override
    public int compareTo(Country country) {

        double d=Double.parseDouble(area);
        double countryArea=Double.parseDouble(country.getArea());

        if (d == countryArea)
            return 0;
        else if (d > countryArea)
            return 1;
        else
            return -1;
    }
}
