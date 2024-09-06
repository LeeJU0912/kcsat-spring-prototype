package hpclab.ksatengmaker_spring.myBook.repository;

import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;

import java.util.Optional;

public interface BookQuestionCustomRepository {
    Optional<BookQuestion> getBookQuestion(Long bookId, Long questionId);
}
