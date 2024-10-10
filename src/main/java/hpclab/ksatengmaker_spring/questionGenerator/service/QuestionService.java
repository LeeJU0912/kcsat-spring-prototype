package hpclab.ksatengmaker_spring.questionGenerator.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.dto.ExplanationResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;

import java.util.TreeMap;

public interface QuestionService {
    String getRandomDefaultDataset();
    TreeMap<QuestionType, String> questionTypeList();
    String getQuestionDefinition(QuestionType type);
    String getExplanationDefinition(QuestionType type);
    Long saveQuestion(Question question);
    ExplanationResponseRawForm getExplanationFromServerOne(QuestionResponseRawForm data);
}
