/**
 * EBook demonstrates use of subclass and overriding (polymorphism).
 */
public class EBook extends Book {
    private static final long serialVersionUID = 1L;
    private String fileSize; // e.g., "2MB"

    public EBook(String id, String title, String author, int copies, String fileSize) {
        super(id, title, author, copies);
        this.fileSize = fileSize;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public void displayInfo() {
        System.out.printf("ID: %s | EBook: %s | Author: %s | Copies: %d | Size: %s%n",
                getId(), getTitle(), getAuthor(), getCopies(), fileSize);
    }
}
