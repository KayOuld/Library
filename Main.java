import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookLibrary library = new BookLibrary();

            library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "1925"));
            library.addBook(new Book("1984", "George Orwell", "1949"));
            library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "1960"));
            library.addBook(new Book("Pride and Prejudice", "Jane Austen", "1813"));
            library.addBook(new Book("Moby-Dick", "Herman Melville", "1851"));
            library.addBook(new Book("War and Peace", "Leo Tolstoy", "1869"));
            library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", "1951"));
            library.addBook(new Book("Brave New World", "Aldous Huxley", "1932"));
            library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "1937"));
            library.addBook(new Book("Jane Eyre", "Charlotte Brontë", "1847"));

        

        while (true) {
            System.out.println("\nEnter 1 for Librarian, 2 for Member, 3 to exit");
            String choice = scanner.next();
            scanner.nextLine(); 

            if (choice.equals("3")) {
                System.out.println("Exiting...");
                break;
            }
            else if (choice.equals("1")) {
                System.out.println("Enter your name:");
                String name = scanner.nextLine();
                System.out.println("Enter your password:");
                String pass = scanner.nextLine();
                Librarian li = new Librarian();
                if (li.getName().equals(name) && li.getPass().equals(pass)) {
                    System.out.println("You are a librarian");
                    while (true) {
                        System.out.println("Do you want to add a new book? (yes/no)");
                        String add = scanner.nextLine();
                        if (add.equalsIgnoreCase("yes")) {
                            System.out.println("Enter book title:");
                            String title = scanner.nextLine();
                            System.out.println("Enter book author:");
                            String author = scanner.nextLine();
                            System.out.println("Enter book year:");
                            String year = scanner.nextLine();
                            library.addBook(new Book(title, author, year));
                            System.out.println("Book added successfully.");
                        } else if (add.equalsIgnoreCase("no")) {
                            System.out.println("Exiting librarian menu...");
                            break;
                        } else {
                            System.out.println("Invalid choice, please try again.");
                        }
                    }
                } else {
                    System.out.println("You are not a librarian.");
                }

            } else if (choice.equals("2")) {
                System.out.println("Do you want to create a new member? (yes/no)");
                String create = scanner.nextLine();

                if (create.equalsIgnoreCase("no")) {
                    System.out.println("Enter your name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter your password:");
                    String pass = scanner.nextLine();

                    if (Person.isValid(name, pass)) {
                        Stack<Person> stack = Person.loadStack();
                        boolean isMember = false;
                        for (Person p : stack) {
                            if (p instanceof Member && p.getName().equals(name) && p.getPass().equals(pass)) {
                                isMember = true;
                                break;
                            }
                        }

                        if (isMember) {
                            System.out.println("You are a member.");
                            System.out.println("Do you want to see the library? (yes/no)");
                            String list = scanner.nextLine();
                            if (list.equalsIgnoreCase("yes")) {
                                List<Book> books = library.getBooks();
                                System.out.println("Available books:");
                                for (Book b : books) {
                                    System.out.println(b);
                                }
                                System.out.println("");
                            }

                            while (true) {
                                System.out.println("Do you want to borrow a book? (yes/no)");
                                String borrow = scanner.nextLine();
                                if (borrow.equalsIgnoreCase("yes")) {
                                    System.out.println("Enter book title:");
                                    String title = scanner.nextLine();
                                    if (library.borrowBook(title)) {
                                        System.out.println("Book borrowed successfully.");
                                    } else {
                                        System.out.println("Book is not available or not found.");
                                    }
                                } else if (borrow.equalsIgnoreCase("no")) {
                                    System.out.println("Exiting borrowing menu...");
                                    break;
                                } else {
                                    System.out.println("Invalid choice, please try again.");
                                }
                            }
                        } else {
                            System.out.println("Valid user, but not a member.");
                        }
                    } else {
                        System.out.println("You are not a member.");
                    }
                } else if (create.equalsIgnoreCase("yes")) {
                    System.out.println("Enter your name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter your password:");
                    String pass = scanner.nextLine();

                    Member me = new Member(name, pass);
                    Stack<Person> stack = Person.loadStack();
                    stack.push(me);
                    Person.storeStack(stack);
                    System.out.println("Member created successfully.");
                } else {
                    System.out.println("Invalid choice, please try again.");
                }

            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}