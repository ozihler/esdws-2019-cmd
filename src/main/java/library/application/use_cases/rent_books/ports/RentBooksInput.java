package library.application.use_cases.rent_books.ports;

import library.application.outbound_ports.BookRepository;
import library.domain.entities.Book;
import library.domain.values.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentBooksInput {
    private final RentBooksRequest rentBooksRequest;
    private BookRepository bookRepository;

    public RentBooksInput(BookRepository bookRepository, RentBooksRequest rentBooksRequest) {
        this.rentBooksRequest = rentBooksRequest;
        this.bookRepository = bookRepository;
    }

    public String getCustomerName() {
        return rentBooksRequest.getCustomerName();
    }

    public List<Rental> getRentals() {
        List<Rental> rentals = new ArrayList<>();
        for (RentBookRequest rentBookRequest : rentBooksRequest.getRentBookRequests()) {
            Book book = this.bookRepository.findById(rentBookRequest.getBookId());
            Rental rental = new Rental(book, rentBookRequest.getDaysRented());
            rentals.add(rental);
        }
        return rentals;
    }
}
