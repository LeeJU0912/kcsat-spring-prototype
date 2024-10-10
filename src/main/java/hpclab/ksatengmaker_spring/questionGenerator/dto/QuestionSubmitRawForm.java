package hpclab.ksatengmaker_spring.questionGenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionSubmitRawForm {

    private String type;
    private String mainText;
}
