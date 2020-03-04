package ru.kravchenko.designer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kravchenko.designer.repositiry.CityNicRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CityNicRepositoryTest {

    @Autowired
    CityNicRepository cityNicRepository;

    @Test
    public void testShowAll() {
        cityNicRepository.showAll();
    }

    @Test
    public void testFindByNic() {
        Assert.assertSame("belgorod", cityNicRepository.findByNic("belgorod").getCityNic());
        Assert.assertSame(1, cityNicRepository.findByNic("belgorod").getCountPage());
    }

}
