package library.application.use_cases.rent_books;

import library.application.outbound_ports.BookRepository;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.application.use_cases.rent_books.ports.RentBooksRequest;
import library.domain.entities.Book;
import library.domain.entities.Customer;
import library.domain.values.RentalDocument;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RentBooksTest {

    @Test
    public void testExecuteWith() {
        Book book = new Book(
                1,
                "Hello",
                "World",
                "ReadingMode",
                "link"
        );

        BookRepository books = new BookRepository() {
            @Override
            public List<Book> getAllBooks() {
                return null;
            }

            @Override
            public Book findById(int bookId) {
                return book;
            }
        };

        RentBooks rentBooks = new RentBooks(Customer::new);

        RentBookRequest rentBookRequest = new RentBookRequest(1, 5);
        RentBooksRequest rentBooksRequest = new RentBooksRequest("userX", List.of(rentBookRequest));
        RentBooksInput rentBooksInput = new RentBooksInput(books, rentBooksRequest);

        rentBooks.executeWith(
                rentBooksInput,
                rentalRecord -> {
                    List<RentalDocument> rentals = rentalRecord.getRentals();
                    assertEquals(1, rentals.size());
                    assertEquals(book.getTitle(), rentals.get(0).getBookTitle());
                });

    }
}