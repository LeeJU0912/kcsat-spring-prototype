package hpclab.ksatengmaker_spring.myBook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;
import hpclab.ksatengmaker_spring.myBook.domain.QBookQuestion;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class BookQuestionCustomRepositoryImpl implements BookQuestionCustomRepository {

    JPAQueryFactory queryFactory;

    public BookQuestionCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<BookQuestion> getBookQuestion(Long bookId, Long questionId) {
        QBookQuestion bookQuestion = new QBookQuestion("bookQuestion");

        BookQuestion result = queryFactory
                .selectFrom(bookQuestion)
                .where()
                .fetchFirst();

        return Optional.ofNullable(result);
    }
}
