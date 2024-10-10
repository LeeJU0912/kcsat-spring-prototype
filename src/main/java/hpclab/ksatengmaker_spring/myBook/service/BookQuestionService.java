package hpclab.ksatengmaker_spring.myBook.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;

public interface BookQuestionService {
    Long saveFirstQuestion(Question question);
    Long saveQuestion(Long qId);
}
