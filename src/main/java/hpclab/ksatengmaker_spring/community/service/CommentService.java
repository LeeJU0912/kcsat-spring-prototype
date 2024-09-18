package hpclab.ksatengmaker_spring.community.service;

import hpclab.ksatengmaker_spring.community.domain.Comment;
import hpclab.ksatengmaker_spring.community.dto.CommentResponseForm;
import hpclab.ksatengmaker_spring.community.dto.CommentWriteForm;

import java.util.List;

public interface CommentService {

    Long writeComment(CommentWriteForm commentWriteForm, Long id, String email);

    List<CommentResponseForm> getHotComments(Long pId);

    List<CommentResponseForm> getComments(Long id);

    void deleteComment(Long id);

    String setCommentCount(Long commentId);

    String increaseCommentCount(Long commentId);

    String decreaseCommentCount(Long commentId);

    String getIncreaseCommentCount(Long commentId);

    String getDecreaseCommentCount(Long commentId);
}