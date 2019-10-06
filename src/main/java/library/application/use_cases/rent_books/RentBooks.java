package library.application.use_cases.rent_books;

import library.Customer;
import library.InMemoryCustomerRepository;
import library.application.outbound_ports.presentation.RentalRecordPresenter;
import library.application.use_cases.rent_books.ports.IRentBooks;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.domain.values.Rental;
import library.domain.values.RentalRecord;
import library.domain.values.RentalRecordDocument;

import java.util.List;

public class RentBooks implements IRentBooks {
    private final InMemoryCustomerRepository customerRepository;

    public RentBooks(InMemoryCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void executedWith(RentBooksInput rentBooksInput, RentalRecordPresenter rentalRecordPresenter) {
        Customer customer = this.customerRepository.findByUsername(rentBooksInput.getCustomerName());
        List<Rental> rentals = rentBooksInput.getRentals();
        RentalRecord rentalRecord = new RentalRecord(customer, rentals);
        RentalRecordDocument rentalRecordDocument = rentalRecord.asDocument();
        rentalRecordPresenter.present(rentalRecordDocument);
    }
}
