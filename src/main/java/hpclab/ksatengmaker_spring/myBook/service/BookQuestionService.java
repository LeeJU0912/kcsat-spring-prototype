package hpclab.ksatengmaker_spring.myBook.service;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;

public interface BookQuestionService {
    public Long saveFirstQuestion(Question question);
    public Long saveQuestion(Long qId);
}
