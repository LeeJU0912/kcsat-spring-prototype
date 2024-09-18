package hpclab.ksatengmaker_spring.community.service;

import hpclab.ksatengmaker_spring.community.dto.PostResponseForm;
import hpclab.ksatengmaker_spring.community.dto.PostWriteForm;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long savePost(PostWriteForm postWriteForm, String email);

    void saveQuestionFromPost(Long postId);

    Page<PostResponseForm> getPostList(Pageable pageable);

    Page<PostResponseForm> getFindPostList(Pageable pageable, String keyword, QuestionType type);

    Page<PostResponseForm> getHotPostList(Pageable pageable);

    Page<PostResponseForm> getFindHotPostList(Pageable pageable, String keyword, QuestionType type);

    PostResponseForm getPost(Long postId);

    Long updatePost(Long postId, PostWriteForm postWriteForm);

    void removePost(Long id);

    String setPostCount(Long postId);

    String increasePostViewCount(Long postId);

    String getPostViewCount(Long postId);

    String increasePostVoteCount(Long postId);

    String decreasePostVoteCount(Long postId);

    String getIncreasePostVoteCount(Long postId);

    String getDecreasePostVoteCount(Long postId);

    void resetPostView();
}
