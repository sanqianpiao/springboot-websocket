package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledUpdatesOnTopic {

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 300)
    public void publishUpdates(String text) {
        template.convertAndSend("/topic/greetings", text);
    }
}
