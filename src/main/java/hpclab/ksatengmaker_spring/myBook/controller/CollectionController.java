package hpclab.ksatengmaker_spring.myBook.controller;

import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.repository.BookQuestionRepository;
import hpclab.ksatengmaker_spring.myBook.service.BookQuestionService;
import hpclab.ksatengmaker_spring.myBook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CollectionController {

    private final BookService bookService;
    private final BookQuestionService bookQuestionService;
    private final BookQuestionRepository bookQuestionRepository;

    @GetMapping("/myBook")
    public String collection(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        Book book = bookService.findBook(userDetails.getUsername());

        model.addAttribute("myBook", book);

        return "collection/myBook";
    }
}