package hpclab.ksatengmaker_spring.questionGenerator.repository;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJPARepository extends JpaRepository<Question, Long> {
    Question findById(long id);
}
