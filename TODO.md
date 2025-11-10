# Library Management System Enhancement TODO

## Completed Basic Features
- [x] Create Book.java
- [x] Create User.java
- [x] Create Library.java
- [x] Create Storage.java
- [x] Create AddBookPanel.java
- [x] Create ViewBooksPanel.java
- [x] Create BorrowReturnPanel.java
- [x] Create MainFrame.java
- [x] Create App.java

## Extra Unique & Smart Features Implementation

### 1. Student Membership System
- [ ] Update User.java: Add membershipId (auto-increment), membershipType ("Basic"/"Premium"), totalBooksBorrowed, fineHistory (list of maps), securityQuestion/Answer
- [ ] Add Membership.java: Static counter for membership ID generation
- [ ] Update Library.java: Add membership logic, borrowing limits based on type (Basic: 3 books, Premium: 5), track total borrowed per member

### 2. Book Stock Management
- [ ] Update ViewBooksPanel.java: Show copies available, alert if stock < 2 (“Low Stock Warning”)
- [ ] Update Library.java: Auto-update copies on borrow/return

### 3. Auto Reminder System
- [ ] Update MainFrame.java: Check due dates on startup, show JOptionPane for close/overdue
- [ ] Add EmailUtil.java: Integrate JavaMail for email reminders (advanced)

### 4. Book Reservation System
- [ ] Add Reservation.java: userId, bookId, reserveDate
- [ ] Update Library.java: Add reservation logic, notify when returned
- [ ] Add ReservationPanel.java: UI for reserving books

### 5. Activity Log / History
- [ ] Add ActivityLog.java: timestamp, action, details
- [ ] Update Library.java: Log every borrow/return action
- [ ] Add ActivityLogPanel.java: JTable to show logs

### 6. Generate Reports
- [ ] Add ReportPanel.java: Export issued/overdue books, members to PDF/CSV using iText

### 7. Smart Recommendation
- [ ] Update Book.java: Add similarBooks list
- [ ] Update Library.java: Recommend similar books on borrow

### 8. Dark Mode / Theme Switcher
- [ ] Add ThemeManager.java: Persist theme preference in file
- [ ] Update MainFrame.java: Toggle button with persistent dark/light mode

### 9. Login & Authentication
- [ ] Update LoginDialog.java: Add forgot password with security question
- [ ] Role-based: Admin full, Librarian limited, Student view/borrow only

### 10. Online Book Information Fetch
- [ ] Add ApiUtil.java: Fetch details from Google Books API by ISBN
- [ ] Update AddBookPanel.java: Auto-fetch on ISBN input

### 11. Dashboard / Home Screen
- [ ] Add DashboardPanel.java: Summary cards, charts with JFreeChart

### 12. Book Cover Display
- [ ] Update Book.java: Add coverImagePath
- [ ] Update AddBookPanel.java: Upload image
- [ ] Update ViewBooksPanel.java: Show small cover preview

### 13. Fine Payment Record
- [ ] Update User.java: Fine history with paid/unpaid status
- [ ] Update BorrowReturnPanel.java: Show fine on return, mark paid

### 14. Forgot Password System
- [ ] Update LoginDialog.java: Security question reset

### 15. Notification Panel
- [ ] Add NotificationPanel.java: Sidebar with due/overdue alerts

### 16. Event Calendar
- [ ] Add CalendarPanel.java: Highlight due dates

### 17. Multi-language Support
- [ ] Add LanguageManager.java: ResourceBundles for English/Hindi

### 18. Import/Export Data
- [ ] Update Library.java: Import/export books/users to CSV

### 19. Auto Backup
- [ ] Update MainFrame.java: Timer for auto-save every few minutes

### 20. About / Help Section
- [ ] Add AboutPanel.java: Developer info

### Extra Add-ons
- [ ] Splash Screen: Loading animation on start
- [ ] Animated transitions: Between panels
- [ ] Book QR Code: Generate with ZXing
- [ ] Sound Effects: On issue/return
- [ ] Cloud Sync: Placeholder for Firebase

### Dependencies & Setup
- [ ] Install libraries: iText (PDF), JFreeChart (charts), JavaMail (email), ZXing (QR), download jars and add to classpath

### Testing
- [ ] Test all features: Membership limits, stock alerts, reservations, logs, reports, themes, notifications, etc.
- [ ] Run app to verify integrations
