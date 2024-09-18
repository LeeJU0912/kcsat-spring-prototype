package hpclab.ksatengmaker_spring.admin.controller;

import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.dto.CommentResponseForm;
import hpclab.ksatengmaker_spring.community.dto.MemberResponseForm;
import hpclab.ksatengmaker_spring.community.dto.PostResponseForm;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminPageController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/admin")
    public String adminMain(Model model) {
        return "admin/adminMain";
    }

    @GetMapping("/admin/requests")
    public String userRequests(Model model) {
        return "admin/userRequests";
    }

    @GetMapping("/admin/members")
    public String memberList(Model model) {
        List<MemberResponseForm> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "admin/memberList";
    }

    @GetMapping("/admin/members/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다"));
        model.addAttribute("member", MemberResponseForm.builder()
                .member(member)
                .build());

        return "admin/memberDetail";
    }

    @GetMapping("/admin/members/{id}/posts")
    public String memberDetailPosts(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다"));

        List<PostResponseForm> posts = member.getPosts().stream().map(x -> PostResponseForm.builder().post(x).build()).toList();

        model.addAttribute("username", member.getUsername());
        model.addAttribute("posts", posts);

        return "admin/memberDetailPosts";
    }

    @GetMapping("/admin/members/{id}/comments")
    public String memberDetailComments(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("없는 유저입니다"));

        List<CommentResponseForm> comments = member.getComments().stream().map(x -> CommentResponseForm.builder().comment(x).build()).toList();

        model.addAttribute("username", member.getUsername());
        model.addAttribute("comments", comments);

        return "admin/memberDetailComments";
    }
}