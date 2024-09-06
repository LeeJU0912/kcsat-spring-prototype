package hpclab.ksatengmaker_spring.myBook.service;


import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.domain.BookQuestion;
import hpclab.ksatengmaker_spring.myBook.dto.BookResponseForm;
import hpclab.ksatengmaker_spring.myBook.repository.BookQuestionRepository;
import hpclab.ksatengmaker_spring.myBook.repository.BookRepository;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Choice;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.dto.QuestionResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookQuestionRepository bookQuestionRepository;

    @Override
    public void makeBook(String email) {
        Book book = new Book(email);
        bookRepository.save(book);
    }

    @Override
    public Book findBook(String email) {
        return bookRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    public BookResponseForm BookToBookResponseForm(Book book) {
        return BookResponseForm.builder()
                .question(book.getBookQuestions().stream().map(this::questionToDto).toList())
                .build();
    }

    private QuestionResponseForm questionToDto(BookQuestion bookQuestion) {
        Question question = bookQuestion.getQuestion();

        return QuestionResponseForm.builder()
                .questionType(question.getType())
                .title(question.getTitle())
                .mainText(question.getMainText())
                .choices(question.getChoices().stream().map(Choice::getChoice).toList())
                .shareCounter(question.getShareCounter())
                .build();
    }
}
