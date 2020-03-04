package ru.kravchenko.designer;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kravchenko.designer.api.ICreateExcelService;
import ru.kravchenko.designer.api.IParserService;
import ru.kravchenko.designer.api.IRequestService;
import ru.kravchenko.designer.entity.CityNic;
import ru.kravchenko.designer.entity.Designer;
import ru.kravchenko.designer.entity.DesignerStudio;
import ru.kravchenko.designer.repositiry.CityNicRepository;
import ru.kravchenko.designer.repositiry.DesignerRepository;
import ru.kravchenko.designer.repositiry.NodeRepository;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CreateExcelServiceTest {

    @Autowired
    ICreateExcelService createExcelService;

    @Autowired
    DesignerRepository designerRepository;

    @Autowired
    IParserService parserService;

    @Autowired
    CityNicRepository cityNicRepository;

    @Autowired
    IRequestService requestService;

    @Autowired
    NodeRepository nodeRepository;

    @Test
    public void testCreateExcelFile() throws InterruptedException {
        fillDesignerRepository();
        List<Designer> listDesigner = designerRepository.getAll();
        createExcelService.createFirstFile(listDesigner, "Астрахань");
    }

    private void fillDesignerRepository() throws InterruptedException {
        CityNic city = cityNicRepository.findByNic("astrakhan");
        String firstUrl = "https://" + city.getCityNic() + ".luxe-design.ru/designers";
        String secondUrl = "https://" + city.getCityNic() + ".luxe-design.ru/designers?page=";

        requestService.saveNodeListAnyCity(firstUrl, secondUrl, city.getCountPage());
        String urlNeedCity = "https://" + city.getCityNic() + ".luxe-design.ru/node/";

        for (String node : nodeRepository.getAll()) {
            parserService.saveDesignerToRepository(urlNeedCity + node);
        }

        while (!nodeRepository.getSize().equals(designerRepository.getSize())) {
            Thread.sleep(1000);
            System.out.println("######################");
            System.out.println("nodeRepository.getSize() " + nodeRepository.getSize());
            System.out.println("designerRepository.getSize() " + designerRepository.getSize());
            System.out.println("######################");

        }
        System.out.println("REPOSITORY COMPLITE!!!");
    }

    @Test
    public void testAppendToFileExcel() {
        for (String shortName : cityNicRepository.getAllShortName()) {

            CityNic city = cityNicRepository.findByNic(shortName);
            createExcelService.appendDataToFile(getAllDesignerFromCity(city.getCityNic()), city.getFullName());
        }

    }

    String firstUrl = "https://luxe-design.ru/";
    String secondUrl = "https://luxe-design.ru/designers?page=";

    @SneakyThrows
    private List<Designer> getAllDesignerFromCity(String nicCity) {
        CityNic city = cityNicRepository.findByNic(nicCity);
        String firstUrl = "https://" + city.getCityNic() + ".luxe-design.ru/designers";
        String firstUrlMsk = "https://luxe-design.ru/";
        String secondUrlMsk = "https://luxe-design.ru/designers?page=";

        String secondUrl = "https://" + city.getCityNic() + ".luxe-design.ru/designers?page=";

        //requestService.saveNodeListAnyCity(firstUrl, secondUrl, city.getCountPage());

        requestService.saveNodeListAnyCity(firstUrlMsk, secondUrlMsk, 18);
        String urlNeedCity = "https://" + city.getCityNic() + ".luxe-design.ru/node/";

        for (String node : nodeRepository.getAll()) {
            parserService.saveDesignerToRepository(urlNeedCity + node);
        }

        System.out.println("REPOSITORY COMPLITE!!!");
        System.out.println("city.getFullName() " + city.getFullName());

        return designerRepository.getAll();
    }

    @Test
    public void testAppendMsk() {
        createExcelService.appendDataToFile(getAllDesignerFrom(), "Москва");
    }

    @SneakyThrows
    private List<Designer> getAllDesignerFrom() {
        String firstUrlMsk = "https://luxe-design.ru/";
        String secondUrlMsk = "https://luxe-design.ru/designers?page=";


        requestService.saveNodeListAnyCity(firstUrlMsk, secondUrlMsk, 18);
//        String urlNeedCity = "https://" + city.getCityNic() + ".luxe-design.ru/node/";
        String urlNeedCity = "https://luxe-design.ru/node/";

        for (String node : nodeRepository.getAll()) {
            parserService.saveDesignerToRepository(urlNeedCity + node);
        }

        System.out.println("REPOSITORY COMPLITE!!!");

        return designerRepository.getAll();
    }

    private List<Designer> getAnyDesigner() {
        List<Designer> result = new ArrayList<>();
        List<String> socialNet = new ArrayList<>();
        socialNet.add("Facebook");

        result.add(new Designer("Ivan", "4444", socialNet, "eeee"));
        result.add(new Designer("Botan", "222", socialNet, "qqq"));
        result.add(new Designer("Kira", "11111", socialNet, "eeewwwe"));
        return result;
    }

    @Test
    public void showShortName() {
        cityNicRepository.getAllShortName().forEach(System.out::println);
    }

    @Test
    public void getAllNodeToDesinStudio() {
        // getAllNodeToStudio().forEach(System.out::println);
    }

    private void fillAllNodeToDisanerStudio(String shortName) {
        requestService.saveNodeListForStudio(shortName);
        for (String s : cityNicRepository.getAllShortName()) {
            requestService.saveNodeListForStudio(s);
        }
    }


//    private List<DesignerStudio> getAnyStudios(String shortName) {
//
//        fillAllNodeToDisanerStudio(shortName);
//
//        List<DesignerStudio> result = new ArrayList<>();
//        for (String s: nodeRepository.getAll()) {
//            result.add(getDesignerStudioByUrl(s));
//            System.out.println(getDesignerStudioByUrl(s));
//        }
//        return  result;
//    }

    @SneakyThrows
    private DesignerStudio getDesignerStudioByUrl(String url) {
        Document doc = Jsoup.connect(url).get();
        DesignerStudio designerStudio = new DesignerStudio();
        designerStudio.setName(doc.getElementById("page-title").text());

        Elements div = doc.getElementsByClass("company-property");
        for (Element element : div) {
            System.out.println(element.text());
            designerStudio.setUrl(element.text());
        }
        return designerStudio;
    }


    @SneakyThrows
    public void testCreateExcelFileWithDesignerStudio(List<DesignerStudio> designerStudios) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");


        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("No.");
        rowhead.createCell(1).setCellValue("Имя");
        rowhead.createCell(2).setCellValue("Сайт");
        rowhead.createCell(3).setCellValue("Город");

        int count = 1;
        for (DesignerStudio designer : designerStudios) {
            HSSFRow row = sheet.createRow((short) count);
            row.createCell(0).setCellValue(count);
            row.createCell(1).setCellValue(designer.getName());
            row.createCell(2).setCellValue(designer.getUrl());
            row.createCell(3).setCellValue("Астрахань");
            count++;
        }

        FileOutputStream fileOut = new FileOutputStream("allStudios.xls");
        workbook.write(fileOut);
        fileOut.close();
//             workbook.close(); TODO разоброаться как закрывать поток
        System.out.println("Your excel file has been generated!");
    }

    @Test
    public void createFileExcelFirst() {
        //https://astrakhan.luxe-design.ru/designstudios
        //https://astrakhan.luxe-design.ru/node/19827
        String shortNsm = "astrakhan";
        requestService.saveNodeListForStudio(shortNsm);

        List<DesignerStudio> re = new ArrayList<>();
        for (String s : nodeRepository.getAll()) {
            DesignerStudio designer = getDesignerStudioByUrl("https://" + shortNsm + ".luxe-design.ru/node/" + s);
            re.add(designer);
        }

        testCreateExcelFileWithDesignerStudio(re);
    }

    @Test
    public void createFileExcelSecond() {
        CityNic cityNic = cityNicRepository.findByNic("yar");
        requestService.saveNodeListForStudio(cityNic.getCityNic());

        List<DesignerStudio> re = new ArrayList<>();
        for (String s : nodeRepository.getAll()) {
            DesignerStudio designer = getDesignerStudioByUrl("https://" + cityNic.getCityNic() + ".luxe-design.ru/node/" + s);
            re.add(designer);
        }

        createExcelService.appendDataToFileStudio(re, cityNic.getFullName());
    }

    //    https://luxe-design.ru/node/26370
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

    public List<Designer> getMskDesigner() {
        String mskUrl = "https://luxe-design.ru/node/";
        List<Designer> mskDesigner = new ArrayList<>();
        for (String s : getNodeMskWithPhone()) {
            Designer designer = parserService.getDesignerByUrl(mskUrl + s);
            mskDesigner.add(designer);
        }
        return mskDesigner;
    }

    @Test
    public void testGetMskDesignerFromMsk() {
        createExcelService.appendDataToFile(getMskDesigner(), "Москва");
    }

}
