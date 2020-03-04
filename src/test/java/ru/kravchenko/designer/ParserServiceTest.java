package ru.kravchenko.designer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kravchenko.designer.api.IParserService;
import ru.kravchenko.designer.api.IRequestService;
import ru.kravchenko.designer.entity.CityNic;
import ru.kravchenko.designer.entity.Designer;
import ru.kravchenko.designer.repositiry.CityNicRepository;
import ru.kravchenko.designer.repositiry.DesignerRepository;
import ru.kravchenko.designer.repositiry.NodeRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ParserServiceTest {

    @Autowired
    private IParserService parserService;

    @Autowired
    private IRequestService requestService;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private CityNicRepository cityNicRepository;

    @Autowired
    private DesignerRepository designerRepository;

    @Test
    public void testGetAllDesigner() throws InterruptedException {
        CityNic city = cityNicRepository.findByNic("sochi");
        String firstUrl = "https://" + city.getCityNic() + ".luxe-design.ru/designers";
        String secondUrl = "https://" + city.getCityNic() + ".luxe-design.ru/designers?page=";

        requestService.saveNodeListAnyCity(firstUrl, secondUrl, city.getCountPage());
        String urlNeedCity = "https://" + city.getCityNic() + ".luxe-design.ru/node/";

        System.out.println("nodeRepository.getSize() " + nodeRepository.getSize());
        System.out.println("designerRepository.getSize() " + designerRepository.getSize());

        List<Designer> allDesigner = getAllDesigner(urlNeedCity);
        System.out.println("@@@@@@@@");

        Thread.sleep(10000);

        System.out.println("nodeRepository.getSize() " + nodeRepository.getSize());
        System.out.println("designerRepository.getSize() " + designerRepository.getSize());

        Thread.sleep(10000);

        designerRepository.insertAll(allDesigner);
        designerRepository.showAll();
    }

    List<Designer> getAllDesigner(String url) {
        List<Designer> result = new ArrayList<>();
        for (String node : nodeRepository.getAll()) {
            Designer designer = parserService.getDesignerByUrl(url + node);
            result.add(designer);
        }
        return result;
    }

    @Test
    public void testGetDesignerByUrl() {
        System.out.println(parserService.getDesignerByUrl("https://sochi.luxe-design.ru/node/28831"));
    }

    @Test
    public void testGetDesignerByUrl2() {
        System.out.println(parserService.getDesignerByUrl("https://sochi.luxe-design.ru/node/26878"));
    }

    @Test
    public void testGetHtmlWithoutTelephone() {
        Designer designer = parserService.getDesignerByUrl("https://krd.luxe-design.ru/node/7741");
        System.out.println(designer);
    }

    @Test
    public void testGetHtmlWithTelephone() {
        Designer designer = parserService.getDesignerByUrl("https://krd.luxe-design.ru/node/7763");
        System.out.println(designer);
    }

    @Test
    public void testAsyncMethod() throws InterruptedException {
        CityNic city = cityNicRepository.findByNic("sochi");
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
        designerRepository.showAll();
    }

    @Test
    public void testAsyncMethodForMsk() throws InterruptedException {
        String firstUrl = "https://luxe-design.ru/";
        String secondUrl = "https://luxe-design.ru/designers?page=";

        requestService.saveNodeListAnyCity(firstUrl, secondUrl, 18);
        String urlNeedCity = "https://luxe-design.ru/node/";

        for (String node : nodeRepository.getAll()) {
            parserService.saveDesignerToRepository(urlNeedCity + node);
        }

        while ((nodeRepository.getSize() - 5) >= (designerRepository.getSize() + 5)) {
            Thread.sleep(1000);
            System.out.println("######################");
            System.out.println("nodeRepository.getSize() " + nodeRepository.getSize());
            System.out.println("designerRepository.getSize() " + designerRepository.getSize());
            System.out.println("######################");

        }
        designerRepository.showAll();
    }

}
