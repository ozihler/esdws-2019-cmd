package library.application.use_cases.rent_books;

import library.application.outbound_ports.BookRepository;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.application.use_cases.rent_books.ports.RentBooksRequest;
import library.domain.entities.Book;
import library.domain.entities.Customer;
import library.domain.values.Rental;
import library.domain.values.RentalDocument;
import library.domain.values.RentalRecordDocument;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RentBooksTest {

    private Book book;
    private RentBooksInput rentBooksInput;

    @Test
    public void testExecuteWith() {
        book = new Book(
                1,
                "Hello",
                "World",
                "BOTH",
                "link"
        );

        BookRepository booksRepository = new BookRepository() {
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
        rentBooksInput = new RentBooksInput(booksRepository, rentBooksRequest);

        rentBooks.executeWith(rentBooksInput, this::assertResults);

    }

    private void assertResults(RentalRecordDocument rentalRecord) {
        List<RentalDocument> rentals = rentalRecord.getRentals();
        assertEquals(1, rentals.size());
        assertEquals(book.getTitle(), rentals.get(0).getBookTitle());
        assertEquals(book.getAuthors(), rentals.get(0).getBookAuthors());

        assertEquals(rentBooksInput.getCustomerName(), rentalRecord.getCustomerName());
        assertEquals(rentBooksInput.getRentals().get(0).getDaysRented(), rentals.get(0).getDaysRented());
        assertEquals(rentBooksInput.getRentals().get(0).getAmount(), rentals.get(0).getAmount(), 0.001);
        assertEquals(rentBooksInput.getRentals().get(0).getFrequentRenterPoints(), rentalRecord.getFrequentRenterPoints(), 0.001);
        assertEquals(rentBooksInput.getRentals().get(0).getAmount(), rentalRecord.getTotalAmount(), 0.001);
    }

}