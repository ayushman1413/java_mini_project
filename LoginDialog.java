import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginDialog extends JDialog {
    private Library library;
    private User loggedInUser;

    public LoginDialog(Frame parent, Library library) {
        super(parent, "Login", true);
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);

        JLabel userLabel = new JLabel("Select User:");
        JComboBox<String> userCombo = new JComboBox<>();
        for (User u : library.listAllUsers()) {
            userCombo.addItem(u.id + " - " + u.name + " (" + u.role + ")");
        }
        JButton loginBtn = new JButton("Login");

        c.gridx = 0; c.gridy = 0; add(userLabel, c);
        c.gridx = 1; c.gridy = 0; add(userCombo, c);
        c.gridx = 1; c.gridy = 1; add(loginBtn, c);

        loginBtn.addActionListener(e -> {
            if (userCombo.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No users available. Add a user first.");
                return;
            }
            String item = (String) userCombo.getSelectedItem();
            int userId = Integer.parseInt(item.split(" - ")[0].trim());
            loggedInUser = library.users.get(userId);
            setVisible(false);
        });

        setSize(300,150);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
