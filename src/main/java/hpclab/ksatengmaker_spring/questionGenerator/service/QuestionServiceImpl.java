package hpclab.ksatengmaker_spring.questionGenerator.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.ExplanationResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private static final String ExplanationServerOneURL = "http://ex-gpt1-svc:8000";

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
    public String getQuestionDefinition(QuestionType type) {
        return questionRepository.getQuestionDefinition(type);
    }

    @Override
    public String getExplanationDefinition(QuestionType type) {
        return questionRepository.getExplanationDefinition(type);
    }

    @Override
    public Long saveQuestion(Question question) {
        Question save = questionJPARepository.save(question);

        return save.getId();
    }


    @Override
    public ExplanationResponseRawForm getExplanationFromServerOne(QuestionResponseRawForm data) {
        // WebClient 방식으로 송신.
        WebClient client = WebClient.create(QuestionServiceImpl.ExplanationServerOneURL);

        // 작성된 양식 데이터를 API Json 송신 데이터로 제작
        Map<String, Object> form = new LinkedHashMap<>();
        String explanationDefinition = questionRepository.getExplanationDefinition(data.getQuestionType());
        form.put("definition", explanationDefinition);
        form.put("title", data.getTitle());
        form.put("mainText", data.getMainText());
        form.put("choices", data.getChoices());

        log.info("SEND EX : {}", form);


        // URI에 원하는 API 경로 정의.
        return client
                .post()
                .uri("/explanation/GPT")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(ExplanationResponseRawForm.class)
                .block();
    }
}
