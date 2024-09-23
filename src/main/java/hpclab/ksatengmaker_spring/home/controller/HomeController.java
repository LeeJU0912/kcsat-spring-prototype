package hpclab.ksatengmaker_spring.home.controller;

import hpclab.ksatengmaker_spring.admin.dto.UserRequestRequestForm;
import hpclab.ksatengmaker_spring.admin.service.UserRequestService;
import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.dto.MemberForm;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/giveHeart")
    public String userGoodSayingRequest() {
        return "userGoodSaying";
    }

    @PostMapping("/giveHeart")
    public String userGoodSayingResponse(UserRequestRequestForm form) {
        userRequestService.saveUserRequest(userRequestService.getGoodSayingForm(form));

        return "redirect:/";
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
