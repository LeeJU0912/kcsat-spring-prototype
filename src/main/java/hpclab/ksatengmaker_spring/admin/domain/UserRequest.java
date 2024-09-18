package hpclab.ksatengmaker_spring.admin.domain;

import hpclab.ksatengmaker_spring.admin.dto.RequestType;
import hpclab.ksatengmaker_spring.community.domain.BaseTimeEntity;
import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private RequestType type;

    private String content;

    private String username;
    private Long qId;
}
