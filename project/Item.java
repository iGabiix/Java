import java.io.Serializable;

/**
 * Abstract base class for library items.
 * Demonstrates Abstraction + Inheritance.
 */
public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String title;
    protected String author;
    protected int copies;

    public Item(String id, String title, String author, int copies) {
        // 'this' keyword used to reference current object's fields
        this.id = id;
        this.title = title;
        this.author = author;
        this.copies = copies;
    }

    // Encapsulation: private/protected fields + getters/setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCopies() {
        return copies;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    // Abstract method to be implemented by subclasses (Polymorphism)
    public abstract void displayInfo();
}
