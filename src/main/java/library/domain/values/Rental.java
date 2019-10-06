package library.domain.values;

import library.domain.entities.Book;

public class Rental {
    private final Book book;
    private final int daysRented;

    public Rental(Book book, int daysRented) {
        this.book = book;
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public String getBookTitle() {
        return book.getTitle();
    }

    public String getBookAuthors() {
        return book.getAuthors();
    }

    public int getFrequentRenterPoints() {
        // add frequent renter points
       int  frequentRenterPoints = 1;

        // add bonus for a reading mode "both"
        if (book.getReadingMode().equals("BOTH") && daysRented > 1) {
            frequentRenterPoints++;
        }
        return frequentRenterPoints;
    }

    public double getAmount() {
        double thisAmount = 0;
        switch (book.getReadingMode()) {
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
        return thisAmount;
    }
}
