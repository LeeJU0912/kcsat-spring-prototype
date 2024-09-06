package hpclab.ksatengmaker_spring.questionGenerator.dto;

import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseForm {

    private Long qId;

    private QuestionType questionType;

    private String title;
    private String mainText;
    private List<String> choices;

    private Long shareCounter;
}