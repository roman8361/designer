package ru.kravchenko.designer.service;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.kravchenko.designer.api.IParserService;
import ru.kravchenko.designer.entity.Designer;
import ru.kravchenko.designer.repositiry.DesignerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService implements IParserService {

    @Autowired
    DesignerRepository designerRepository;

    @Override
    @SneakyThrows

    public Designer getDesignerByUrl(String url) {
        Designer designer = new Designer();
        Document doc = Jsoup.connect(url).get();
        designer.setName(doc.getElementById("page-title").text().substring(19)); // обрезаем начало строки, оставляем только ФИО
        Elements div = doc.getElementsByClass("company-property");
        List<String> socialNet = new ArrayList<>();
        for (Element element : div) {
            socialNet.add(element.text());
        }

        designer.setSocialNets(socialNet);
        Elements phones = doc.getElementsByClass("inline-block");
        Element phone = phones.first();
        designer.setTelephone(getPhone(phone.toString()));

        System.out.println("Designer " + designer.getName() + " add to list");
        System.out.println("Thread.currentThread().getId() - " + Thread.currentThread().getId());
        designerRepository.insert(designer);
        return designer;
    }

    private String getPhone(String text) {
        String result = "";
        String[] subStr;
        String delimeter = "data-content=";
        subStr = text.split(delimeter);
        for (int i = 0; i < subStr.length; i++) {
            if (i == 1) {
                result = getNumberFromStringUtil(subStr[i].substring(0, 20));
                return result;
            }
        }
        return null;
    }

    private String getNumberFromStringUtil(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c) || c == '-') result.append(c);
        }
        return result.toString();
    }


    public void asyncMethod() {
        System.out.println("AsyncComponent");
        System.out.println("Thread.currentThread().getId() " + Thread.currentThread().getId());
        System.out.println("Thread.currentThread().getName() " + Thread.currentThread().getName());
    }

    @Override
    @SneakyThrows
    public void saveDesignerToRepository(String url) {
        Designer designer = new Designer();
        Document doc = Jsoup.connect(url).get();
        designer.setName(doc.getElementById("page-title").text().substring(19)); // обрезаем начало строки, оставляем только ФИО
        Elements div = doc.getElementsByClass("company-property");
        List<String> socialNet = new ArrayList<>();
        for (Element element : div) {
            socialNet.add(element.text());
        }

        designer.setSocialNets(socialNet);

        Elements phones = doc.getElementsByClass("inline-block");
        Element phone = phones.first();
        designer.setTelephone(getPhone(phone.toString()));

        System.out.println("Designer " + designer.getName() + " add to list");
        System.out.println("Thread.currentThread().getId() - " + Thread.currentThread().getId());
        designerRepository.insert(designer);
    }

}
