package ru.kravchenko.designer.api;

public interface IRequestService {

    String getHtmlByUrl(String url);

 //   Designer getDesignerFromHtml(String url);

    void saveNodeListAnyCity(String firstUrl, String secondUrl, Integer countPage);

    void saveNodeListForStudio(String shortName);

    public void asyncMethod();

}
