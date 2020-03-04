package ru.kravchenko.designer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kravchenko.designer.repositiry.NodeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class NodeRepositoryTest {

    @Autowired
    NodeRepository nodeRepository;

    @Test
    public void testInsertAndGet() {
        nodeRepository.insert("1111");
        Assert.assertSame("1111", nodeRepository.getAll().get(0));
    }

}
