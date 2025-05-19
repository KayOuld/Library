class Librarian extends Person {
    private static final long serialVersionUID = 1L;

    public Librarian() {
        super("Kayden", "12345");
    }

    @Override
    public String toString() {
        return "Librarian: " + getName() + ", Pass: " + getPass();
    }
}