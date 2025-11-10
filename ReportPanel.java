import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;

public class ReportPanel extends JPanel {
    private Library library;

    public ReportPanel(Library library) {
        this.library = library;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);

        JButton overdueBtn = new JButton("Export Overdue Books PDF");
        JButton issuedBtn = new JButton("Export Issued Books PDF");
        JButton membersBtn = new JButton("Export Members PDF");

        c.gridx=0; c.gridy=0; add(overdueBtn, c);
        c.gridx=0; c.gridy=1; add(issuedBtn, c);
        c.gridx=0; c.gridy=2; add(membersBtn, c);

        overdueBtn.addActionListener(e -> exportOverduePDF());
        issuedBtn.addActionListener(e -> exportIssuedPDF());
        membersBtn.addActionListener(e -> exportMembersPDF());
    }

    private void exportOverduePDF() {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("overdue_books.pdf"));
            doc.open();
            doc.add(new Paragraph("Overdue Books Report"));
            List<Book> overdue = library.getOverdueBooks();
            for (Book b : overdue) {
                doc.add(new Paragraph(b.title + " - Due: " + b.dueDate + " - User: " + b.borrowedByUserId));
            }
            doc.close();
            JOptionPane.showMessageDialog(this, "Exported to overdue_books.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export failed: " + e.getMessage());
        }
    }

    private void exportIssuedPDF() {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("issued_books.pdf"));
            doc.open();
            doc.add(new Paragraph("Issued Books Report"));
            List<Book> issued = library.listAllBooks().stream().filter(b -> b.isBorrowed).toList();
            for (Book b : issued) {
                doc.add(new Paragraph(b.title + " - User: " + b.borrowedByUserId + " - Due: " + b.dueDate));
            }
            doc.close();
            JOptionPane.showMessageDialog(this, "Exported to issued_books.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export failed: " + e.getMessage());
        }
    }

    private void exportMembersPDF() {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("members.pdf"));
            doc.open();
            doc.add(new Paragraph("Members Report"));
            List<User> users = library.listAllUsers();
            for (User u : users) {
                doc.add(new Paragraph(u.name + " - " + u.role + " - Membership: " + u.membershipType));
            }
            doc.close();
            JOptionPane.showMessageDialog(this, "Exported to members.pdf");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Export failed: " + e.getMessage());
        }
    }
}
