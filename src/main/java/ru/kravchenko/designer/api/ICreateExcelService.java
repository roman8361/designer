package ru.kravchenko.designer.api;

import ru.kravchenko.designer.entity.Designer;
import ru.kravchenko.designer.entity.DesignerStudio;

import java.util.List;

public interface ICreateExcelService {

    void createFirstFile(List<Designer> designerList, String city);

    void appendDataToFile(List<Designer> designers, String city);

    void appendDataToFileStudio(List<DesignerStudio> designers, String city);

}
