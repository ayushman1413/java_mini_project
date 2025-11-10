import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {
    private Library library;
    private User loggedInUser;
    private ViewBooksPanel viewBooksPanel;
    private AddBookPanel addBookPanel;
    private BorrowReturnPanel borrowReturnPanel;
    private StatisticsPanel statisticsPanel;
    private ReservationPanel reservationPanel;
    private ActivityLogPanel activityLogPanel;
    private ReportPanel reportPanel;
    private DashboardPanel dashboardPanel;
    private NotificationPanel notificationPanel;
    private CalendarPanel calendarPanel;
    private AboutPanel aboutPanel;
    private JPanel cards;
    private Timer autoBackupTimer;

    public MainFrame(Library library, User loggedInUser) {
        this.library = library;
        this.loggedInUser = loggedInUser;
        setTitle("Library Management System - Logged in as: " + loggedInUser.name + " (" + loggedInUser.role + ")");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load theme
        ThemeManager.loadTheme();
        ThemeManager.applyTheme(this);

        // Top menu
        JPanel top = new JPanel();
        JButton btnView = new JButton(LanguageManager.get("menu.view"));
        JButton btnAdd = new JButton(LanguageManager.get("menu.add"));
        JButton btnBorrow = new JButton(LanguageManager.get("menu.borrow"));
        JButton btnStats = new JButton(LanguageManager.get("menu.stats"));
        JButton btnReserve = new JButton("Reservations");
        JButton btnLogs = new JButton("Activity Logs");
        JButton btnReports = new JButton("Reports");
        JButton btnDashboard = new JButton("Dashboard");
        JButton btnNotifications = new JButton("Notifications");
        JButton btnCalendar = new JButton("Calendar");
        JButton btnAbout = new JButton("About");
        JButton btnAddUser = new JButton(LanguageManager.get("menu.adduser"));
        JButton btnDarkMode = new JButton(LanguageManager.get("menu.dark"));
        JButton btnLang = new JButton(LanguageManager.get("menu.lang"));
        top.add(btnView); top.add(btnAdd); top.add(btnBorrow); top.add(btnStats); top.add(btnReserve); top.add(btnLogs); top.add(btnReports); top.add(btnDashboard); top.add(btnNotifications); top.add(btnCalendar); top.add(btnAbout); top.add(btnAddUser); top.add(btnDarkMode); top.add(btnLang);
        add(top, BorderLayout.NORTH);

        // Cards area
        cards = new JPanel(new CardLayout());
        viewBooksPanel = new ViewBooksPanel(library);
        addBookPanel = new AddBookPanel(library);
        borrowReturnPanel = new BorrowReturnPanel(library);
        statisticsPanel = new StatisticsPanel(library);
        reservationPanel = new ReservationPanel(library);
        activityLogPanel = new ActivityLogPanel(library);
        reportPanel = new ReportPanel(library);
        dashboardPanel = new DashboardPanel(library);
        notificationPanel = new NotificationPanel(library);
        calendarPanel = new CalendarPanel(library);
        aboutPanel = new AboutPanel();

        cards.add(viewBooksPanel, "VIEW");
        cards.add(addBookPanel, "ADD");
        cards.add(borrowReturnPanel, "BORROW");
        cards.add(statisticsPanel, "STATS");
        cards.add(reservationPanel, "RESERVE");
        cards.add(activityLogPanel, "LOGS");
        cards.add(reportPanel, "REPORTS");
        cards.add(dashboardPanel, "DASHBOARD");
        cards.add(notificationPanel, "NOTIFICATIONS");
        cards.add(calendarPanel, "CALENDAR");
        cards.add(aboutPanel, "ABOUT");
        add(cards, BorderLayout.CENTER);

        // Role-based access
        if (!"Admin".equals(loggedInUser.role) && !"Librarian".equals(loggedInUser.role)) {
            btnAdd.setEnabled(false);
            btnAddUser.setEnabled(false);
            btnReserve.setEnabled(false);
            btnLogs.setEnabled(false);
            btnReports.setEnabled(false);
        }
        if ("Student".equals(loggedInUser.role)) {
            btnBorrow.setEnabled(false); // Students can only view/borrow, but borrow is separate
        }

        // Add user quick dialog
        btnAddUser.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "User name:");
            if (name == null || name.trim().isEmpty()) return;
            String email = JOptionPane.showInputDialog(this, "Email (optional):");
            String phone = JOptionPane.showInputDialog(this, "Phone (optional):");
            String role = JOptionPane.showInputDialog(this, "Role (Admin/Librarian/Student):");
            if (role == null || (!role.equals("Admin") && !role.equals("Librarian") && !role.equals("Student"))) role = "Student";
            String studentId = JOptionPane.showInputDialog(this, "Student ID (optional):");
            library.addUser(name.trim(), email==null? "": email.trim(), phone==null? "": phone.trim(), role, studentId==null? "": studentId.trim());
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
        btnStats.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "STATS");
            statisticsPanel.refreshStats();
        });
        btnReserve.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "RESERVE");
        });
        btnLogs.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "LOGS");
            activityLogPanel.loadLogs();
        });
        btnReports.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "REPORTS");
        });
        btnDashboard.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "DASHBOARD");
        });
        btnNotifications.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "NOTIFICATIONS");
            notificationPanel.loadNotifications();
        });
        btnCalendar.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "CALENDAR");
            calendarPanel.loadCalendar();
        });
        btnAbout.addActionListener(e -> {
            ((CardLayout)cards.getLayout()).show(cards, "ABOUT");
        });
        btnDarkMode.addActionListener(e -> toggleDarkMode());
        btnLang.addActionListener(e -> toggleLanguage());

        // Notification for due dates
        checkDueDates();

        // Auto backup timer
        autoBackupTimer = new Timer();
        autoBackupTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Storage.save(library, "library_data.ser");
            }
        }, 5*60*1000, 5*60*1000); // every 5 minutes

        // On close save
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                autoBackupTimer.cancel();
                Storage.save(library, "library_data.ser");
                super.windowClosing(e);
            }
        });

        setVisible(true);
    }

    private void toggleDarkMode() {
        boolean isDark = ThemeManager.isDarkMode();
        ThemeManager.saveTheme(!isDark);
        ThemeManager.applyTheme(this);
        JOptionPane.showMessageDialog(this, "Theme toggled.");
    }

    private void toggleLanguage() {
        LanguageManager.toggleLanguage();
        JOptionPane.showMessageDialog(this, "Language toggled. Restart app for full effect.");
    }

    private void checkDueDates() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        List<Book> books = library.listAllBooks();
        for (Book b : books) {
            if (b.dueDate != null && (b.dueDate.equals(today) || b.dueDate.equals(tomorrow))) {
                JOptionPane.showMessageDialog(this, "Book '" + b.title + "' is due " +
                    (b.dueDate.equals(today) ? "today" : "tomorrow") + "!");
            }
        }
    }
}
