package hpclab.ksatengmaker_spring.admin.dto;

import hpclab.ksatengmaker_spring.admin.domain.UserRequest;
import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class UserRequestResponseForm {

    private RequestType type;
    private Member member;
    private Question question;
    private String content;
}
