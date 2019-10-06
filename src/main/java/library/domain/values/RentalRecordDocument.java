package library.domain.values;

import java.util.List;

public class RentalRecordDocument {
    private final String customerName;
    private final double totalAmount;
    private final int frequentRenterPoints;
    private final List<RentalDocument> rentals;

    RentalRecordDocument(String customerName,
                         double totalAmount,
                         int frequentRenterPoints,
                         List<RentalDocument> rentals) {
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.frequentRenterPoints = frequentRenterPoints;
        this.rentals = rentals;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getFrequentRenterPoints() {
        return frequentRenterPoints;
    }

    public List<RentalDocument> getRentals() {
        return rentals;
    }
}
