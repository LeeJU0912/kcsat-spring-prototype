package hpclab.ksatengmaker_spring.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitKafkaForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final Consumers consumers;
    private final Producers producers;
    private final ObjectMapper objectMapper;
    private final HttpSession httpSession;

    // UUID를 사용한 비즈니스 로직 처리
    public Long sendQuestionToKafka(QuestionSubmitKafkaForm form) throws InterruptedException, JsonProcessingException, ExecutionException {
        String stringForm = objectMapper.writeValueAsString(form);

        log.info("Send UUID : {}", httpSession.getAttribute("userUUID"));

        // 메시지 전송
        Long nowOffset = producers.sendMessage(stringForm);
        Long recentOffset = 0L;

//        if (producers.produceIdx == 0) {
//            recentOffset = getCurrentOffset("QuestionRequest1");
//        }
//        else {
//            recentOffset = getCurrentOffset("QuestionRequest2");
//        }

        return nowOffset - recentOffset;
    }


    // 현재 Consumer의 오프셋 얻기
    private long getCurrentOffset(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka.default.svc.cluster.local:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "HPCLab");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            TopicPartition topicPartition = new TopicPartition(topic, 0);
            Set<TopicPartition> partitions = Collections.singleton(topicPartition);
            Map<TopicPartition, OffsetAndMetadata> committedOffsets = consumer.committed(partitions);
            OffsetAndMetadata offsetAndMetadata = committedOffsets.get(topicPartition);
            if (offsetAndMetadata != null) {
                return offsetAndMetadata.offset();
            }
            else {
                return 0L;
            }
        }
    }


    // UUID를 사용한 비즈니스 로직 처리
    public QuestionResponseRawForm receiveQuestionFromKafka() throws InterruptedException {
        // 메시지가 들어올 때까지 대기
        ConsumerRecord<String, String> message = consumers.getMessageFromQueue();
        String messageValue = message.value();

        try {
            return objectMapper.readValue(messageValue, QuestionResponseRawForm.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
