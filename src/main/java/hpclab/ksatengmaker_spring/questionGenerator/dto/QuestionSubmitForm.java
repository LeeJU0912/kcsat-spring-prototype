package hpclab.ksatengmaker_spring.questionGenerator.dto;

import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionSubmitForm {

    private String requestLink;

    private QuestionType questionType;
    private String definition;
    private String mainText;
}