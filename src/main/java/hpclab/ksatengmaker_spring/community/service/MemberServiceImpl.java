package hpclab.ksatengmaker_spring.community.service;

import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.domain.Role;
import hpclab.ksatengmaker_spring.community.dto.MemberForm;
import hpclab.ksatengmaker_spring.community.dto.MemberResponseForm;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;
import hpclab.ksatengmaker_spring.myBook.dto.BookResponseForm;
import hpclab.ksatengmaker_spring.myBook.repository.BookRepository;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;


    @Override
    public Long join(MemberForm memberForm) {
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));

        Member member = Member.builder()
                .email(memberForm.getEmail())
                .username(memberForm.getUsername())
                .password(memberForm.getPassword())
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(member).getMID();
    }

    @Override
    public List<MemberResponseForm> findMembers() {
        List<Member> all = memberRepository.findAll();
        List<MemberResponseForm> members = new ArrayList<>();

        for (Member member : all) {
            MemberResponseForm memberForm = MemberResponseForm.builder()
                    .member(member)
                    .build();
            members.add(memberForm);
        }

        return members;
    }
}
