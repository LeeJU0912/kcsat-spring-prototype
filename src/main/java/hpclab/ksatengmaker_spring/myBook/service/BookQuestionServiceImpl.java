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
import org.springframework.data.redis.core.RedisTemplate;
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

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Long saveFirstQuestion(Question question) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        questionJPARepository.save(question);

        Book book = bookRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        bookQuestionRepository.save(new BookQuestion(book, question));

        return book.getId();
    }

    @Override
    public Long saveQuestion(Long qId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Question question = questionJPARepository.findById(qId).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 문제에여."));

        Book book = bookRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        if (redisTemplate.opsForValue().get("question:" + email + ":isSaved:" + qId) == null) {
            redisTemplate.opsForValue().set("question:" + email + ":isSaved:" + qId, "1");
            bookQuestionRepository.save(new BookQuestion(book, question));
        }

        return book.getId();
    }
}
