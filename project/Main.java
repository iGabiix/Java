import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Main class: user interface (console). Implements menus required by template.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            try {
                showMainMenu();
                int choice = readInt("Enter choice: ");
                switch (choice) {
                    case 1 -> createNewFile();
                    case 2 -> openFile();
                    case 3 -> saveFile();
                    case 4 -> saveAsFile();
                    case 5 -> bookRecordMenu();
                    case 6 -> {
                        exitProgram();
                        running = false;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        scanner.close();
        System.out.println("Program terminated.");
    }

    private static void showMainMenu() {
        System.out.println("\n============================");
        System.out.println(" Library Management System");
        System.out.println("============================");
        System.out.println("1. NEW");
        System.out.println("2. OPEN");
        System.out.println("3. SAVE");
        System.out.println("4. SAVE AS");
        System.out.println("5. BOOK RECORD");
        System.out.println("6. EXIT");
    }

    // NEW - create new library in memory (optionally save later)
    private static void createNewFile() {
        if (library.isModified()) {
            boolean save = readYesNo("Current library has unsaved changes. Save now? (y/n): ");
            if (save)
                saveFile();
        }
        library = new Library();
        System.out.println("New library created in memory. Use SAVE or SAVE AS to persist.");
    }

    // OPEN - load from file
    private static void openFile() {
        String path = readString("Enter path of library file to open: ");
        File f = new File(path);
        if (!f.exists()) {
            System.out.println("File not found: " + path);
            boolean retry = readYesNo("Do you want to try another path? (y/n): ");
            if (retry)
                openFile();
            return;
        }
        try {
            library = Library.loadFromFile(f);
            System.out.println("Library loaded successfully from: " + f.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println("Failed to open file: " + ex.getMessage());
        }
    }

    // SAVE - save to current file, or ask for path if none
    private static void saveFile() {
        File f = library.getCurrentFile();
        if (f == null) {
            System.out.println("No file associated. Use SAVE AS to choose a file name.");
            saveAsFile();
            return;
        }
        try {
            library.saveToFile(f);
            System.out.println("Library saved to: " + f.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println("Failed to save: " + ex.getMessage());
        }
    }

    // SAVE AS - choose new file path
    private static void saveAsFile() {
        String path = readString("Enter path to save library (will append " + Library.DEFAULT_EXT + " if missing): ");
        if (!path.endsWith(Library.DEFAULT_EXT))
            path += Library.DEFAULT_EXT;
        File f = new File(path);
        if (f.exists()) {
            boolean overwrite = readYesNo("File exists. Overwrite? (y/n): ");
            if (!overwrite) {
                System.out.println("Save As cancelled.");
                return;
            }
        }
        try {
            library.saveToFile(f);
            System.out.println("Library saved to: " + f.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println("Failed to save: " + ex.getMessage());
        }
    }

    // BOOK RECORD menu (ADD, SEARCH, DELETE, SHOW, BACK)
    private static void bookRecordMenu() {
        boolean back = false;
        while (!back) {
            showBookMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addBook();
                case 2 -> searchBook();
                case 3 -> deleteBook();
                case 4 -> showBookList();
                case 5 -> back = true;
                default -> System.out.println("Invalid choice. Please select 1-5.");
            }
        }
    }

    private static void showBookMenu() {
        System.out.println("\n------ BOOK RECORD ------");
        System.out.println("1. ADD BOOK");
        System.out.println("2. SEARCH BOOK");
        System.out.println("3. DELETE BOOK");
        System.out.println("4. SHOW BOOK LIST");
        System.out.println("5. BACK TO MENU");
    }

    private static void addBook() {
        String id = readString("Enter Book ID: ");
        if (library.searchById(id) != null) {
            System.out.println("A book with this ID already exists.");
            return;
        }
        String title = readString("Enter Title: ");
        String author = readString("Enter Author: ");
        int copies = readInt("Enter number of copies: ");
        // Simple version uses Book class; to show polymorphism you can choose to create
        // an EBook sometimes
        Book b;
        boolean ebook = readYesNo("Is this an EBook? (y/n): ");
        if (ebook) {
            String size = readString("Enter file size (e.g., 2MB): ");
            b = new EBook(id, title, author, copies, size);
        } else {
            b = new Book(id, title, author, copies);
        }
        library.addBook(b);
        System.out.println("Book added successfully.");
        boolean more = readYesNo("Add another book? (y/n): ");
        if (more)
            addBook();
    }

    private static void searchBook() {
        String id = readString("Enter Book ID to search: ");
        Book b = library.searchById(id);
        if (b != null) {
            System.out.println("Book found:");
            b.displayInfo(); // polymorphic call
        } else {
            System.out.println("Book not found with ID: " + id);
        }
        boolean again = readYesNo("Search another? (y/n): ");
        if (again)
            searchBook();
    }

    private static void deleteBook() {
        String id = readString("Enter Book ID to delete: ");
        Book b = library.searchById(id);
        if (b != null) {
            System.out.println("Found book:");
            b.displayInfo();
            boolean confirm = readYesNo("Delete this book? (y/n): ");
            if (confirm) {
                boolean ok = library.deleteById(id);
                if (ok)
                    System.out.println("Book deleted.");
                else
                    System.out.println("Failed to delete.");
            } else
                System.out.println("Delete cancelled.");
        } else {
            System.out.println("No book with ID: " + id);
        }
        boolean again = readYesNo("Delete another? (y/n): ");
        if (again)
            deleteBook();
    }

    private static void showBookList() {
        List<Book> all = library.getAllBooks();
        if (all.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }
        System.out.println("\nID | Title | Author | Copies");
        System.out.println("-------------------------------------------");
        for (Book b : all) {
            b.displayInfo();
        }
    }

    private static void exitProgram() {
        if (library.isModified()) {
            boolean save = readYesNo("You have unsaved changes. Save before exit? (y/n): ");
            if (save)
                saveFile();
        }
        System.out.println("Exiting application...");
    }

    // ---------- Utility input methods with exception handling ----------
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("y") || r.equals("yes"))
                return true;
            if (r.equals("n") || r.equals("no"))
                return false;
            System.out.println("Please answer y/n.");
        }
    }
}
