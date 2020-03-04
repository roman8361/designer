package ru.kravchenko.designer.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignerStudio extends AbstractEntity {

    private String name;

    private String url;

    private String city;

    @Override
    public String toString() {
        return "DesignerStudio{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
