package hpclab.ksatengmaker_spring.community.repository;

import hpclab.ksatengmaker_spring.community.domain.Post;
import hpclab.ksatengmaker_spring.questionGenerator.domain.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findByQuestionTypeAndTitle(Pageable pageable, String title, QuestionType type);
}
