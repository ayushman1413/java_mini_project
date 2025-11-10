import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String name;
    public String email;
    public String phone;
    public String role; // "Admin", "Librarian", "Student"
    public String studentId;
    public int membershipId;
    public String membershipType; // "Basic" or "Premium"
    public int totalBooksBorrowed;
    public List<Map<String, Object>> fineHistory; // list of maps: {date, amount, paid}
    public String securityQuestion;
    public String securityAnswer;

    public User(int id, String name, String email, String phone, String role, String studentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.studentId = studentId;
        this.membershipId = Membership.generateMembershipId();
        this.membershipType = "Basic"; // default
        this.totalBooksBorrowed = 0;
        this.fineHistory = new ArrayList<>();
        this.securityQuestion = null;
        this.securityAnswer = null;
    }

    public int getBorrowingLimit() {
        return "Premium".equals(membershipType) ? 5 : 3;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - Membership: %s (%d)", id, name, role, membershipType, membershipId);
    }
}
