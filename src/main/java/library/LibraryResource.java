package library;

import library.adapters.file_persistence.FileBasedBookRepository;
import library.adapters.rest.RestRentalRecordPresenter;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.domain.entities.Book;
import library.domain.values.Rental;
import library.domain.values.RentalRecord;

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

        RentalRecord rentalRecord = new RentalRecord(customer, rentals);

        RestRentalRecordPresenter rentalRecordPresenter = new RestRentalRecordPresenter();
        rentalRecordPresenter.present(rentalRecord);

        return rentalRecordPresenter.presentation();
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

}

