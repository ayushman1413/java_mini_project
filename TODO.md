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

## New Enhancement Tasks

### 1. Enhance Data Models
- [ ] Update Book.java: Add category (String), ISBN (String), copiesAvailable (int, default 1), dueDate (LocalDate)
- [ ] Update User.java: Add role (String, e.g., "Admin" or "User"), studentId (String)
- [ ] Update Library.java: Modify borrow/return to handle copies (decrement/increment copiesAvailable), add methods for stats (totalBooks, issuedBooks, availableBooks, activeUsers), fine calculation (daysLate * 5)

### 2. Add Intermediate Features
- [ ] Implement Due Date Tracking: In Library.borrowBook, set dueDate = borrowDate + 7 days using LocalDate
- [ ] Implement Fine Calculation: In Library.returnBook, calculate fine if returned late (days late * ₹5/day), show in UI
- [ ] Add Search & Filter in ViewBooksPanel: Add JTextField for search (by title/author/category), filter JTable rows dynamically
- [ ] Create Statistics Dashboard: New StatisticsPanel.java with labels for total books, issued books, available books, active users; add button in MainFrame
- [ ] Implement Login System: Create LoginDialog.java at startup, set user role; hide admin buttons (add/delete) for non-admin users
- [ ] Add Book Category System: Update AddBookPanel with JComboBox for categories (Fiction, Science, History, etc.)
- [ ] Add Export Reports: In ViewBooksPanel, add "Export Borrowed" button, export borrowed books list to CSV file

### 3. Add Advanced Features
- [ ] Implement Dark Mode: Add toggle button in MainFrame, switch LookAndFeel between light and dark
- [ ] Generate Borrow Slip (PDF): Add "Generate PDF" button in BorrowReturnPanel, use iText library to create PDF receipt
- [ ] Add Notification System: On app load, check for books due today/tomorrow, show JOptionPane reminders
- [ ] Implement Authentication + Role-Based Access: Integrate with login, disable add/delete for users, enable borrow/return/view

### 4. UI Updates
- [ ] Update AddBookPanel: Add JTextFields for category, ISBN, copies; update layout
- [ ] Update ViewBooksPanel: Add search JTextField, export button, update table columns (add Category, ISBN, Copies, DueDate)
- [ ] Update BorrowReturnPanel: Show due date in borrow confirmation, show fine on return
- [ ] Create new panels: StatisticsPanel.java, LoginDialog.java
- [ ] Update MainFrame: Add stats button, dark mode toggle, integrate LoginDialog before showing main frame

### 5. Data Persistence & Testing
- [ ] Ensure new fields are serializable in Book.java and User.java
- [ ] Update Storage.java if needed for compatibility
- [ ] Install required libraries: Download and add iText jar for PDF generation
- [ ] Test borrow/return with dates, search, export, login
- [ ] Run the app to verify UI and data persistence
