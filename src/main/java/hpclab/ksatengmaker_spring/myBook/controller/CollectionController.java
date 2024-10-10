package hpclab.ksatengmaker_spring.myBook.controller;

import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.service.BookService;
import hpclab.ksatengmaker_spring.questionGenerator.domain.Question;
import hpclab.ksatengmaker_spring.questionGenerator.repository.QuestionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class CollectionController {

    private final BookService bookService;
    private final QuestionJPARepository questionJPARepository;

    @GetMapping("/myBook")
    public String myQuestion(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        Book book = bookService.findBook(userDetails.getUsername());

        model.addAttribute("myBook", book);

        return "collection/myBook";
    }

    @GetMapping("/myBook/{id}")
    public String myQuestionDetail(@PathVariable Long id, Model model) {
        Question question = questionJPARepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find question"));

        model.addAttribute("question", question);

        return "collection/myBookDetail";
    }
}