package hpclab.ksatengmaker_spring.community.dto;

import hpclab.ksatengmaker_spring.community.domain.Comment;
import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberResponseForm {

    private LocalDateTime createdDate;

    private Long id;
    private String email;
    private String username;

    private List<Post> posts;
    private List<Comment> comments;

    @Builder
    public MemberResponseForm(Member member) {
        this.id = member.getMID();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.createdDate = member.getCreatedDate();
        this.posts = member.getPosts();
        this.comments = member.getComments();
    }
}
