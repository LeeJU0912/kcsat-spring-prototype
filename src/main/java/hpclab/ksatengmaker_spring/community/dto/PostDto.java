package hpclab.ksatengmaker_spring.community.dto;

import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PostDto {

    private Long pId;

    private String title;
    private String content;

    private LocalDateTime postDate;

    private String email;
    private String username;

    private String questionType;

    private Question question;

    public PostDto(Post post) {
        this.pId = post.getPId();
        this.title = post.getPostTitle();
        this.content = post.getPostContent();
        this.postDate = post.getCreatedDate();
        this.email = post.getMember().getEmail();
        this.username = post.getMember().getUsername();

        Question question = post.getQuestion();

        if (question == null) {
            this.questionType = "";
        }
        else {
            this.questionType = question.getType().getKrName();
            this.question = question;
        }
    }
}
