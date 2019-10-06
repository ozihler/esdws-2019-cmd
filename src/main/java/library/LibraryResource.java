package library;

import library.adapters.file_persistence.FileBasedBookRepository;
import library.domain.entities.Book;
import library.domain.values.Rental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryResource {
    private final FileBasedBookRepository bookRepository;
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

    public List<String> calculateFee(List<String> rentBooksRequests) {
        if (rentBooksRequests == null || rentBooksRequests.size() == 0) {
            throw new IllegalArgumentException("rent books requests cannot be null!");
        }
        String customerName = rentBooksRequests.remove(0);

        // fetch customer
        Customer customer = customerRepository.findByUsername(customerName);

        // calculate fee, frequent renter points, and document to display in front end
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for " + customer.getName() + "\n";

        for (int i = 0; i < rentBooksRequests.size(); i++) {
            final String[] rentalData = rentBooksRequests.get(i).split(" ");
            int bookId = Integer.parseInt(rentalData[0]);
            int daysRented = Integer.parseInt(rentalData[1]);

            Book book = bookRepository.findById(bookId);
            Rental rental = new Rental(book, daysRented);

            frequentRenterPoints += rental.getFrequentRenterPoints();

            // create figures for this rental
            result += "\t'" + rental.getBookTitle() + "' by '" + rental.getBookAuthors() + "' for " + rental.getDaysRented() + " days: \t" + rental.getAmount() + " $\n";
            totalAmount += rental.getAmount();
        }

        // add footer lines
        result += "You owe " + totalAmount + " $\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points\n";

        return List.of(result);
    }

}

