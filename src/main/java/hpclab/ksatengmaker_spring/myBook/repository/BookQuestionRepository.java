package hpclab.ksatengmaker_spring.myBook.repository;

import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookQuestionRepository extends JpaRepository<BookQuestion, Long>, BookQuestionCustomRepository {
}
