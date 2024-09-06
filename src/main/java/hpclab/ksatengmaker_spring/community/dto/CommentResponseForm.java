package hpclab.ksatengmaker_spring.community.dto;

import hpclab.ksatengmaker_spring.community.domain.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponseForm {

    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private Long CId;
    private String content;
    private String username;
    private String email;

    @Builder
    public CommentResponseForm(Comment comment) {
        this.createdDateTime = comment.getCreatedDate();
        this.modifiedDateTime = comment.getLastModifiedDate();
        this.CId = comment.getCId();
        this.content = comment.getContent();
        this.username = comment.getMember().getUsername();
        this.email = comment.getMember().getEmail();
    }
}
