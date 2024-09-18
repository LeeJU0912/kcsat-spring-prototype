package hpclab.ksatengmaker_spring.admin.service;

import hpclab.ksatengmaker_spring.admin.domain.UserRequest;
import hpclab.ksatengmaker_spring.admin.dto.RequestType;
import hpclab.ksatengmaker_spring.admin.dto.UserRequestRequestForm;
import hpclab.ksatengmaker_spring.admin.dto.UserRequestResponseForm;
import hpclab.ksatengmaker_spring.admin.repository.UserRequestRepository;
import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRequestServiceImpl implements UserRequestService {

    private final UserRequestRepository userRequestRepository;
    private final MemberRepository memberRepository;
    private final QuestionJPARepository questionRepository;

    @Override
    public UserRequestResponseForm getQuestionErrorForm(Long qId) {
        return UserRequestResponseForm.builder()
                .type(RequestType.QUESTION_ERROR)
                .content("QUESTION_ERROR")
                .question(questionRepository.findById(qId).orElseThrow(() -> new IllegalArgumentException("찾는 문제 엄서여")))
                .member(memberRepository.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("찾는 사람 엄서여")))
                .build();
    }

    @Override
    public UserRequestResponseForm getGoodSayingForm(UserRequestRequestForm form) {
        return UserRequestResponseForm.builder()
                .type(RequestType.GOOD_SAYING)
                .content(form.getContent())
                .member(memberRepository.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("찾는 사람 엄서여")))
                .build();
    }

    @Override
    public UserRequestResponseForm getImprovingForm(UserRequestRequestForm form) {
        return UserRequestResponseForm.builder()
                .type(RequestType.IMPROVING)
                .content(form.getContent())
                .member(memberRepository.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("찾는 사람 엄서여")))
                .build();
    }

    @Override
    public UserRequestResponseForm getETCForm(UserRequestRequestForm form) {
        return UserRequestResponseForm.builder()
                .type(RequestType.ETC)
                .content(form.getContent())
                .member(memberRepository.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("찾는 사람 엄서여")))
                .build();
    }

    @Override
    public void saveUserRequest(UserRequestResponseForm form) {
        String email = getUserEmail();

        userRequestRepository.save(UserRequest.builder()
                .type(form.getType())
                .content(form.getContent())
                .username(email)
                .qId(form.getQuestion().getId())
                .build());
    }

    @Override
    public List<UserRequestResponseForm> getUserRequests() {
        String email = getUserEmail();

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("찾는 유저 업서요"));

        List<UserRequest> requests = userRequestRepository.findAll();
        List<UserRequestResponseForm> forms = new ArrayList<>();

        for (UserRequest request : requests) {
            UserRequestResponseForm form = UserRequestResponseForm.builder()
                    .type(request.getType())
                    .content(request.getContent())
                    .member(member)
                    .question(questionRepository.findById(request.getQId()).orElseThrow(() -> new IllegalArgumentException("없는 질문이에여")))
                    .build();

            forms.add(form);
        }

        return forms;
    }

    private static String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
