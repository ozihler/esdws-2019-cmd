package library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LibraryResource {
    private InMemoryCustomerRepository customerRepository;

    public LibraryResource() {
        this.customerRepository = new InMemoryCustomerRepository();
    }

    public List<String[]> getBooks() throws IOException {
        final List<String[]> books = new ArrayList<>();
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("books.csv"),
                        StandardCharsets.UTF_8
                )
        );
        while (bufferedReader.ready()) {
            final String line = bufferedReader.readLine();
            final String[] book = line.split(";");
            books.add(book);
        }
        return books;
    }

    public List<String> calculateFee(List<String> rentBooksRequests) throws IOException {
        if (rentBooksRequests == null || rentBooksRequests.size() == 0) {
            throw new IllegalArgumentException("rent books requests cannot be null!");
        }
        String customerName = rentBooksRequests.remove(0);

        // fetch customer
        Customer customer = customerRepository.findByUsername(customerName);

        // fetch books
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("books.csv"),
                        StandardCharsets.UTF_8
                )
        );
        final List<String[]> books = new ArrayList<>();
        while (bufferedReader.ready()) {
            final String line = bufferedReader.readLine();
            final String[] book = line.split(";");
            books.add(book);
        }

        // calculate fee, frequent renter points, and document to display in front end
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        String result = "Rental Record for " + customer.getName() + "\n";

        for (int i = 0; i < rentBooksRequests.size(); i++) {
            final String[] rental = rentBooksRequests.get(i).split(" ");
            final String[] book = books.get(Integer.parseInt(rental[0]));
            double thisAmount = 0;

            int daysRented = Integer.parseInt(rental[1]);
            String readingMode = book[3];
            switch (readingMode) {
                case "IMAGE":
                    thisAmount += 2;
                    if (daysRented > 2)
                        thisAmount += (daysRented - 2) * 1.5;
                    break;
                case "TEXT":
                    thisAmount += 1.5;
                    if (daysRented > 3)
                        thisAmount += (daysRented - 3) * 1.5;
                    break;
                case "BOTH":
                    thisAmount += daysRented * 3;
                    break;
            }

            // add frequent renter points
            frequentRenterPoints++;

            // add bonus for a reading mode "both"
            if (readingMode.equals("BOTH") && daysRented > 1) {
                frequentRenterPoints++;
            }

            // create figures for this rental
            result += "\t'" + book[1] + "' by '" + book[2] + "' for " + daysRented + " days: \t" + thisAmount + " $\n";
            totalAmount += thisAmount;
        }

        // add footer lines
        result += "You owe " + totalAmount + " $\n";
        result += "You earned " + frequentRenterPoints + " frequent renter points\n";

        return List.of(result);
    }
}

