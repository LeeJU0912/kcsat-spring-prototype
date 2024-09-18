package hpclab.ksatengmaker_spring.questionRank.service;

import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;

import java.util.List;

public interface QuestionRankService {
    void updateQuestionRank();

    List<QuestionResponseForm> getRankedQuestions();
}
