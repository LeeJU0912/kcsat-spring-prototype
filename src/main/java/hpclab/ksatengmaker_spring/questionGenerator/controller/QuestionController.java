package hpclab.ksatengmaker_spring.questionGenerator.controller;

import hpclab.ksatengmaker_spring.myBook.service.BookQuestionService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitRawForm;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitForm;
import hpclab.ksatengmaker_spring.questionGenerator.service.QuestionServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;
    private final BookQuestionService bookQuestionService;

    // 양식 화면 로드
    @GetMapping("/question")
    public String getForm(Model model) {
        model.addAttribute("questionType", questionService.questionTypeList());
        return "question/questionForm";
    }

    @PostMapping("/question/createRandom/GPT")
    public String createDefaultQuestionByGPT(QuestionSubmitRawForm form, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("response", getRandomQuestion(form, "/create/GPT"));

        log.info("GPT-4o로 기출 문제 definition : {} 생성 완료.", form.getType());

        return "redirect:/question/result";
    }

    @PostMapping("/question/create/GPT")
    public String createQuestionByGPT(QuestionSubmitRawForm form, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("response", getMyQuestion(form, "/create/GPT"));

        log.info("GPT-4o로 외부 지문 문제 definition : {} 생성 완료.", form.getType());

        return "redirect:/question/result";
    }

    @PostMapping("/question/createRandom/LLaMA")
    public String createDefaultQuestionByLLaMA(QuestionSubmitRawForm form, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("response", getRandomQuestion(form, "/create/LLaMA"));

        log.info("LLaMA로 기출 문제 definition : {} 생성 완료.", form.getType());

        return "redirect:/question/result";
    }

    @PostMapping("/question/create/LLaMA")
    public String createQuestionByLLaMA(QuestionSubmitRawForm form, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("response", getMyQuestion(form, "/create/LLaMA"));

        log.info("LLaMA로 외부 지문 문제 definition : {} 생성 완료.", form.getType());

        return "redirect:/question/result";
    }

    private QuestionResponseRawForm getRandomQuestion(QuestionSubmitRawForm form, String requestLink) {
        QuestionType questionType = QuestionType.valueOf(form.getType());

        String definition = questionService.getDefinition(questionType);
        String mainText = questionService.getRandomDefaultDataset();

        // API 송신
        return questionService.getAIQuestion(new QuestionSubmitForm(requestLink, questionType, definition, mainText));
    }

    private QuestionResponseRawForm getMyQuestion(QuestionSubmitRawForm form, String requestLink) {
        QuestionType questionType = QuestionType.valueOf(form.getType());

        String definition = questionService.getDefinition(questionType);

        // API 송신
        return questionService.getAIQuestion(new QuestionSubmitForm(requestLink, questionType, definition, form.getMainText()));
    }

    @GetMapping("/question/result")
    public String resultForm(@ModelAttribute("response") QuestionResponseRawForm response, Model model) {
        model.addAttribute("response", response);
        return "question/resultForm";
    }

//    @PostMapping("/result/download/{fileName}")
//    public String downloadFile(@PathVariable String fileName, Model model) {
//
//        return "question/resultForm";
//    }

    @PostMapping("/question/result/save")
    public String saveQuestion(QuestionResponseRawForm form, RedirectAttributes redirectAttributes) {

        Question question = Question
                .builder()
                .type(form.getQuestionType())
                .title(form.getTitle())
                .mainText(form.getMainText())
                .shareCounter(0L)
                .build();

        question.setChoices(form.getChoices().stream().map(Choice::new).toList());

        bookQuestionService.saveQuestion(question);

        redirectAttributes.addFlashAttribute("response", form);


        return "redirect:/question/result";
    }
}