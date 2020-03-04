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
import ru.kravchenko.designer.entity.DesignerStudio;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Kravchenko
 */
public class DesignerStudiosParserTest {

    @Test
    @SneakyThrows
    public void testCreateExcelFileWithDesignerStudio() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");
        List<DesignerStudio> designerStudios = getAllDesignerStudios();

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("No.");
        rowhead.createCell(1).setCellValue("Name");
        rowhead.createCell(2).setCellValue("Url");

        int count = 1;
        for (DesignerStudio designer : designerStudios) {
            HSSFRow row = sheet.createRow((short) count);
            row.createCell(0).setCellValue(count);
            row.createCell(1).setCellValue(designer.getName());
            row.createCell(2).setCellValue(designer.getUrl());
            count++;
        }

        FileOutputStream fileOut = new FileOutputStream("designerStudioMsk.xls");
        workbook.write(fileOut);
        fileOut.close();
//             workbook.close(); TODO разоброаться как закрывать поток
        System.out.println("Your excel file has been generated!");
    }


    private List<DesignerStudio> getAllDesignerStudios() {
        List<DesignerStudio> allDesignerStudio = new ArrayList<>();
        for (String string : getAllNodeStudioMsk()) {
            String urlAllDesigner = "https://luxe-design.ru/node/";
            allDesignerStudio.add(getDesignerStudioByUrl(urlAllDesigner + string));
        }
        return allDesignerStudio;
    }

    @SneakyThrows
    private DesignerStudio getDesignerStudioByUrl(String url) {
        Document doc = Jsoup.connect(url).get();
        DesignerStudio designerStudio = new DesignerStudio();
        designerStudio.setName(doc.getElementById("page-title").text());

        System.out.println(designerStudio.getName());

        Elements div = doc.getElementsByClass("company-property");
        for (Element element : div) {
            System.out.println(element.text());
            designerStudio.setUrl(element.text());
        }
        return designerStudio;
    }

    private List<String> getAllNodeStudioMsk() {
        List<String> result = new ArrayList<>();
        result.add("28867");
        result.add("28779");
        result.add("28683");
        result.add("8208");
        result.add("13252");
        result.add("21078");
        result.add("24072");
        result.add("15235");
        result.add("27761");
        result.add("27571");
        result.add("21494");
        result.add("27463");
        result.add("27220");
        result.add("7255");
        result.add("27096");
        result.add("26919");
        result.add("20274");
        result.add("26642");
        result.add("18113");
        result.add("6370");
        result.add("7683");
        result.add("7993");
        result.add("8792");
        result.add("12356");
        result.add("15091");
        result.add("18185");
        result.add("22896");
        result.add("6646");
        result.add("25743");
        result.add("25671");
        result.add("25004");
        result.add("24087");
        result.add("24781");
        result.add("24267");
        result.add("7619");
        result.add("23632");
        result.add("23770");
        result.add("23620");
        result.add("23467");
        result.add("22587");
        result.add("21690");
        result.add("20999");
        result.add("21098");
        result.add("20958");
        result.add("20895");
        result.add("20392");
        result.add("20334");
        result.add("11383");
        result.add("19747");
        result.add("18317");
        result.add("19654");
        result.add("19586");
        result.add("7477");
        result.add("8912");
        result.add("17594");
        result.add("17451");
        result.add("17371");
        result.add("17213");
        result.add("17225");
        result.add("17132");
        result.add("16826");
        result.add("9641");
        result.add("13669");
        result.add("12532");
        result.add("12149");
        result.add("11547");
        result.add("10755");
        result.add("11311");
        result.add("10075");
        result.add("9855");
        result.add("9647");
        result.add("9612");
        result.add("6303");
        result.add("8956");
        result.add("8656");
        result.add("8536");
        result.add("7627");
        result.add("7598");
        result.add("7465");
        result.add("7189");
        result.add("7008");
        result.add("6798");
        result.add("6737");
        result.add("6701");
        result.add("6498");
        result.add("6445");
        result.add("6327");
        result.add("6248");
        result.add("6233");
        result.add("11669");
        result.add("18280");
        result.add("24672");
        result.add("28683");
        result.add("6233");

        return result;
    }

}
