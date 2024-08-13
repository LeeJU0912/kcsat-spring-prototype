package hpclab.ksatengmaker_spring.questionGenerator.api;

import hpclab.ksatengmaker_spring.questionGenerator.service.QuestionService;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionApiController {

    private final QuestionService questionService;

    // API 수신
//    @PostMapping("question/api/new")
//    public CreateQuestionResponse saveQuestion(@RequestBody @Valid CreateQuestionRequest request) {
//        //Question question = Question.createQuestion(request.getQuestion(), request.getMainText(), request.getChoices(), request.getAnswer());
//
//    }

    @Data
    static class CreateQuestionResponse {
        private Long id;
    }

    @Data
    private class CreateQuestionRequest {
        @NotEmpty
        private String question;
        private String mainText;
        private String choices;
        private String answer;
    }
}
