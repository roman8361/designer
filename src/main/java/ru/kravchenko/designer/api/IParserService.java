package ru.kravchenko.designer.api;

import ru.kravchenko.designer.entity.Designer;

public interface IParserService {

    Designer getDesignerByUrl(String url);

    public void asyncMethod();

    void saveDesignerToRepository(String url);

}
