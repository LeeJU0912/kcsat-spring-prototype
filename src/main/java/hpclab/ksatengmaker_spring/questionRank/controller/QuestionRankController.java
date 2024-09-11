package hpclab.ksatengmaker_spring.questionRank.controller;

import hpclab.ksatengmaker_spring.community.service.PostService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.service.QuestionService;
import hpclab.ksatengmaker_spring.questionRank.service.QuestionRankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionRankController {

    private final QuestionRankService questionRankService;
    private final QuestionJPARepository questionJPARepository;
    private final PostService postService;

    @GetMapping("/weekly")
    public String weeklyQuestionRank(Model model) {

        // weekly Question 5개 랭킹 순서대로 가져 와서 모델에 넣기.
        model.addAttribute("rankedQuestions", questionRankService.getQuestions());

        return "questionRank/questionRanking";
    }

    @GetMapping("/weekly/{id}")
    public String weeklyQuestionDetail(Model model, @PathVariable Long id) {
        Question question = questionJPARepository.findById(id).orElseThrow(() -> new IllegalArgumentException("문제 참조 오류입니다아"));

        QuestionResponseForm questionDTO = QuestionResponseForm.builder()
                .qId(question.getId())
                .title(question.getTitle())
                .mainText(question.getMainText())
                .questionType(question.getType())
                .choices(question.getChoices().stream().map(Choice::getChoice).toList())
                .shareCounter(question.getShareCounter())
                .build();


        model.addAttribute("rankedQuestion", questionDTO);
        model.addAttribute("postDate", question.getCreatedDate());

        return "questionRank/questionRankedDetail";
    }

    @GetMapping("/weekly/{id}/save")
    public String weeklyQuestionPull(@PathVariable Long id) {

        postService.saveQuestionFromPost(id);

        return "redirect:/myBook";
    }
}
