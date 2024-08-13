package hpclab.ksatengmaker_spring.questionGenerator.service;

import hpclab.ksatengmaker_spring.questionGenerator.controller.QuestionDTO;
import hpclab.ksatengmaker_spring.questionGenerator.controller.QuestionForm;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private static final String AIServerURL = "http://host.docker.internal:8000";

    private final QuestionRepository questionRepository;

    public Map<QuestionType, String> questionTypeList() {
        return questionRepository.findTypeAll();
    }

    public String getDefinition(QuestionType type) {
        return questionRepository.getDefinition(type);
    }

    public QuestionForm getAIQuestion(QuestionDTO data) {

        System.out.println(data.getMainText());
        System.out.println(data.getDefinition());


        // WebClient 방식으로 송신.
        WebClient client = WebClient.create(AIServerURL);

        // 작성된 양식 데이터를 API Json 송신 데이터로 제작
        Map<String, String> form = new LinkedHashMap<>();
        form.put("definition", data.getDefinition());
        form.put("mainText", data.getMainText());

        // URI에 원하는 API 경로 정의.
        return client
                .post()
                .uri("/predict")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(QuestionForm.class)
                .block();


        // RestClient 방식

//        RestClient restClient = RestClient.builder()
//                        .baseUrl("http://localhost:8000")
//                                .build();
//
//        restClient.post()
//                .uri("/predict")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(data)
//                .exchange((clientRequest, clientResponse) -> {
//                    String now = convert
//                });

    }
}
