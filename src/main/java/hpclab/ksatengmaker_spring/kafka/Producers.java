package hpclab.ksatengmaker_spring.kafka;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class Producers {

    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final HttpSession httpSession;
    public int produceIdx = 1;

    public Long sendMessage(String message) throws ExecutionException, InterruptedException {

        String uuid = (String) httpSession.getAttribute("userUUID");

        //RoundRobin
        produceIdx = (produceIdx + 1) % 2;

        if (produceIdx == 0) {
            logger.info("sending message to topic: QuestionRequest1, keys: {}, message: {}", uuid, message);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("QuestionRequest1", uuid, message);

            return future.get().getRecordMetadata().offset();
        }
        else {
            logger.info("sending message to topic: QuestionRequest2, keys: {}, message: {}", uuid, message);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("QuestionRequest2", uuid, message);

            return future.get().getRecordMetadata().offset();
        }
    }
}
