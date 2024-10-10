package hpclab.ksatengmaker_spring.questionGenerator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hpclab.ksatengmaker_spring.admin.service.UserRequestService;
import hpclab.ksatengmaker_spring.kafka.KafkaService;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionSubmitKafkaForm;
import hpclab.ksatengmaker_spring.myBook.service.BookQuestionService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.dto.*;
import hpclab.ksatengmaker_spring.questionGenerator.service.QuestionServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;
    private final BookQuestionService bookQuestionService;
    private final UserRequestService userRequestService;
    private final KafkaService kafkaService;

    // 양식 화면 로드
    @GetMapping("/question")
    public String getForm(Model model) {
        model.addAttribute("questionType", questionService.questionTypeList());
        return "question/questionForm";
    }

    @PostMapping("/createQuestionAllRandom/LLaMA")
    public String createDemoQuestionByLLaMAFromServerOne(RedirectAttributes redirectAttributes) throws InterruptedException, JsonProcessingException, ExecutionException {

        QuestionType questionType = QuestionType.getRandomQuestionType();

        String definition = questionService.getQuestionDefinition(questionType);
        String mainText = questionService.getRandomDefaultDataset();

        Long offsetGap = kafkaService.sendQuestionToKafka(new QuestionSubmitKafkaForm(definition, mainText));

        log.info("offsetGap : {}", offsetGap);

        QuestionResponseRawForm response = kafkaService.receiveQuestionFromKafka();
        response.setQuestionType(questionType);
        redirectAttributes.addFlashAttribute("response", response);

        log.info("Kafka로 DEMO 문제 definition : {} 생성 완료.", response.getQuestionType());

        return "redirect:/question/result";
    }

    @PostMapping("/question/createRandom/LLaMA")
    public String createDefaultQuestionByLLaMAFromServerOne(QuestionSubmitRawForm form, RedirectAttributes redirectAttributes) throws InterruptedException, JsonProcessingException, ExecutionException {

        QuestionType questionType = QuestionType.valueOf(form.getType());

        String definition = questionService.getQuestionDefinition(questionType);
        String mainText = questionService.getRandomDefaultDataset();

        Long offsetGap = kafkaService.sendQuestionToKafka(new QuestionSubmitKafkaForm(definition, mainText));

        log.info("offsetGap : {}", offsetGap);

        QuestionResponseRawForm response = kafkaService.receiveQuestionFromKafka();
        response.setQuestionType(questionType);
        redirectAttributes.addFlashAttribute("response", response);

        log.info("Kafka로 기출 문제 definition : {} 생성 완료.", response.getQuestionType());

        return "redirect:/question/result";
    }

    @PostMapping("/question/create/LLaMA")
    public String createQuestionByLLaMAFromServerOne(QuestionSubmitRawForm form, RedirectAttributes redirectAttributes) throws InterruptedException, JsonProcessingException, ExecutionException {

        QuestionType questionType = QuestionType.valueOf(form.getType());

        String definition = questionService.getQuestionDefinition(questionType);
        String mainText = form.getMainText();

        Long offsetGap = kafkaService.sendQuestionToKafka(new QuestionSubmitKafkaForm(definition, mainText));

        log.info("offsetGap : {}", offsetGap);

        QuestionResponseRawForm response = kafkaService.receiveQuestionFromKafka();
        response.setQuestionType(questionType);
        redirectAttributes.addFlashAttribute("response", response);

        log.info("Kafka로 외부 지문 문제 definition : {} 생성 완료.", questionType);

        return "redirect:/question/result";
    }

    @GetMapping("/question/result")
    public String questionResultForm(@ModelAttribute("response") QuestionResponseRawForm response, Model model) {
        model.addAttribute("response", response);
        return "question/questionResultForm";
    }

    @PostMapping("/question/explanation/GPT")
    public String createExplanationByGPTFromServerOne(QuestionResponseRawForm form, HttpSession session) {

        ExplanationResponseRawForm explanation = questionService.getExplanationFromServerOne(form);

        QuestionDto question = QuestionDto.builder()
                .title(form.getTitle())
                .questionType(form.getQuestionType())
                .mainText(form.getMainText())
                .answer(explanation.getAnswer())
                .translation(explanation.getTranslation())
                .explanation(explanation.getExplanation())
                .build();

        question.setChoices(form.getChoices().stream().toList());

        Boolean saveComplete = (Boolean) session.getAttribute("saveComplete");
        if (saveComplete == null || saveComplete == Boolean.TRUE) session.setAttribute("saveComplete", Boolean.FALSE);

        session.setAttribute("response", question);

        log.info("GPT 해설 definition : {} 생성 완료.", form.getQuestionType());

        return "redirect:/question/explanation/result";
    }

    @GetMapping("/question/explanation/result")
    public String explanationResultForm(HttpSession session, Model model) {

        QuestionDto response = (QuestionDto) session.getAttribute("response");
        Boolean saveComplete = (Boolean) session.getAttribute("saveComplete");

        model.addAttribute("response", response);
        model.addAttribute("saveComplete", saveComplete);

        return "question/explanationResultForm";
    }

    @PostMapping("/question/result/save")
    public String saveQuestion(QuestionDto form, HttpSession session) {

        Question question = Question
                .builder()
                .type(form.getQuestionType())
                .title(form.getTitle())
                .mainText(form.getMainText())
                .answer(form.getAnswer())
                .translation(form.getTranslation())
                .explanation(form.getExplanation())
                .shareCounter(0L)
                .build();

        question.setChoices(form.getChoices().stream().map(Choice::new).toList());

        bookQuestionService.saveFirstQuestion(question);

        session.setAttribute("response", form);
        session.setAttribute("saveComplete", Boolean.TRUE);

        return "redirect:/question/explanation/result";
    }

    @PostMapping("/question/result/junk")
    public String filterQuestion(QuestionDto form) {

        Question question = Question
                .builder()
                .type(form.getQuestionType())
                .title(form.getTitle())
                .mainText(form.getMainText())
                .answer(form.getAnswer())
                .translation(form.getTranslation())
                .explanation(form.getExplanation())
                .shareCounter(0L)
                .build();

        question.setChoices(form.getChoices().stream().map(Choice::new).toList());

        Long qId = questionService.saveQuestion(question);

        userRequestService.saveUserRequest(userRequestService.getQuestionErrorForm(qId));

        return "redirect:/question";
    }
}