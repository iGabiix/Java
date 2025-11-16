/**
 * Concrete Book class.
 * Inherits from Item.
 */
public class Book extends Item {
    private static final long serialVersionUID = 1L;

    // Example of a static field (shared by class)
    public static final String TYPE = "BOOK";

    public Book(String id, String title, String author, int copies) {
        // 'super' calls the parent constructor
        super(id, title, author, copies);
    }

    @Override
    public void displayInfo() {
        System.out.printf("ID: %s | Title: %s | Author: %s | Copies: %d%n",
                id, title, author, copies);
    }
}
