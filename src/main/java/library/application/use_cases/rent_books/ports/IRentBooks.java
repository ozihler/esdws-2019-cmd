package library.application.use_cases.rent_books.ports;

import library.application.outbound_ports.presentation.RentalRecordPresenter;

public interface IRentBooks {
    void executedWith(RentBooksInput rentBooksInput, RentalRecordPresenter rentalRecordPresenter);
}
