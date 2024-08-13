package hpclab.ksatengmaker_spring.questionGenerator.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDTO {
    private String definition;
    private String mainText;
}