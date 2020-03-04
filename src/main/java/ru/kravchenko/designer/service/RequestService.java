package ru.kravchenko.designer.service;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kravchenko.designer.api.IRequestService;
import ru.kravchenko.designer.repositiry.NodeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService implements IRequestService {

    @Autowired
    private NodeRepository nodeRepository;

    @Override
    @SneakyThrows
    public String getHtmlByUrl(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.toString();
    }

    @Override
    @SneakyThrows
    public void saveNodeListAnyCity(String firstUrl, String secondUrl, Integer countPage) {
        List<String> nodeListAnyCity = new ArrayList<>();
        Document doc = Jsoup.connect(firstUrl).get();    //designer-teaser designer-promo clearfix
        Elements div = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
        for (Element element : div) {
            nodeRepository.insert(getNode(element.toString()));
        }

        div = doc.getElementsByClass("designer-teaser designer-on-list-page designer-promo clearfix");
        for (Element element : div) {
            nodeRepository.insert(getNode(element.toString()));
        }

        for (int i = 1; i < countPage + 1; i++) {
            doc = Jsoup.connect(secondUrl + i).get();
            Elements div2 = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
            for (Element element : div2) {
                nodeRepository.insert(getNode(element.toString()));
            }
        }
        nodeRepository.insertAll(nodeListAnyCity);
    }

    @Override
    @SneakyThrows
    public void saveNodeListForStudio(String shortName) {
        String url = "https://" + shortName + ".luxe-design.ru/designstudios";
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.getElementsByClass("designer-teaser clearfix");
        for (Element element : rows) {
            try {
                System.out.println(getNode(element.toString()));
                nodeRepository.insert(getNode(element.toString()));
            } catch (NullPointerException | IndexOutOfBoundsException e1) {
                System.out.println("contactId not found");
            }
        }
    }

    private String getNode(String text) {
        String result = "";
        String[] subStr;
        String delimeter = "node/";
        subStr = text.split(delimeter);
        for (int i = 0; i < subStr.length; i++) {
            if (i == 1) {
                result = getNumberFromStringUtil(subStr[i].substring(0, 7));
                return result;
            }
        }
        return null;
    }

    private String getNumberFromStringUtil(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) result.append(c);
        }
        return result.toString();
    }


    public void asyncMethod() {
        System.out.println("AsyncComponent");
        System.out.println("Thread.currentThread().getId() " + Thread.currentThread().getId());
        System.out.println("Thread.currentThread().getName() " + Thread.currentThread().getName());
    }

}
