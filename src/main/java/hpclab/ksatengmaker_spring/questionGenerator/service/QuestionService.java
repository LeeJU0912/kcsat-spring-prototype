package hpclab.ksatengmaker_spring.questionGenerator.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.repository.ChoiceJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private static final String AIServerURL = "http://localhost:8000";

    private final QuestionRepository questionRepository;
    private final QuestionJPARepository questionJPARepository;
    private final ChoiceJPARepository choiceJPARepository;

    public String getRandomDefaultDataset() {
        Random random = new Random();
        return questionRepository.getDefaultDatasets().get(random.nextInt(questionRepository.getDefaultDatasets().size()));
    }

    public TreeMap<QuestionType, String> questionTypeList() {
        return questionRepository.getQuestionType();
    }

    public String getDefinition(QuestionType type) {
        return questionRepository.getDefinition(type);
    }

    public void saveQuestion(Question question) {
        questionJPARepository.save(question);
    }

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
