package library.application.use_cases.rent_books;

import library.domain.entities.Customer;
import library.application.outbound_ports.CustomerRepository;
import library.application.outbound_ports.presentation.RentalRecordPresenter;
import library.application.use_cases.rent_books.ports.IRentBooks;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.domain.values.Rental;
import library.domain.values.RentalRecord;
import library.domain.values.RentalRecordDocument;

import java.util.List;

public class RentBooks implements IRentBooks {
    private final CustomerRepository customerRepository;

    public RentBooks(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void executeWith(RentBooksInput rentBooksInput, RentalRecordPresenter rentalRecordPresenter) {
        Customer customer = this.customerRepository.findByUsername(rentBooksInput.getCustomerName());
        List<Rental> rentals = rentBooksInput.getRentals();
        RentalRecord rentalRecord = new RentalRecord(customer, rentals);
        RentalRecordDocument rentalRecordDocument = rentalRecord.asDocument();
        rentalRecordPresenter.present(rentalRecordDocument);
    }
}
