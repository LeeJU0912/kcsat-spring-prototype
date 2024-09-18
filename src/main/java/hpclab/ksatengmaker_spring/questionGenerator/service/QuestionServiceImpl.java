package hpclab.ksatengmaker_spring.questionGenerator.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitForm;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.repository.ChoiceJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final String AIServerURL = "http://172.17.0.8:8000";

    private final QuestionRepository questionRepository;
    private final QuestionJPARepository questionJPARepository;

    @Override
    public String getRandomDefaultDataset() {
        Random random = new Random();
        return questionRepository.getDefaultDatasets().get(random.nextInt(questionRepository.getDefaultDatasets().size()));
    }

    @Override
    public TreeMap<QuestionType, String> questionTypeList() {
        return questionRepository.getQuestionType();
    }

    @Override
    public String getDefinition(QuestionType type) {
        return questionRepository.getDefinition(type);
    }

    @Override
    public Long saveQuestion(Question question) {
        Question save = questionJPARepository.save(question);

        return save.getId();
    }

    @Override
    public QuestionResponseRawForm getAIQuestion(QuestionSubmitForm data) {

        // WebClient 방식으로 송신.
        WebClient client = WebClient.create(AIServerURL);

        // 작성된 양식 데이터를 API Json 송신 데이터로 제작
        Map<String, String> form = new LinkedHashMap<>();
        form.put("definition", data.getDefinition());
        form.put("mainText", data.getMainText());

        // URI에 원하는 API 경로 정의.
        QuestionResponseRawForm response = client
                .post()
                .uri(data.getRequestLink())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(QuestionResponseRawForm.class)
                .block();


        Question question = Question.builder()
                .type(data.getQuestionType())
                .title(response.getTitle())
                .mainText(response.getMainText())
                .shareCounter(0L)
                .build();

        question.setChoices(response.getChoices().stream().map(now -> new Choice()).toList());

        //saveQuestion(question);


        return QuestionResponseRawForm.builder()
                .questionType(question.getType())
                .title(question.getTitle())
                .mainText(question.getMainText())
                .choices(response.getChoices())
                .build();
    }
}
