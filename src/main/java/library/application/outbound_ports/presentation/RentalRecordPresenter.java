package library.application.outbound_ports.presentation;

import library.domain.values.RentalRecordDocument;

public interface RentalRecordPresenter {
    void present(RentalRecordDocument rentalRecord);
}
