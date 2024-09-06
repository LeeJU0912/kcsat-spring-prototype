package hpclab.ksatengmaker_spring.myBook.service;

import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;
import hpclab.ksatengmaker_spring.myBook.repository.BookQuestionRepository;
import hpclab.ksatengmaker_spring.myBook.repository.BookRepository;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionRepository;
import hpclab.ksatengmaker_spring.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookQuestionServiceImpl implements BookQuestionService {
    private final BookRepository bookRepository;
    private final BookQuestionRepository bookQuestionRepository;
    private final QuestionJPARepository questionJPARepository;

    @Override
    public Long saveQuestion(Question question) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        //question.upShareCounter();

        questionJPARepository.save(question);

        Book book = bookRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        bookQuestionRepository.save(new BookQuestion(book, question));

        return book.getId();
    }
}
