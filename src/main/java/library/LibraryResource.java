package library;

import library.adapters.file_persistence.FileBasedBookRepository;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.domain.entities.Book;
import library.domain.values.Rental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryResource {
    private FileBasedBookRepository bookRepository;
    private InMemoryCustomerRepository customerRepository;

    public LibraryResource() throws IOException {
        this.customerRepository = new InMemoryCustomerRepository();
        this.bookRepository = new FileBasedBookRepository(getClass().getResourceAsStream("books.csv"));
    }

    public List<String[]> getBooks() {
        List<String[]> books = new ArrayList<>();
        for (Book book : bookRepository.getAllBooks()) {
            books.add(new String[]{
                    String.valueOf(book.getId()),
                    book.getTitle(),
                    book.getAuthors(),
                    book.getReadingMode(),
                    book.getThumbnailLink()
            });
        }
        return books;
    }

    public List<String> calculateFee(List<String> rentBooksRequestData) {
        if (rentBooksRequestData == null || rentBooksRequestData.size() == 0) {
            throw new IllegalArgumentException("rent books requests cannot be null!");
        }
        String customerName = rentBooksRequestData.remove(0);

        // fetch customer
        Customer customer = customerRepository.findByUsername(customerName);

        // calculate fee, frequent renter points, and document to display in front end
        List<RentBookRequest> rentBookRequests = getRentBookRequests(rentBooksRequestData);

        List<Rental> rentals = getRentals(rentBookRequests);

        String result = "Rental Record for " + customer.getName() + "\n";
        result += format(rentals);
        // add footer lines
        result += "You owe " + getTotalAmount(rentals) + " $\n";
        result += "You earned " + getFrequentRenterPoints(rentals) + " frequent renter points\n";

        return List.of(result);
    }

    private List<RentBookRequest> getRentBookRequests(List<String> rentBooksRequestData) {
        List<RentBookRequest> rentBookRequests = new ArrayList<>();
        for (int i = 0; i < rentBooksRequestData.size(); i++) {
            final String[] rentBookRequestData = rentBooksRequestData.get(i).split(" ");
            RentBookRequest rentBookRequest = new RentBookRequest(
                    Integer.parseInt(rentBookRequestData[0]),
                    Integer.parseInt(rentBookRequestData[1])
            );
            rentBookRequests.add(rentBookRequest);
        }
        return rentBookRequests;
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

    private double getTotalAmount(List<Rental> rentals) {
        double totalAmount = 0;
        for (Rental rental : rentals) {
            totalAmount += rental.getAmount();
        }
        return totalAmount;
    }

    private int getFrequentRenterPoints(List<Rental> rentals) {
        int frequentRenterPoints = 0;
        for (Rental rental : rentals) {
            frequentRenterPoints += rental.getFrequentRenterPoints();
        }
        return frequentRenterPoints;
    }

    private String format(List<Rental> rentals) {
        String result = "";
        for (Rental rental : rentals) {
            // create figures for this rental
            result += "\t'" + rental.getBookTitle() + "' by '" + rental.getBookAuthors() + "' for " + rental.getDaysRented() + " days: \t" + rental.getAmount() + " $\n";
        }
        return result;
    }

}

