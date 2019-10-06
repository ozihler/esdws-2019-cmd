package library.domain.values;

import library.Customer;

import java.util.List;

public class RentalRecord {
    private final Customer customer;
    private final List<Rental> rentals;

    public RentalRecord(Customer customer, List<Rental> rentals) {
        this.customer = customer;
        this.rentals = rentals;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public double getTotalAmount() {
        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.getAmount();
        }
        return totalAmount;
    }

    public int getFrequentRenterPoints() {
        int frequentRenterPoints = 0;
        for (Rental rental : rentals) {
            frequentRenterPoints += rental.getFrequentRenterPoints();
        }
        return frequentRenterPoints;
    }

    public List<Rental> getRentals() {
        return rentals;
    }
}
