package ru.kravchenko.designer.repositiry;

import org.springframework.stereotype.Repository;
import ru.kravchenko.designer.entity.CityNic;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CityNicRepository {

    private List<CityNic> cityNicList = new ArrayList<>();

    @PostConstruct
    private void fillCityNicList() {
        cityNicList.add(new CityNic("astrakhan", 1, "Астраханть"));
        cityNicList.add(new CityNic("barnaul", 3, "Барнаул"));
        cityNicList.add(new CityNic("belgorod", 1, "Белгород"));
        cityNicList.add(new CityNic("bryansk", 1, "Брянск"));
        cityNicList.add(new CityNic("vladivostok", 2, "Владивосток"));
        cityNicList.add(new CityNic("volgograd", 1, "Волгоград"));
        cityNicList.add(new CityNic("vrn", 3, "Воронеж"));
        cityNicList.add(new CityNic("ekat", 8, "Екатеринбург"));
        cityNicList.add(new CityNic("izh", 1, "Ижевск"));
        cityNicList.add(new CityNic("irk", 2, "Иркутск"));
        cityNicList.add(new CityNic("kazan", 4, "Казань"));
        cityNicList.add(new CityNic("kalinin", 3, "Калининград"));
        cityNicList.add(new CityNic("kemerovo", 1, "Кемерово"));
        cityNicList.add(new CityNic("kirov", 1, "Киров"));
        cityNicList.add(new CityNic("krd", 5, "Краснодар"));
        cityNicList.add(new CityNic("krasnoyarsk", 3, "Красноярск"));
        cityNicList.add(new CityNic("kursk", 1, "Курск"));
        cityNicList.add(new CityNic("lipetsk", 1, "Липецк"));
        cityNicList.add(new CityNic("murmansk", 1, "Мурманск"));
    //    cityNic.add(new CityNic("", 18)); //msk
        cityNicList.add(new CityNic("chelny", 1, "Набережные челны"));
        cityNicList.add(new CityNic("nnov", 1, "Нижний Новгород"));
        cityNicList.add(new CityNic("nsk", 5, "Новосибирск"));
        cityNicList.add(new CityNic("oms", 2, "Омск"));
        cityNicList.add(new CityNic("orenburg", 1, "Оренбург"));
        cityNicList.add(new CityNic("orel", 1, "Орёл"));
        cityNicList.add(new CityNic("penza", 1, "Пенза"));
        cityNicList.add(new CityNic("perm", 2, "Пермь"));
        cityNicList.add(new CityNic("rostov", 2, "Ростов"));
        cityNicList.add(new CityNic("ryazan", 1, "Рязань"));
        cityNicList.add(new CityNic("samara", 3, "Самара"));
        cityNicList.add(new CityNic("spb", 8, "Санкт-Петербург"));
        cityNicList.add(new CityNic("saratov", 1, "Саратов"));
        cityNicList.add(new CityNic("simferopol", 2, "Симферополь"));
        cityNicList.add(new CityNic("sochi", 3, "Сочи"));
        cityNicList.add(new CityNic("stavropol", 2, "Ставрополь"));
        cityNicList.add(new CityNic("surgut", 1, "Сургут"));
        cityNicList.add(new CityNic("tver", 1, "Тверь")); // count 0 ))
        cityNicList.add(new CityNic("tolyatti", 1, "Тольяти"));
        cityNicList.add(new CityNic("tomsk", 2, "Томск"));
        cityNicList.add(new CityNic("tyumen", 5, "Тюмень"));
        cityNicList.add(new CityNic("ulyanovsk", 1, "Ульяновск"));
        cityNicList.add(new CityNic("ufa", 3, "Уфа"));
        cityNicList.add(new CityNic("khabarovsk", 2, "Хабаровск"));
        cityNicList.add(new CityNic("chel", 8, "Челябинск"));
        cityNicList.add(new CityNic("yar", 1, "Ярославль"));
    }

    public void showAll() {
        cityNicList.forEach(System.out::println);
    }

    public CityNic findByNic(String nic) {
        return cityNicList.stream().filter(x -> x.getCityNic().equals(nic)).findAny().orElse(null);
    }

    public List<String> getAllShortName () {
        List<String> result = new ArrayList<>();
        for (CityNic cityNic : cityNicList) {
            result.add(cityNic.getCityNic());
        }
        return result;
    }

}
