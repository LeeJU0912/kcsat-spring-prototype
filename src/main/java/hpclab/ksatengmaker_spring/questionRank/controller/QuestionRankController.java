package hpclab.ksatengmaker_spring.questionRank.controller;

import hpclab.ksatengmaker_spring.community.service.PostService;
import hpclab.ksatengmaker_spring.myBook.service.BookQuestionService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionRank.service.QuestionRankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionRankController {

    private final QuestionRankService questionRankService;
    private final QuestionJPARepository questionJPARepository;
    private final BookQuestionService bookQuestionService;

    @GetMapping("/weekly")
    public String weeklyQuestionRank(Model model) {

        // weekly Question 5개 랭킹 순서대로 가져 와서 모델에 넣기.
        model.addAttribute("rankedQuestions", questionRankService.getRankedQuestions());

        return "questionRank/questionRanking";
    }

    @GetMapping("/weekly/{qId}")
    public String weeklyQuestionDetail(Model model, @PathVariable Long qId) {
        Question question = questionJPARepository.findById(qId).orElseThrow(() -> new IllegalArgumentException("문제 참조 오류입니다아"));

        model.addAttribute("question", question);

        return "questionRank/questionRankedDetail";
    }

    @GetMapping("/weekly/{qId}/save")
    public String weeklyQuestionPull(@PathVariable Long qId) {
        bookQuestionService.saveQuestion(qId);

        return "redirect:/myBook";
    }
}
