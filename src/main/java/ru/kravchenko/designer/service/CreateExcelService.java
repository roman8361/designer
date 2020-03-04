package ru.kravchenko.designer.service;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kravchenko.designer.api.ICreateExcelService;
import ru.kravchenko.designer.constant.Constant;
import ru.kravchenko.designer.entity.Designer;
import ru.kravchenko.designer.entity.DesignerStudio;
import ru.kravchenko.designer.repositiry.CityNicRepository;
import ru.kravchenko.designer.repositiry.DesignerRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateExcelService implements ICreateExcelService {

    @Autowired
    private DesignerRepository designerRepository;

    @Override
    @SneakyThrows
    public void createFirstFile(List<Designer> designerList, String city) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");

        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("No.");
        rowhead.createCell(1).setCellValue("Имя");
        rowhead.createCell(2).setCellValue("Телефон");
        rowhead.createCell(3).setCellValue("Инстаграм");
        rowhead.createCell(4).setCellValue("Вконтакте");
        rowhead.createCell(5).setCellValue("Файсбук");
        rowhead.createCell(6).setCellValue("ok");
        rowhead.createCell(7).setCellValue("Твиттер");
        rowhead.createCell(8).setCellValue("Сайт");
        rowhead.createCell(9).setCellValue("Город");
        rowhead.createCell(10).setCellValue("Должность");

        int count = 1;
        for (Designer designer : designerRepository.getAll()) {
            HSSFRow row = sheet.createRow((short) count);
            row.createCell(0).setCellValue(count);
            row.createCell(1).setCellValue(designer.getName());
            row.createCell(2).setCellValue(designer.getTelephone());
            row.createCell(9).setCellValue(city);
            row.createCell(10).setCellValue(designer.getPosition());

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

            }
            count++;
        }
        FileOutputStream fileOut = new FileOutputStream(Constant.FILE_NAME);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        System.out.println("Your excel file has been generated!");

    }

    @SneakyThrows
    public void appendDataToFile(List<Designer> designers, String city) {
        FileInputStream inputStream = new FileInputStream(new File(Constant.FILE_NAME));
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();

        System.out.println(rowCount);

        for (Designer designer : designers) {
            Row row = sheet.createRow(++rowCount);

            row.createCell(0).setCellValue(rowCount);
            row.createCell(1).setCellValue(designer.getName());
            row.createCell(2).setCellValue(designer.getTelephone());
            row.createCell(9).setCellValue(city);
            row.createCell(10).setCellValue(designer.getPosition());

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

            }
        }

        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream(Constant.FILE_NAME);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    @SneakyThrows
    public void appendDataToFileStudio(List<DesignerStudio> designers, String city) {
        FileInputStream inputStream = new FileInputStream(new File("allStudios.xls"));
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();

        System.out.println(rowCount);

        for (DesignerStudio designer : designers) {
            Row row = sheet.createRow(++rowCount);

            row.createCell(0).setCellValue(rowCount);
            row.createCell(1).setCellValue(designer.getName());
            row.createCell(2).setCellValue(designer.getCity());
            row.createCell(3).setCellValue(city);
        }

        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream("allStudios.xls");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
