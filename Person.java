import java.io.*;
import java.util.*;

abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String pass;

    public Person(String nam, String pas) {
        this.name = nam;
        this.pass = pas;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public static void storeStack(Stack<Person> stack) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PersonIn"))) {
            oos.writeObject(stack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Stack<Person> loadStack() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PersonIn"))) {
            return (Stack<Person>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Stack<>();
        }
    }

    public static boolean isValid(String user, String pin) {
        Stack<Person> stack = loadStack();
        for (Person p : stack) {
            if (p.getName().equals(user) && p.getPass().equals(pin)) {
                return true;
            }
        }
        return false;
    }
}
