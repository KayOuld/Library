import java.io.*;
import java.util.*;

public class BookLibrary {
    private List<Book> books = new ArrayList<>();
    private final String filename;

    public BookLibrary() {
        this.filename = "Books";  
        loadBooks();
    }

    private void loadBooks() {
        books.clear();
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }

    public void saveBooks() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Book b : books) {
                pw.println(b.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

   
    public boolean borrowBook(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                if (!b.isBorrowed()) {
                    b.borrow();
                    saveBooks();
                    return true;
                }
                return false; 
            }
        }
        return false; 
    }

    public boolean returnBook(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                if (b.isBorrowed()) {
                    b.returnBook();
                    saveBooks();
                    return true;
                }
                return false; 
            }
        }
        return false; 
}
}