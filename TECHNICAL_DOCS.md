# Technical Documentation - Library Management System

## Table of Contents
- [Code Architecture](#code-architecture)
- [Data Flow](#data-flow)
- [Method Documentation](#method-documentation)
- [Code Analysis](#code-analysis)
- [Error Handling](#error-handling)
- [Performance Considerations](#performance-considerations)
- [Security Analysis](#security-analysis)
- [Testing Guidelines](#testing-guidelines)
- [Refactoring Opportunities](#refactoring-opportunities)

## Code Architecture

### Overview
The Library Management System is implemented as a single Java class (`Library_System`) with a procedural approach using static methods and data structures.

### Design Pattern
- **Pattern**: Procedural Programming with Static Methods
- **Structure**: Single class with static data members and methods
- **Approach**: Console-based user interaction with menu-driven interface

### Class Diagram
```
Library_System
├── Static Fields
│   ├── registeredAcc: Hashtable<String, String>
│   ├── borrowers: TreeMap<String, String>
│   ├── books: Hashtable<String, String>
│   └── keys: Set<String>
└── Static Methods
    ├── register()
    ├── checkAccount(String, String)
    ├── borrowersHandler(String)
    └── main(String[])
```

## Data Flow

### Authentication Flow
```
Start → Account Check → Credentials Input → Validation → Role-based Dashboard
```

### Borrowing Flow
```
Browse Books → Select Book → Check Limits → Update Borrowers → Confirm
```

### Admin Operations Flow
```
Admin Login → Menu Selection → Operation Execution → Data Update → Confirmation
```

## Method Documentation

### Core Methods

#### `register()`
```java
public static void register()
```
**Purpose**: Handles new user registration with username uniqueness validation.

**Algorithm**:
1. Prompt for username and password
2. Check if username already exists in `registeredAcc`
3. If exists, display error and retry
4. If unique, add to `registeredAcc` hashtable

**Input Validation**: Checks username uniqueness against existing keys

**Side Effects**: Modifies `registeredAcc` hashtable

---

#### `checkAccount(String username, String password)`
```java
public static String checkAccount(String username, String password)
```
**Purpose**: Validates user login credentials.

**Parameters**:
- `username`: User's login identifier
- `password`: User's password

**Return Values**:
- `null`: Valid credentials
- `"Username doesn't exist!"`: Username not found
- `"Wrong password!"`: Password mismatch

**Algorithm**:
1. Check if username exists in `registeredAcc`
2. If not found, return username error
3. If found, compare password with stored value
4. Return null for valid, error message for invalid

---

#### `borrowersHandler(String username)`
```java
public static boolean borrowersHandler(String username)
```
**Purpose**: Checks if a user currently has borrowed books.

**Parameters**:
- `username`: User to check for borrowed books

**Return**: `true` if user has borrowed books, `false` otherwise

**Algorithm**:
1. Iterate through `borrowers` keySet
2. Split each key by comma to extract username
3. Compare with target username
4. Return true if match found

**Complexity**: O(n×m) where n = number of borrowers, m = average key components

---

### Data Structure Analysis

#### `registeredAcc: Hashtable<String, String>`
- **Purpose**: Store user credentials
- **Key**: Username (String)
- **Value**: Password (String)
- **Operations**: Put (register), Get (authenticate)
- **Thread Safety**: Yes (Hashtable is synchronized)

#### `borrowers: TreeMap<String, String>`
- **Purpose**: Track borrowed books with user info and dates
- **Key Format**: `"username, date, (borrowCount)"`
- **Value**: Book name
- **Sorting**: Lexicographic order by key
- **Example**: `"john, 2024-01-15, (2)" → "Java Programming"`

#### `books: Hashtable<String, String>`
- **Purpose**: Store available books
- **Key**: Book title
- **Value**: Book link/URL
- **Operations**: Add, Remove, Browse

#### `suggestedBooks: HashSet<String>`
- **Purpose**: Store user book suggestions
- **Benefits**: Automatic duplicate prevention
- **Operations**: Add, Remove, Iterate

## Code Analysis

### Strengths
1. **Functional Requirements**: Meets all specified requirements
2. **User Experience**: Clear menu-driven interface
3. **Data Integrity**: Borrowing limits enforced
4. **Role Separation**: Admin vs Guest access control

### Areas for Improvement

#### 1. Code Organization
- **Current**: Single large main method (300+ lines)
- **Recommendation**: Split into smaller, focused methods
- **Benefit**: Improved readability and maintainability

#### 2. Error Handling
- **Current**: Basic input validation
- **Missing**: Exception handling for edge cases
- **Recommendation**: Add try-catch blocks for robust error handling

#### 3. Data Persistence
- **Current**: In-memory storage only
- **Limitation**: Data lost on program exit
- **Recommendation**: File I/O or database integration

#### 4. Security
- **Current**: Plain text passwords
- **Risk**: Security vulnerability
- **Recommendation**: Password hashing (BCrypt, PBKDF2)

## Error Handling

### Current Error Handling
```java
// Username validation
if (usernameAlrExist == true) {
    System.out.println("Username already taken!");
}

// Book not found
if (books.get(bookName) == null) {
    System.out.println("No book found!");
}
```

### Recommended Improvements
```java
try {
    // Input operations
} catch (InputMismatchException e) {
    System.out.println("Invalid input format");
} catch (Exception e) {
    System.out.println("Unexpected error: " + e.getMessage());
}
```

## Performance Considerations

### Time Complexity Analysis

| Operation | Current Complexity | Optimized Approach |
|-----------|-------------------|-------------------|
| User Authentication | O(1) | O(1) - Already optimal |
| Check Borrowed Books | O(n×m) | O(n) with better key structure |
| Book Search | O(1) | O(1) - Already optimal |
| Add/Remove Books | O(1) | O(1) - Already optimal |

### Memory Usage
- **Small Scale**: Efficient for hundreds of users/books
- **Large Scale**: May require optimization for thousands of records
- **Recommendation**: Consider database for scalability

### Optimization Opportunities
1. **Borrower Tracking**: Use separate data structure for user-book mapping
2. **Search Functionality**: Implement indexed search for large book collections
3. **Caching**: Cache frequently accessed data

## Security Analysis

### Current Security Issues
1. **Password Storage**: Plain text passwords
2. **Admin Hardcoding**: Single hardcoded admin account
3. **No Session Management**: No timeout or session control
4. **Input Validation**: Limited input sanitization

### Security Recommendations
```java
// Password hashing example
import java.security.MessageDigest;

public static String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}
```

## Testing Guidelines

### Unit Test Coverage
```java
@Test
public void testValidLogin() {
    // Setup test user
    Library_System.register("testuser", "testpass");
    
    // Test valid login
    String result = Library_System.checkAccount("testuser", "testpass");
    assertNull(result);
}

@Test
public void testInvalidLogin() {
    String result = Library_System.checkAccount("nonexistent", "password");
    assertEquals("Username doesn't exist!", result);
}
```

### Integration Test Scenarios
1. **Complete User Journey**: Registration → Login → Borrow → Return
2. **Admin Workflow**: Login → Add Books → Manage Borrowers
3. **Edge Cases**: Maximum borrowing, duplicate registrations
4. **Error Scenarios**: Invalid inputs, system limits

### Manual Testing Checklist
- [ ] User registration with duplicate username
- [ ] Login with wrong password
- [ ] Borrowing maximum (2) books
- [ ] Returning books and verifying count update
- [ ] Admin book addition/removal
- [ ] Suggestion system workflow
- [ ] Session logout functionality

## Refactoring Opportunities

### 1. Method Extraction
```java
// Current: Everything in main()
// Proposed: Extract methods
public static void handleAdminDashboard(String username, Scanner input) { }
public static void handleGuestDashboard(String username, Scanner input) { }
public static void displayBookMenu() { }
```

### 2. Constants Definition
```java
public class Constants {
    public static final int MAX_BORROW_LIMIT = 2;
    public static final int BORROW_DURATION_DAYS = 30;
    public static final String ADMIN_USERNAME = "carlo";
    public static final String ADMIN_PASSWORD = "morva";
}
```

### 3. Data Access Layer
```java
public class UserDAO {
    public void saveUser(String username, String password) { }
    public User getUser(String username) { }
    public boolean isUserExists(String username) { }
}

public class BookDAO {
    public void addBook(String title, String link) { }
    public void removeBook(String title) { }
    public List<Book> getAllBooks() { }
}
```

### 4. Business Logic Separation
```java
public class BorrowingService {
    public boolean canUserBorrow(String username) { }
    public void borrowBook(String username, String bookTitle) { }
    public void returnBook(String username, String bookTitle) { }
}
```

## Development Workflow

### Code Style Guidelines
1. **Naming Conventions**: Use camelCase for variables, PascalCase for classes
2. **Method Length**: Keep methods under 50 lines
3. **Comments**: Document complex logic and business rules
4. **Error Messages**: Use consistent, user-friendly messages

### Version Control Best Practices
1. **Commit Messages**: Use descriptive commit messages
2. **Branch Strategy**: Feature branches for new functionality
3. **Code Reviews**: Peer review before merging
4. **Testing**: All tests pass before commit

### Build and Deployment
```bash
# Compile
javac -cp . Library_System.java

# Run
java -cp . Library_System

# Create JAR
jar cfm library-system.jar manifest.txt *.class

# Run JAR
java -jar library-system.jar
```

---

**Last Updated**: [Current Date]
**Version**: 1.0
**Maintainer**: Development Team