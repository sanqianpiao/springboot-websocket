package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
public class CommentsController {

    @Autowired
    private CommentCache commentCache;

    @Autowired
    private CommentSimulator commentSimulator;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private volatile boolean run = false;

    @GetMapping("/comment")
    public Map comment(HelloMessage message) throws Exception {
        if (this.run == true) return Collections.singletonMap("status", Boolean.TRUE);
        this.run = true;
        commentSimulator.start();
        taskExecutor.submit(() -> {
            while (this.run) {
                String comment = commentCache.read();
                System.out.println(comment);
                template.convertAndSend("/topic/comments", comment);
            }
            return null;
        });

        return Collections.singletonMap("status", Boolean.TRUE);
    }

    @GetMapping("/comment/stop")
    public Map commentStop(HelloMessage message) {
        commentSimulator.stop();
        this.run = false;
        return Collections.singletonMap("status", Boolean.TRUE);
    }

}
