import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditUserPanel extends JPanel {
    private Library library;
    private JComboBox<String> userCombo;
    private JTextField nameField, emailField, phoneField, studentIdField;
    private JComboBox<String> roleCombo, membershipCombo;
    private JButton loadBtn, saveBtn, deleteBtn, addBtn;

    public EditUserPanel(Library library) {
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        userCombo = new JComboBox<>();
        loadBtn = new JButton("Load User");
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        roleCombo = new JComboBox<>(new String[]{"Admin", "Librarian", "Student"});
        membershipCombo = new JComboBox<>(new String[]{"Basic", "Premium"});
        studentIdField = new JTextField(20);
        saveBtn = new JButton("Save Changes");
        deleteBtn = new JButton("Delete User");
        addBtn = new JButton("Add New User");

        c.gridx=0; c.gridy=0; add(new JLabel("Select User:"), c);
        c.gridx=1; c.gridy=0; add(userCombo, c);
        c.gridx=2; c.gridy=0; add(loadBtn, c);
        c.gridx=0; c.gridy=1; add(new JLabel("Name:"), c);
        c.gridx=1; c.gridy=1; add(nameField, c);
        c.gridx=0; c.gridy=2; add(new JLabel("Email:"), c);
        c.gridx=1; c.gridy=2; add(emailField, c);
        c.gridx=0; c.gridy=3; add(new JLabel("Phone:"), c);
        c.gridx=1; c.gridy=3; add(phoneField, c);
        c.gridx=0; c.gridy=4; add(new JLabel("Role:"), c);
        c.gridx=1; c.gridy=4; add(roleCombo, c);
        c.gridx=0; c.gridy=5; add(new JLabel("Membership:"), c);
        c.gridx=1; c.gridy=5; add(membershipCombo, c);
        c.gridx=0; c.gridy=6; add(new JLabel("Student ID:"), c);
        c.gridx=1; c.gridy=6; add(studentIdField, c);
        c.gridx=1; c.gridy=7; add(saveBtn, c);
        c.gridx=1; c.gridy=8; add(deleteBtn, c);
        c.gridx=1; c.gridy=9; add(addBtn, c);

        loadUsers();

        loadBtn.addActionListener(e -> loadUser());
        saveBtn.addActionListener(e -> saveUser());
        deleteBtn.addActionListener(e -> deleteUser());
        addBtn.addActionListener(e -> addUser());
    }

    private void loadUsers() {
        userCombo.removeAllItems();
        for (User u : library.listAllUsers()) {
            userCombo.addItem(u.id + " - " + u.name);
        }
    }

    private void loadUser() {
        if (userCombo.getItemCount() == 0) return;
        String item = (String) userCombo.getSelectedItem();
        int id = Integer.parseInt(item.split(" - ")[0]);
        User u = library.users.get(id);
        if (u != null) {
            nameField.setText(u.name);
            emailField.setText(u.email);
            phoneField.setText(u.phone);
            roleCombo.setSelectedItem(u.role);
            membershipCombo.setSelectedItem(u.membershipType);
            studentIdField.setText(u.studentId);
        }
    }

    private void saveUser() {
        if (userCombo.getItemCount() == 0) return;
        String item = (String) userCombo.getSelectedItem();
        int id = Integer.parseInt(item.split(" - ")[0]);
        User u = library.users.get(id);
        if (u != null) {
            u.name = nameField.getText().trim();
            u.email = emailField.getText().trim();
            u.phone = phoneField.getText().trim();
            u.role = (String) roleCombo.getSelectedItem();
            u.membershipType = (String) membershipCombo.getSelectedItem();
            u.studentId = studentIdField.getText().trim();
            JOptionPane.showMessageDialog(this, "User updated.");
            loadUsers();
        }
    }

    private void deleteUser() {
        if (userCombo.getItemCount() == 0) return;
        String item = (String) userCombo.getSelectedItem();
        int id = Integer.parseInt(item.split(" - ")[0]);
        library.users.remove(id);
        JOptionPane.showMessageDialog(this, "User deleted.");
        loadUsers();
        clearFields();
    }

    private void addUser() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String role = (String) roleCombo.getSelectedItem();
        String membership = (String) membershipCombo.getSelectedItem();
        String studentId = studentIdField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }
        User u = library.addUser(name, email, phone, role, studentId);
        u.membershipType = membership;
        JOptionPane.showMessageDialog(this, "User added: " + u);
        loadUsers();
        clearFields();
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        studentIdField.setText("");
    }
}
