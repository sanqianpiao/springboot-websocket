package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CommentSimulator {

    @Autowired
    private CommentCache commentCache;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private volatile boolean run = false;

    public void start() {
        this.run = true;
        taskExecutor.submit(() -> {
            while (this.run && Thread.currentThread().isInterrupted() == false) {
                commentCache.write(UUID.randomUUID().toString());
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(0, 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void stop() {
        this.run = false;
    }
}
