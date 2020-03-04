package ru.kravchenko.designer.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Designer extends AbstractEntity {

    private String name;

    private String telephone;

    private List<String> socialNets;

    private String position = "Дизайнер интерьера";

    @Override
    public String toString() {
        return "Designer{" +
                "name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", socialNets=" + socialNets +
                ", position='" + position + '\'' +
                '}';
    }

    public Designer(String name, String telephone, List<String> socialNets, String position) {
        this.name = name;
        this.telephone = telephone;
        this.socialNets = socialNets;
        this.position = position;
    }

    public Designer() { }

}
