import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Library class manages collection of books and file operations.
 * Demonstrates Collections, I/O Streams, Exception Handling.
 */
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    // Use ArrayList to store Book objects (Collections module)
    private ArrayList<Book> books;
    private transient boolean modified; // transient: not saved
    private transient File currentFile;

    public static final String DEFAULT_EXT = ".lib";

    public Library() {
        books = new ArrayList<>();
        modified = false;
        currentFile = null;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean m) {
        modified = m;
    }

    public void addBook(Book b) {
        books.add(b);
        modified = true;
    }

    public Book searchById(String id) {
        for (Book b : books) {
            if (b.getId().equalsIgnoreCase(id))
                return b;
        }
        return null;
    }

    public boolean deleteById(String id) {
        Book found = searchById(id);
        if (found != null) {
            books.remove(found);
            modified = true;
            return true;
        }
        return false;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public void clear() {
        books.clear();
        modified = true;
    }

    // File operations: save and load using serialization (binary format)
    public void saveToFile(File file) throws IOException {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null.");
        try (FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this); // write entire library object
        }
        currentFile = file;
        modified = false;
    }

    public static Library loadFromFile(File file) throws IOException, ClassNotFoundException {
        if (file == null)
            throw new IllegalArgumentException("File cannot be null.");
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            Library lib = (Library) ois.readObject();
            // restore transient fields
            lib.modified = false;
            lib.currentFile = file;
            return lib;
        }
    }

    // getters/setters for file path
    public File getCurrentFile() {
        return currentFile;
    }
}
