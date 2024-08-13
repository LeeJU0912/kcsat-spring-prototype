package hpclab.ksatengmaker_spring.questionGenerator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import hpclab.ksatengmaker_spring.questionGenerator.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 양식 화면 로드
    @GetMapping("/question")
    public String createForm(Model model) {
        model.addAttribute("questionType", questionService.questionTypeList());
        return "question/questionForm";
    }

    @PostMapping("/question/new")
    public String createInstance(InputForm form, RedirectAttributes redirectAttributes) throws JsonProcessingException, ParseException {

        ObjectMapper test = new ObjectMapper();

        String s = test.writeValueAsString(form);
        System.out.println(s);

        String definition = questionService.getDefinition(QuestionType.valueOf(form.getType()));
        String mainText = form.getMainText();

        // API 송신
        QuestionForm response = questionService.getAIQuestion(new QuestionDTO(definition, mainText));

        System.out.println("response.getResponse() = " + response.getResponse());

//        JSONParser parser = new JSONParser(response.getResponse());
//
//        Object parse = parser.parse();
//
//        JSONObject object = (JSONObject) parse;
//
//        System.out.println("object.get(\"response\") = " + object.get("response"));

        redirectAttributes.addFlashAttribute("response", response.getResponse());

        log.info("FastAPI로 definition : " + form.getType() + " 송신 완료.");

        return "redirect:/question/new";
    }

    @GetMapping("/question/new")
    public String resultForm(@ModelAttribute("response") String response, Model model) {
        model.addAttribute("response", response);
        return "question/resultForm";
    }
}
