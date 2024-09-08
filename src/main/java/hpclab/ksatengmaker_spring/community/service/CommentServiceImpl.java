package hpclab.ksatengmaker_spring.community.service;

import hpclab.ksatengmaker_spring.community.domain.Comment;
import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.community.dto.CommentResponseForm;
import hpclab.ksatengmaker_spring.community.dto.CommentWriteForm;
import hpclab.ksatengmaker_spring.community.repository.CommentRepository;
import hpclab.ksatengmaker_spring.community.repository.MemberRepository;
import hpclab.ksatengmaker_spring.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long writeComment(CommentWriteForm commentWriteForm, Long id, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 게시물입니다."));

        Comment comment = Comment.builder()
                .content(commentWriteForm.getContent())
                .member(member)
                .post(post)
                .build();

        commentRepository.save(comment);

        return comment.getCId();
    }

    @Override
    public List<CommentResponseForm> getComments(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(comment -> CommentResponseForm.builder()
                        .comment(comment)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);

    }

    @Override
    public String setCommentCount(Long commentId) {

        String upVote = "comment:" + commentId + ":upVote";
        String downVote = "comment:" + commentId + ":downVote";
        redisTemplate.opsForValue().set(upVote, "0");
        redisTemplate.opsForValue().set(downVote, "0");

        return redisTemplate.opsForValue().get(upVote);
    }

    @Override
    public String increaseCommentCount(Long commentId) {

        String upVote = "comment:" + commentId + ":upVote";
        String user = "comment:" + commentId + ":user:" + getUserEmail();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(user))) {
            // 예외 발생
        }
        else {
            redisTemplate.opsForValue().increment(upVote);
            redisTemplate.opsForValue().set(user, "1");
        }

        return redisTemplate.opsForValue().get(upVote);
    }


    @Override
    public String decreaseCommentCount(Long commentId) {

        String downVote = "comment:" + commentId + ":downVote";
        String user = "comment:" + commentId + ":user:" + getUserEmail();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(user))) {
            // 예외 발생
        }
        else {
            redisTemplate.opsForValue().increment(downVote);
            redisTemplate.opsForValue().set(user, "1");
        }

        return redisTemplate.opsForValue().get(downVote);
    }

    private static String getUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public String getIncreaseCommentCount(Long commentId) {

        return redisTemplate.opsForValue().get("comment:" + commentId + ":upVote");
    }

    @Override
    public String getDecreaseCommentCount(Long commentId) {

        return redisTemplate.opsForValue().get("comment:" + commentId + ":downVote");
    }
}