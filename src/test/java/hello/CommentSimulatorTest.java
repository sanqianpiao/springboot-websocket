package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentSimulatorTest {

    @Autowired
    CommentSimulator commentSimulator;

    @Autowired
    CommentCache commentCache;

    @Test
    public void testStart() throws InterruptedException {
        commentSimulator.start();
        Thread.currentThread().sleep(1000);
        commentSimulator.stop();
        assertTrue(commentCache.size() > 0);
    }
}