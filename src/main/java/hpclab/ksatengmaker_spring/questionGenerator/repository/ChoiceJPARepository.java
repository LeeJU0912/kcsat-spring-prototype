package hpclab.ksatengmaker_spring.questionGenerator.repository;

import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceJPARepository extends JpaRepository<Choice, Integer> {
}
