package life.zwp.community.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncServiceTest {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void executeAsync1() {
    }

    @Test
    public void executeAsync2() {
    }

    @Test
    public void asyncTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            asyncService.executeAsync1();
            asyncService.executeAsync2();
        }
        Thread.sleep(1000);
    }
}
