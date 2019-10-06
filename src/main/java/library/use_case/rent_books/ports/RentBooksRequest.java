package library.use_case.rent_books.ports;

import library.application.use_cases.rent_books.ports.RentBookRequest;

import java.util.List;

public class RentBooksRequest {
    private final String customerName;
    private final List<RentBookRequest> rentBookRequests;

    public RentBooksRequest(String customerName, List<RentBookRequest> rentBookRequests) {
        this.customerName = customerName;
        this.rentBookRequests = rentBookRequests;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<RentBookRequest> getRentBookRequests() {
        return rentBookRequests;
    }
}
