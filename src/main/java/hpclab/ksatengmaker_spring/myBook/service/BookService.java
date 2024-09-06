package hpclab.ksatengmaker_spring.myBook.service;

import hpclab.ksatengmaker_spring.myBook.domain.Book;
import hpclab.ksatengmaker_spring.myBook.dto.BookResponseForm;

public interface BookService {
    void makeBook(String email);

    Book findBook(String username);

    BookResponseForm BookToBookResponseForm(Book book);
}