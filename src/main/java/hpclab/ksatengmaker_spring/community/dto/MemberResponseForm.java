package hpclab.ksatengmaker_spring.community.dto;

import hpclab.ksatengmaker_spring.community.domain.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberResponseForm {

    private String email;
    private String username;

    @Builder
    public MemberResponseForm(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
    }
}
