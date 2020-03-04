package ru.kravchenko.designer.entity;

import lombok.Getter;

@Getter
public class CityNic extends AbstractEntity {

    private String cityNic;

    private int countPage;

    private String fullName;

    public CityNic(String cityNic, int countPage, String fullName) {
        this.cityNic = cityNic;
        this.countPage = countPage;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "CityNic{" +
                "cityNic='" + cityNic + '\'' +
                ", countPage=" + countPage +
                '}';
    }

}
