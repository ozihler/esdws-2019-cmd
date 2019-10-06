package library.application.outbound_ports;

import library.domain.entities.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAllBooks();

    Book findById(int bookId);
}
