package library.domain.values;

public class RentalDocument {
    private final double amount;
    private final int daysRented;
    private final String bookAuthors;
    private final String bookTitle;

    public RentalDocument(double amount, int daysRented, String bookAuthors, String bookTitle) {
        this.amount = amount;
        this.daysRented = daysRented;
        this.bookAuthors = bookAuthors;
        this.bookTitle = bookTitle;
    }

    public double getAmount() {
        return amount;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public String getBookAuthors() {
        return bookAuthors;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}
