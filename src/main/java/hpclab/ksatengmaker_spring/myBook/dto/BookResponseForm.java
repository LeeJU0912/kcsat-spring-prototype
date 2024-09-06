package hpclab.ksatengmaker_spring.myBook.dto;

import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class BookResponseForm {

    private List<QuestionResponseForm> question;

    private LocalDateTime creationDate;

//    private String answer;
//    private String translation;
//    private String explanation;

}