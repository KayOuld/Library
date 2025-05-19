import java.io.*;
import java.util.*;

// Abstraction
abstract class LibraryUser {
    protected String id;
    protected String name;

    public LibraryUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract double calculateFine(int daysLate);
}

// Inheritance
class Member extends LibraryUser {
    public Member(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 0.5;
    }
}

class StudentMember extends Member {
    public StudentMember(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 0.25;
    }
}

class FacultyMember extends Member {
    public FacultyMember(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 0.1;
    }
}

class Book {
    private String isbn;
    private String title;
    private boolean isAvailable = true;

    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    @Override
    public String toString() {
        return title + " (ISBN: " + isbn + ")";
    }
}

class Loan {
    private Book book;
    private Member member;
    private Date loanDate;

    public Loan(Book book, Member member, Date loanDate) {
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
    }

    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public Date getLoanDate() { return loanDate; }
}

class Reservation {
    private Member member;
    private Book book;
    private Date reserveDate;

    public Reservation(Member member, Book book, Date reserveDate) {
        this.member = member;
        this.book = book;
        this.reserveDate = reserveDate;
    }

    public Member getMember() { return member; }
    public Book getBook() { return book; }
    public Date getReserveDate() { return reserveDate; }
}

class Library {
    List<Book> books = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    List<Loan> loans = new ArrayList<>();
    Queue<Reservation> reservationQueue = new LinkedList<>();

    public void addBook(Book book) { books.add(book); }
    public void addMember(Member member) { members.add(member); }

    public void loanBook(String isbn, String memberId) throws Exception {
        Book book = findBook(isbn);
        Member member = findMember(memberId);

        if (!book.isAvailable()) throw new Exception("Book not available");

        book.setAvailable(false);
        loans.add(new Loan(book, member, new Date()));
    }

    public void reserveBook(String isbn, String memberId) {
        Book book = findBook(isbn);
        Member member = findMember(memberId);
        reservationQueue.add(new Reservation(member, book, new Date()));
    }

    public void saveBooksToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Book book : books) {
            writer.write(book.getIsbn() + "," + book.getTitle() + "," + book.isAvailable());
            writer.newLine();
        }
        writer.close();
    }

    public void loadBooksFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Book book = new Book(parts[0], parts[1]);
            book.setAvailable(Boolean.parseBoolean(parts[2]));
            books.add(book);
        }
        reader.close();
    }

    private Book findBook(String isbn) {
        return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    private Member findMember(String id) {
        return members.stream().filter(m -> m.id.equals(id)).findFirst().orElse(null);
    }

    // TEST CASES
    public static class LibraryTest {

        public static void runAllTests() {
            testBookAvailability();
            testLoanBookSuccess();
            testLoanBookUnavailable();
            testReservationQueue();
            testFileIO();
        }

        public static void testBookAvailability() {
            Book book = new Book("123", "Test Book");
            System.out.println("Test Book Availability: " + (book.isAvailable() ? "PASS" : "FAIL"));
            book.setAvailable(false);
            System.out.println("Test Book Unavailability: " + (!book.isAvailable() ? "PASS" : "FAIL"));
        }

        public static void testLoanBookSuccess() {
            try {
                Library lib = new Library();
                Book book = new Book("111", "Java Book");
                Member member = new StudentMember("S01", "Alice");
                lib.addBook(book);
                lib.addMember(member);
                lib.loanBook("111", "S01");
                System.out.println("Test Loan Book Success: " + (!book.isAvailable() ? "PASS" : "FAIL"));
            } catch (Exception e) {
                System.out.println("Test Loan Book Success: FAIL - " + e.getMessage());
            }
        }

        public static void testLoanBookUnavailable() {
            try {
                Library lib = new Library();
                Book book = new Book("111", "Java Book");
                Member member = new StudentMember("S01", "Alice");
                lib.addBook(book);
                lib.addMember(member);
                book.setAvailable(false);
                lib.loanBook("111", "S01");
                System.out.println("Test Loan Book Unavailable: FAIL");
            } catch (Exception e) {
                System.out.println("Test Loan Book Unavailable: PASS");
            }
        }

        public static void testReservationQueue() {
            Library lib = new Library();
            Book book = new Book("111", "Java Book");
            Member member = new FacultyMember("F01", "Bob");
            lib.addBook(book);
            lib.addMember(member);
            lib.reserveBook("111", "F01");
            System.out.println("Test Reservation Queue: " + (!lib.reservationQueue.isEmpty() ? "PASS" : "FAIL"));
        }

        public static void testFileIO() {
            try {
                Library lib = new Library();
                Book book = new Book("111", "Java Book");
                lib.addBook(book);
                lib.saveBooksToFile("test_books.txt");

                Library newLib = new Library();
                newLib.loadBooksFromFile("test_books.txt");
                boolean titleMatch = newLib.books.size() == 1 && "Java Book".equals(newLib.books.get(0).getTitle());
                System.out.println("Test File IO: " + (titleMatch ? "PASS" : "FAIL"));
            } catch (IOException e) {
                System.out.println("Test File IO: FAIL - " + e.getMessage());
            }
        }
    }
}

public class LibraryApp {
    public static void main(String[] args) {
        Library lib = new Library();

        Member alice = new StudentMember("S01", "Alice");
        Member bob = new FacultyMember("F01", "Bob");
        Book javaBook = new Book("111", "Java Basics");

        lib.addBook(javaBook);
        lib.addMember(alice);
        lib.addMember(bob);

        try {
            lib.loanBook("111", "S01");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        lib.reserveBook("111", "F01");

        try {
            lib.saveBooksToFile("books.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Run manual test cases
        System.out.println("\nRunning Manual Test Cases:");
        Library.LibraryTest.runAllTests();
    }
}
