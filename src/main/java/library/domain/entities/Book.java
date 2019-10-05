package library.domain.entities;

public class Book {
    private final int id;
    private final String title;
    private final String authors;
    private final String readingMode;
    private final String link;

    public Book(int id, String title, String authors, String readingMode, String link) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.readingMode = readingMode;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getReadingMode() {
        return readingMode;
    }

    public String getLink() {
        return link;
    }
}
