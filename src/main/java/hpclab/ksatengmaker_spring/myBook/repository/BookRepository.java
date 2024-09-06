package hpclab.ksatengmaker_spring.myBook.repository;

import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.myBook.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByEmail(String email);
}
