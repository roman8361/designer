package ru.kravchenko.designer;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.kravchenko.designer.entity.CityNic;
import ru.kravchenko.designer.entity.Designer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Kravchenko
 */
public class JsoupTest {

    private String url = "https://luxe-design.ru/designers?page=";

    private String urlAstrahan = "https://astrakhan.luxe-design.ru/node/";

    private List<CityNic> cityNicList;

//    private String urlFirstBarnaul = "https://barnaul.luxe-design.ru/designers";
//
//    private String urlSecondBarnaul = "https://barnaul.luxe-design.ru/designers?page=";

    private String urlDesigner = "https://luxe-design.ru/node/";

    private List<String> nodeList = new ArrayList<>();

    private List<Designer> allDesigner = new ArrayList<>();

    private String node = "<div class=\"image\"> \n" +
            " <a href=\"/node/12660\"><img class=\"img-rounded\" src=\"https://luxe-design.ru/sites/default/files/styles/profile_teaser/public/foto/rvxyl89birc.jpg?itok=hY6Z4qeI\" width=\"100\" height=\"100\" alt=\"\"></a> \n" +
            "</div>";

    @Test
    public void addNodeToList() {
        String result = "";
        String[] subStr;
        String delimeter = "node/";
        subStr = node.split(delimeter);
        for (int i = 0; i < subStr.length; i++) {
            if (i == 1) {
                result = getNumberFromStringUtil(subStr[i].substring(0, 7));
                System.out.println(result);
            }
        }
    }

    @Test
    @SneakyThrows
    public void testJsoup() {
        Designer designer = getDesigner("https://luxe-design.ru/node/11670");
        System.out.println(designer);
    }

    private void fillDesignerList() {
        fillAllNode();
        for (String s : nodeList) {
            allDesigner.add(getDesigner(urlDesigner + s));
        }
    }

    @SneakyThrows
    private Designer getDesigner(String url) {
        Designer designer = new Designer();
        Document doc = Jsoup.connect(url).get();
        designer.setName(doc.getElementById("page-title").text().substring(19)); // обрезаем начало строки, оставляем только ФИО
        Elements div = doc.getElementsByClass("company-property");
        List<String> socialNet = new ArrayList<>();

        for (Element element : div) {
            socialNet.add(element.text());
        }

        designer.setSocialNets(socialNet);
        Elements phones = doc.getElementsByClass("btn btn-success btn-sm phone-button");
        Element phone = phones.last();
        String phoneString = phone.attr("data-content"); // telephone
        designer.setTelephone(phoneString);
        System.out.println("Designer " + designer.getName() + " add to list");
        return designer;
    }

