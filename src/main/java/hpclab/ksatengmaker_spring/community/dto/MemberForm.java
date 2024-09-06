package hpclab.ksatengmaker_spring.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberForm {

    private String email;
    private String username;
    private String password;
}
