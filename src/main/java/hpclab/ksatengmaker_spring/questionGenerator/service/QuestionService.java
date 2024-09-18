package hpclab.ksatengmaker_spring.questionGenerator.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitForm;

import java.util.TreeMap;

public interface QuestionService {
    String getRandomDefaultDataset();
    TreeMap<QuestionType, String> questionTypeList();
    String getDefinition(QuestionType type);
    Long saveQuestion(Question question);
    QuestionResponseRawForm getAIQuestion(QuestionSubmitForm data);
}
