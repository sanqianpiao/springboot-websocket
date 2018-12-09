package hello;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Component
public class CommentCache {
    private BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    public void write(String message) {
        queue.offer(message);
    }

    public String read() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }
}
