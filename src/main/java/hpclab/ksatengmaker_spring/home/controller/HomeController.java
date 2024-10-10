package hpclab.ksatengmaker_spring.home.controller;

import hpclab.ksatengmaker_spring.admin.dto.UserRequestRequestForm;
import hpclab.ksatengmaker_spring.admin.service.UserRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserRequestService userRequestService;

    @GetMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }

    @GetMapping("/credit")
    public String credit() {
        log.info("credit");
        return "credit";
    }

    @GetMapping("/giveIdea")
    public String userGiveIdeaRequest() {
        return "userRequest";
    }

    @PostMapping("/giveIdea")
    public String userGiveIdeaResponse(UserRequestRequestForm form) {
        userRequestService.saveUserRequest(userRequestService.getImprovingForm(form));

        return "redirect:/";
    }
}
