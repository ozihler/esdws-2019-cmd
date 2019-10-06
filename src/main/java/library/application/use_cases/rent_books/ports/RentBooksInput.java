package library.application.use_cases.rent_books.ports;

import library.application.outbound_ports.persistence.IFindSingleBooks;
import library.domain.entities.Book;
import library.domain.values.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentBooksInput {
    private final RentBooksRequest rentBooksRequest;
    private IFindSingleBooks IFindSingleBooks;

    public RentBooksInput(IFindSingleBooks IFindSingleBooks, RentBooksRequest rentBooksRequest) {
        this.rentBooksRequest = rentBooksRequest;
        this.IFindSingleBooks = IFindSingleBooks;
    }

    public String getCustomerName() {
        return rentBooksRequest.getCustomerName();
    }

    public List<Rental> getRentals() {
        List<Rental> rentals = new ArrayList<>();
        for (RentBookRequest rentBookRequest : rentBooksRequest.getRentBookRequests()) {
            Book book = this.IFindSingleBooks.byId(rentBookRequest.getBookId());
            Rental rental = new Rental(book, rentBookRequest.getDaysRented());
            rentals.add(rental);
        }
        return rentals;
    }
}
