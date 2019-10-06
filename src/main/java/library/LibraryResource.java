package library;

import library.adapters.file_persistence.FileBasedBookRepository;
import library.adapters.rest.RestRentalRecordPresenter;
import library.application.outbound_ports.BookRepository;
import library.application.outbound_ports.CustomerRepository;
import library.application.use_cases.rent_books.ports.IRentBooks;
import library.application.use_cases.rent_books.ports.RentBookRequest;
import library.application.use_cases.rent_books.ports.RentBooksInput;
import library.domain.entities.Book;
import library.application.use_cases.rent_books.RentBooks;
import library.application.use_cases.rent_books.ports.RentBooksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryResource {
    private BookRepository bookRepository;
    private CustomerRepository customerRepository;

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
        List<RentBookRequest> rentBookRequests = getRentBookRequests(rentBooksRequestData);
        RentBooksRequest rentBooksRequest = new RentBooksRequest(customerName, rentBookRequests);
        RentBooksInput rentBooksInput = new RentBooksInput(bookRepository, rentBooksRequest);

        RestRentalRecordPresenter rentalRecordPresenter = new RestRentalRecordPresenter();
        IRentBooks iRentBooks = new RentBooks(customerRepository);
        iRentBooks.executedWith(rentBooksInput, rentalRecordPresenter);

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

}

