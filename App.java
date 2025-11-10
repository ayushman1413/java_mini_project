import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Load saved data
        Library library = Storage.load("library_data.ser");

        // Run UI on EDT
        SwingUtilities.invokeLater(() -> {
            new MainFrame(library);
        });
    }
}
