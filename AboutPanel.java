import javax.swing.*;
import java.awt.*;
public class AboutPanel extends JPanel {
    public AboutPanel() {
        setLayout(new BorderLayout());
        JTextArea aboutText = new JTextArea();
        aboutText.setEditable(false);
        aboutText.setText("Library Management System\n\n" +
                "Developed by: Ayushman\n" +
                "Course: Computer Science\n" +
                "College: XYZ University\n\n" +
                "Features:\n" +
                "- Book Management\n" +
                "- User Membership\n" +
                "- Borrow/Return with Limits\n" +
                "- Reservations\n" +
                "- Activity Logs\n" +
                "- Reports (PDF)\n" +
                "- Dark Mode\n" +
                "- Multi-language Support\n" +
                "- And more!");
        add(new JScrollPane(aboutText), BorderLayout.CENTER);
    }
}
