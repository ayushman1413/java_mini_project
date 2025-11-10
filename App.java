import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Load saved data
        Library library = Storage.load("library_data.ser");

        // If no users, add a default admin
        if (library.listAllUsers().isEmpty()) {
            library.addUser("Admin", "admin@example.com", "1234567890", "Admin", "ADM001");
        }

        // Show login dialog
        LoginDialog loginDialog = new LoginDialog(null, library);
        loginDialog.setVisible(true);
        User loggedInUser = loginDialog.getLoggedInUser();
        if (loggedInUser == null) {
            System.exit(0); // Exit if no login
        }

        // Run UI on EDT
        SwingUtilities.invokeLater(() -> {
            new MainFrame(library, loggedInUser);
        });
    }
}
