import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        // Splash screen
        JWindow splash = new JWindow();
        JLabel splashLabel = new JLabel("Loading Library Management System...", SwingConstants.CENTER);
        splashLabel.setFont(new Font("Arial", Font.BOLD, 16));
        splash.getContentPane().add(splashLabel);
        splash.setSize(400, 200);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        try {
            Thread.sleep(2000); // Simulate loading
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splash.setVisible(false);

        // Load saved data
        Library library = Storage.load("library_data.ser");

        // If no users, add a default admin
        if (library.listAllUsers().isEmpty()) {
            User admin = library.addUser("Admin", "admin@example.com", "1234567890", "Admin", "ADM001");
            admin.securityQuestion = "What is your favorite color?";
            admin.securityAnswer = "blue";
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
