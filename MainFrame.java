import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private Library library;
    private ViewBooksPanel viewBooksPanel;
    private AddBookPanel addBookPanel;
    private BorrowReturnPanel borrowReturnPanel;
    private JPanel cards;

    public MainFrame(Library library) {
        this.library = library;
        setTitle("Library Management System");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top menu
        JPanel top = new JPanel();
        JButton btnView = new JButton("View Books");
        JButton btnAdd = new JButton("Add Book");
        JButton btnBorrow = new JButton("Borrow/Return");
        JButton btnAddUser = new JButton("Add User");
        top.add(btnView); top.add(btnAdd); top.add(btnBorrow); top.add(btnAddUser);
        add(top, BorderLayout.NORTH);

        // Cards area
        cards = new JPanel(new CardLayout());
        viewBooksPanel = new ViewBooksPanel(library);
        addBookPanel = new AddBookPanel(library);
        borrowReturnPanel = new BorrowReturnPanel(library);

        cards.add(viewBooksPanel, "VIEW");
        cards.add(addBookPanel, "ADD");
        cards.add(borrowReturnPanel, "BORROW");
        add(cards, BorderLayout.CENTER);

        // Add user quick dialog
        btnAddUser.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "User name:");
            if (name == null || name.trim().isEmpty()) return;
            String email = JOptionPane.showInputDialog(this, "Email (optional):");
            String phone = JOptionPane.showInputDialog(this, "Phone (optional):");
            library.addUser(name.trim(), email==null? "": email.trim(), phone==null? "": phone.trim());
            JOptionPane.showMessageDialog(this, "User added.");
        });

        btnView.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "VIEW");
            viewBooksPanel.loadData();
        });
        btnAdd.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "ADD");
        });
        btnBorrow.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "BORROW");
        });

        // On close save
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Storage.save(library, "library_data.ser");
                super.windowClosing(e);
            }
        });

        setVisible(true);
    }
}
