package library.application.use_cases.rent_books;

import library.Customer;
import library.InMemoryCustomerRepository;
import library.adapters.file_persistence.FileBasedBookRepository;
import library.adapters.rest.RestRentalRecordPresenter;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.domain.values.Rental;
import library.domain.values.RentalRecord;

import java.util.List;

public class RentBooks {
    private final InMemoryCustomerRepository customerRepository;

    public RentBooks(InMemoryCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void executeWith(RentBooksInput rentBooksInput, RestRentalRecordPresenter rentalRecordPresenter) {
        Customer customer = this.customerRepository.findByUsername(rentBooksInput.getCustomerName());
        List<Rental> rentals = rentBooksInput.getRentals();
        RentalRecord rentalRecord = new RentalRecord(customer, rentals);
        rentalRecordPresenter.present(rentalRecord);
    }

}
