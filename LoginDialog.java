import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginDialog extends JDialog {
    private Library library;
    private User loggedInUser;

    public LoginDialog(Frame parent, Library library) {
        super(parent, LanguageManager.get("login.title"), true);
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);

        JLabel userLabel = new JLabel(LanguageManager.get("login.select"));
        JComboBox<String> userCombo = new JComboBox<>();
        for (User u : library.listAllUsers()) {
            userCombo.addItem(u.id + " - " + u.name + " (" + u.role + ")");
        }
        JButton loginBtn = new JButton(LanguageManager.get("login.button"));
        JButton forgotBtn = new JButton("Forgot Password");

        c.gridx = 0; c.gridy = 0; add(userLabel, c);
        c.gridx = 1; c.gridy = 0; add(userCombo, c);
        c.gridx = 1; c.gridy = 1; add(loginBtn, c);
        c.gridx = 1; c.gridy = 2; add(forgotBtn, c);

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

        forgotBtn.addActionListener(e -> {
            if (userCombo.getItemCount() == 0) return;
            String item = (String) userCombo.getSelectedItem();
            int userId = Integer.parseInt(item.split(" - ")[0].trim());
            User u = library.users.get(userId);
            if (u != null && u.securityQuestion != null) {
                String answer = JOptionPane.showInputDialog(this, u.securityQuestion);
                if (answer != null && answer.trim().equals(u.securityAnswer)) {
                    JOptionPane.showMessageDialog(this, "Your password is remembered. Login as usual.");
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong answer.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No security question set.");
            }
        });

        setSize(300,200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
