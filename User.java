import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String name;
    public String email;
    public String phone;
    public String role; // "Admin" or "User"
    public String studentId;

    public User(int id, String name, String email, String phone, String role, String studentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s)", id, name, role);
    }
}
