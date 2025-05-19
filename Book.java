import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String year;
    private boolean isBorrowed;

    public Book(String title, String author, String year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isBorrowed = false;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public boolean isBorrowed() { return isBorrowed; }

    public boolean borrow() {
        if (isBorrowed) {
            return false;
        }
        isBorrowed = true;
        return true;
    }

    public void returnBook() {
        isBorrowed = false;
    }

    @Override
    public String toString() {
        return title + "," + author + "," + year + "," + isBorrowed;
    }

    public static Book fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 4) return null; 

        String title = parts[0].trim();
        String author = parts[1].trim();
        String year = parts[2].trim();
        boolean borrowed = Boolean.parseBoolean(parts[3].trim());

        Book book = new Book(title, author, year);
        book.isBorrowed = borrowed;
        return book;
    }
}