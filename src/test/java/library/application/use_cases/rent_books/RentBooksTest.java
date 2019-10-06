package library.application.use_cases.rent_books;

import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.application.use_cases.rent_books.ports.RentBooksRequest;
import library.domain.entities.Book;
import library.domain.entities.Customer;
import library.domain.values.RentalDocument;
import library.domain.values.RentalRecordDocument;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RentBooksTest {
    @Test
    public void testRentBooksExecuteWith() {
        new RentBooks(name -> new Customer(name))
                .executeWith(testInput(), rentalRecord -> assertResults(rentalRecord));
    }

    private RentBooksInput testInput() {
        RentBookRequest rentBookRequest = new RentBookRequest(1, 5);
        RentBooksRequest rentBooksRequest = new RentBooksRequest("userX", List.of(rentBookRequest));
        return new RentBooksInput(bookId -> testBook(bookId), rentBooksRequest);
    }

    private Book testBook(int bookId) {
        return new Book(bookId, "Hello", "World", "BOTH", "link");
    }

    private void assertResults(RentalRecordDocument rentalRecord) {
        assertRentalRecord(rentalRecord);
        assertRentals(rentalRecord.getRentals());
    }

    private void assertRentalRecord(RentalRecordDocument rentalRecord) {
        assertEquals(testInput().getCustomerName(), rentalRecord.getCustomerName());
        assertEquals(testInput().getRentals().get(0).getFrequentRenterPoints(), rentalRecord.getFrequentRenterPoints(), 0.001);
        assertEquals(testInput().getRentals().get(0).getAmount(), rentalRecord.getTotalAmount(), 0.001);
    }

    private void assertRentals(List<RentalDocument> rentals) {
        assertEquals(1, rentals.size());
        assertEquals(testBook(1).getTitle(), rentals.get(0).getBookTitle());
        assertEquals(testBook(1).getAuthors(), rentals.get(0).getBookAuthors());
        assertEquals(testInput().getRentals().get(0).getDaysRented(), rentals.get(0).getDaysRented());
        assertEquals(testInput().getRentals().get(0).getAmount(), rentals.get(0).getAmount(), 0.001);
    }
}