    private String getNumberFromStringUtil(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) result.append(c);
        }
        return result.toString();
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

    @SneakyThrows
    private void fillNodeFromOnePage(String url) {
        Document doc = Jsoup.connect(url).get();
        Elements div = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
        for (Element element : div) {
            nodeList.add(getNode(element.getElementsByClass("image").toString()));
        }
    }

    private void fillAllNode() {
        for (int i = 1; i < 20; i++) {
            fillNodeFromOnePage(url + i);
        }
        System.out.println("Node List is filling, size: " + nodeList.size());
    }

    @Test
    @SneakyThrows
    public void testCreateFile() {
        //  fillDesignerList();
        createExcelFileForDesigner("designer3.xls", getAnyDesigner());
//        createExcelFileForDesigner("designer2.xls", getAllDesigner());
    }

    @Test
    @SneakyThrows
    public void testCreateFileWithTelephone() {
        List<CityNic> cityNicList = getCityNic();
        CityNic belgorod = cityNicList.get(2);

        String city = "yar";
        createExcelFileForDesigner("designer-" + belgorod.getCityNic() + " .xls",
                getAllDesigner(city, belgorod.getCountPage()));
    }

    private List<CityNic> getCityNic() {
        List<CityNic> cityNic = new ArrayList<>();
        cityNic.add(new CityNic("astrakhan", 1, "Астраханть"));
        cityNic.add(new CityNic("barnaul", 3, "Барнаул"));
        cityNic.add(new CityNic("belgorod", 1, "Белгород"));
        cityNic.add(new CityNic("bryansk", 1, "Брянск"));
        cityNic.add(new CityNic("vladivostok", 2, "Владивосток"));
        cityNic.add(new CityNic("volgograd", 1, "Волгоград"));
        cityNic.add(new CityNic("vrn", 3, "Воронеж"));
        cityNic.add(new CityNic("ekat", 8, "Екатеринбург"));
        cityNic.add(new CityNic("izh", 1, "Ижевск"));
        cityNic.add(new CityNic("irk", 2, "Иркутск"));
        cityNic.add(new CityNic("kazan", 4, "Казань"));
        cityNic.add(new CityNic("kalinin", 3, "Калининград"));
        cityNic.add(new CityNic("kemerovo", 1, "Кемерово"));
        cityNic.add(new CityNic("kirov", 1, "Киров"));
        cityNic.add(new CityNic("krd", 5, "Краснодар"));
        cityNic.add(new CityNic("krasnoyarsk", 3, "Красноярск"));
        cityNic.add(new CityNic("kursk", 1, "Курск"));
        cityNic.add(new CityNic("lipetsk", 1, "Липецк"));
        cityNic.add(new CityNic("murmansk", 1, "Мурманск"));
//        cityNic.add(new CityNic("", 18)); //msk
        cityNic.add(new CityNic("chelny", 1, "Набережные челны"));
        cityNic.add(new CityNic("nnov", 1, "Нижний Новгород"));
        cityNic.add(new CityNic("nsk", 5, "Новосибирск"));
        cityNic.add(new CityNic("oms", 2, "Омск"));
        cityNic.add(new CityNic("orenburg", 1, "Оренбург"));
        cityNic.add(new CityNic("orel", 1, "Орёл"));
        cityNic.add(new CityNic("penza", 1, "Пенза"));
        cityNic.add(new CityNic("perm", 2, "Пермь"));
        cityNic.add(new CityNic("rostov", 2, "Ростов"));
        cityNic.add(new CityNic("ryazan", 1, "Рязань"));
        cityNic.add(new CityNic("samara", 3, "Самара"));
        cityNic.add(new CityNic("spb", 8, "Санкт-Петербург"));
        cityNic.add(new CityNic("saratov", 1, "Саратов"));
        cityNic.add(new CityNic("simferopol", 2, "Симферополь"));
        cityNic.add(new CityNic("sochi", 3, "Сочи"));
        cityNic.add(new CityNic("stavropol", 2, "Ставрополь"));
        cityNic.add(new CityNic("surgut", 1, "Сургут"));
        cityNic.add(new CityNic("tver", 1, "Тверь")); // count 0 ))
        cityNic.add(new CityNic("tolyatti", 1, "Тольяти"));
        cityNic.add(new CityNic("tomsk", 2, "Томск"));
        cityNic.add(new CityNic("tyumen", 5, "Тюмень"));
        cityNic.add(new CityNic("ulyanovsk", 1, "Ульяновск"));
        cityNic.add(new CityNic("ufa", 3, "Уфа"));
        cityNic.add(new CityNic("khabarovsk", 2, "Хабаровск"));
        cityNic.add(new CityNic("chel", 8, "Челябинск"));
        cityNic.add(new CityNic("yar", 1, "Ярославль"));


        return cityNic;
    }

    @Test
    @SneakyThrows
    public void testCreateFileWithoutTelephone() {
        fillDesignerList();
        createExcelFileForDesigner("designer-msk.xls", allDesigner);
    }

    @SneakyThrows
    public void createExcelFileForDesigner(String fileName, List<Designer> designers) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("No.");
        rowhead.createCell(1).setCellValue("Name");
        rowhead.createCell(2).setCellValue("Phone");
        rowhead.createCell(3).setCellValue("instagram");
        rowhead.createCell(4).setCellValue("vkontakte");
        rowhead.createCell(5).setCellValue("facebook");
        rowhead.createCell(6).setCellValue("ok");
        rowhead.createCell(7).setCellValue("twitter");
        rowhead.createCell(8).setCellValue("page");

        int count = 1;
        for (Designer designer : designers) {
            HSSFRow row = sheet.createRow((short) count);
            row.createCell(0).setCellValue(count);
            row.createCell(1).setCellValue(designer.getName());
            row.createCell(2).setCellValue(designer.getTelephone());

            //         int countSocial = 3;
            for (String s : designer.getSocialNets()) {
                if (s.contains("instagram")) {
                    row.createCell(3).setCellValue(s);
                    continue;
                }
                if (s.contains("vk")) {
                    row.createCell(4).setCellValue(s);
                    continue;
                }
                if (s.contains("facebook")) {
                    row.createCell(5).setCellValue(s);
                    continue;
                }
                if (s.contains("ok")) {
                    row.createCell(6).setCellValue(s);
                    continue;
                }
                if (s.contains("twitter")) {
                    row.createCell(7).setCellValue(s);
                } else row.createCell(8).setCellValue(s);

//                row.createCell(countSocial).setCellValue(s);
//                countSocial++;
            }
            count++;
        }
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
//             workbook.close(); TODO разоброаться как закрывать поток
        System.out.println("Your excel file has been generated!");
    }

    List<Designer> getAnyDesigner() {
        List<Designer> result = new ArrayList<>();
        Designer designer1 = new Designer();
        designer1.setTelephone("3333");
        designer1.setName("Ivan");
        List<String> designer1List = new ArrayList<>();
        designer1List.add("facebook");
        designer1List.add("vkkontakt");
        designer1.setSocialNets(designer1List);
        result.add(designer1);

        Designer designer2 = new Designer();
        designer2.setTelephone("444");
        designer2.setName("Branan");
        List<String> designer2List = new ArrayList<>();
        designer2List.add("facebook");
        designer2List.add("www.torololo.ru");
        designer2.setSocialNets(designer2List);
        result.add(designer2);

        return result;
    }

    private List<Designer> getAllDesigner() {
        List<Designer> result = new ArrayList<>();
        for (String s : getNodeAstrahan()) {
            Designer designer = getDesigner(urlAstrahan + s);
            result.add(designer);
        }
        return result;
    }

    private List<Designer> getAllDesigner(String city, int count) {
        //private String urlAstrahan = "https://astrakhan.luxe-design.ru/node/";
//        private String urlFirstBarnaul = "https://barnaul.luxe-design.ru/designers";
//
//        private String urlSecondBarnaul = "https://barnaul.luxe-design.ru/designers?page=";
        String urlNeedCity = "https://" + city +".luxe-design.ru/node/";
        String firstUrl = "https://" + city + ".luxe-design.ru/designers";
        String secondUrl = "https://" + city + ".luxe-design.ru/designers?page=";
        List<Designer> result = new ArrayList<>();
        for (String s : getNodeListAnyCity(firstUrl, secondUrl, count)) {
            Designer designer = getDesigner(urlNeedCity + s);
            result.add(designer);
        }
        return result;
    }

    List<String> getNodeMskWithPhone() {
        List<String> result = new ArrayList<>();
        result.add("7272");
        result.add("28784");
        result.add("28452");
        result.add("27882");
        result.add("6328");
        result.add("28089");
        result.add("15857");
        result.add("6328");
        result.add("20139");
        result.add("28768");
        result.add("11350");
        result.add("5465");
        result.add("11470");
        result.add("21892");
        result.add("26370");
        result.add("28814");
        result.add("11670");
        result.add("27500");
        result.add("28991");

        return result;
    }

    @Test
    public void testCheckSocialNet() {
        for (String s : getSocialNet()) {
            if (s.contains("vk")) System.out.println("vk");
            if (s.contains("face")) System.out.println("face");
        }
    }

    private List<String> getSocialNet() {
        List<String> socialNet = new ArrayList<>();
        socialNet.add("http://as-version.ru/arkhitektura/");
        socialNet.add("https://vk.com/asversion");
        socialNet.add("https://www.facebook.com/profile.php?id=100015528670909");
        socialNet.add("https://twitter.com/?lang=ru");
        socialNet.add("https://ok.ru/group/58238606770232");
        return socialNet;
    }

    @Test
    public void testSplitString() {
        String s = "Дизайнер интерьера Татьяна Соняк".substring(19);

        System.out.println(s);
    }


    @SneakyThrows
    public List<String> getNodeAstrahan() {
        Document doc = Jsoup.connect("https://astrakhan.luxe-design.ru/designers").get();
        Elements div = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
        List<String> node = new ArrayList<>();
//        Set<String> node = new HashSet<>();
        for (Element element : div) {
            node.add(getNode(element.getElementsByClass("image").toString()));
        }
        doc = Jsoup.connect("https://astrakhan.luxe-design.ru/designers?page=1").get();
        div = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
        for (Element element : div) {
            node.add(getNode(element.getElementsByClass("image").toString()));
        }
        return node;
    }

    @Test
    public void testGetNode() {
//        nodeList = getNodeListAnyCity(urlFirstBarnaul, urlSecondBarnaul, 3);
//
//        System.out.println(nodeList.size());
//        nodeList.forEach(System.out::println);
    }

    @SneakyThrows
    private List<String> getNodeListAnyCity(String firstUrl, String secondUrl, Integer countPage) {
        List<String> nodeListAnyCity = new ArrayList<>();
        Document doc = Jsoup.connect(firstUrl).get();
        Elements div = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
        for (Element element : div) {
            nodeListAnyCity.add(getNode(element.getElementsByClass("image").toString()));
        }
        for (int i = 1; i < countPage + 1; i++) {
            doc = Jsoup.connect(secondUrl + i).get();
            Elements div2 = doc.getElementsByClass("designer-teaser designer-on-list-page clearfix");
            for (Element element : div2) {
                nodeListAnyCity.add(getNode(element.getElementsByClass("image").toString()));
            }
        }
        return nodeListAnyCity;
    }

}
