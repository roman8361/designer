package ru.kravchenko.designer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kravchenko.designer.api.IRequestService;
import ru.kravchenko.designer.constant.Constant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RequestServiceTest {

    @Autowired
    IRequestService requestService;

    @Test
    public void testGetHtml() {
        Assert.assertNotNull(requestService.getHtmlByUrl(Constant.URL + 7477));
    }

    @Test
    public void testAsync() {
        System.out.println("Thread.currentThread().getId() " + Thread.currentThread().getId());
        System.out.println("Thread.currentThread().getName() " + Thread.currentThread().getName());

        System.out.println("#####");

        requestService.asyncMethod();
    }

}
