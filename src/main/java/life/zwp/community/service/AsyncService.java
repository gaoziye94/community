package life.zwp.community.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    @Async
    public void executeAsync1() throws InterruptedException {
        Thread.sleep(20);
        System.out.println("异步任务::1");

    }

    @Async
    public void executeAsync2() {
        System.out.println("异步任务::2");
    }
}
