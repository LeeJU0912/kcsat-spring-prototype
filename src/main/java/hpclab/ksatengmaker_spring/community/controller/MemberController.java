package hpclab.ksatengmaker_spring.community.controller;

import hpclab.ksatengmaker_spring.community.dto.MemberForm;
import hpclab.ksatengmaker_spring.community.dto.MemberResponseForm;
import hpclab.ksatengmaker_spring.community.service.MemberService;
import hpclab.ksatengmaker_spring.myBook.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BookService bookService;

    @GetMapping("/members/new")
    public String newMember() {
        return "member/newMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberForm memberForm) {
        memberService.join(memberForm);
        bookService.makeBook(memberForm.getEmail());

        return "redirect:/login";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<MemberResponseForm> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "member/memberList";
    }

    @GetMapping("/login")
    public String login() {
        return "member/loginForm";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "member/newMemberForm";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberForm memberForm, Errors errors, Model model) {
        /* 검증 */
        if (errors.hasErrors()) {
            /* 회원가입 실패 시 입력 데이터 유지 */
            model.addAttribute("dto", memberForm);
            /* 유효성 검사를 통과하지 못한 필드와 메세지 핸들링 */
            //globalService.messageHandling(errors, model);
            /* 회원가입 페이지로 리턴 */
            return "member/newMemberForm";
        }
        memberService.join(memberForm);

        return "redirect:/";
    }
}
