package library.adapters.file_persistence;

import library.application.outbound_ports.persistence.IFindSingleBooks;
import library.application.outbound_ports.persistence.IRetrieveAllBooks;
import library.domain.entities.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBasedBookRepository implements IFindSingleBooks, IRetrieveAllBooks {

    private final List<Book> books;

    public FileBasedBookRepository(InputStream bookFileAsStream) throws IOException {
        // fetch books
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        bookFileAsStream,
                        StandardCharsets.UTF_8
                )
        );
        books = new ArrayList<>();
        while (bufferedReader.ready()) {
            final String line = bufferedReader.readLine();
            final String[] bookData = line.split(";");
            Book book = new Book(Integer.parseInt(bookData[0]), bookData[1], bookData[2], bookData[3], bookData[4]);
            books.add(book);
        }
    }

    @Override
    public List<Book> get() {
        return books;
    }

    @Override
    public Book byId(int bookId) {
        return books.get(bookId);
    }
}
