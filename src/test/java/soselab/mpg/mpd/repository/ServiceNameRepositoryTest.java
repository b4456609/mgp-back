package soselab.mpg.mpd.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.mpg.mpd.model.ServiceName;

/**
 * Created by bernie on 2017/1/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceNameRepositoryTest {

    @Autowired
    private ServiceNameRepository serviceNameRepository;

    @Before
    public void setUp() throws Exception {
        serviceNameRepository.deleteAll();

    }

    @Test
    public void testDuplicate() {
        serviceNameRepository.save(new ServiceName("a"));
        try {
            serviceNameRepository.save(new ServiceName("a"));
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }
    }

}