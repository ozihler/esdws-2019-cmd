package library.application.use_cases.rent_books;

import library.Customer;
import library.InMemoryCustomerRepository;
import library.adapters.file_persistence.FileBasedBookRepository;
import library.adapters.rest.RestRentalRecordPresenter;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.domain.entities.Book;
import library.domain.values.Rental;
import library.domain.values.RentalRecord;
import library.application.use_cases.rent_books.ports.RentBooksRequest;

import java.util.ArrayList;
import java.util.List;

public class RentBooks {
    private final InMemoryCustomerRepository customerRepository;
    private final FileBasedBookRepository bookRepository;

    public RentBooks(InMemoryCustomerRepository customerRepository, FileBasedBookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public void executeWith(RentBooksRequest rentBooksRequest, RestRentalRecordPresenter rentalRecordPresenter) {
        Customer customer = this.customerRepository.findByUsername(rentBooksRequest.getCustomerName());
        List<Rental> rentals = getRentals(rentBooksRequest.getRentBookRequests());
        RentalRecord rentalRecord = new RentalRecord(customer, rentals);
        rentalRecordPresenter.present(rentalRecord);
    }

    private List<Rental> getRentals(List<RentBookRequest> rentBookRequests) {
        List<Rental> rentals = new ArrayList<>();
        for (RentBookRequest rentBookRequest : rentBookRequests) {
            Book book = bookRepository.findById(rentBookRequest.getBookId());
            Rental rental = new Rental(book, rentBookRequest.getDaysRented());
            rentals.add(rental);
        }
        return rentals;
    }
}
