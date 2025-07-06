# API Reference - Library Management System

## Overview
This document provides a quick reference for all methods, data structures, and usage patterns in the Library Management System.

## Table of Contents
- [Core Methods](#core-methods)
- [Data Structures](#data-structures)
- [Usage Examples](#usage-examples)
- [Constants and Configuration](#constants-and-configuration)
- [Error Codes](#error-codes)
- [State Management](#state-management)

## Core Methods

### Authentication Methods

#### `register()`
```java
public static void register()
```
**Description**: Handles user registration with interactive input.

**Behavior**:
- Prompts for username and password via console
- Validates username uniqueness
- Loops until valid unique username is provided
- Stores credentials in `registeredAcc`

**Side Effects**: Modifies global `registeredAcc` hashtable

**Example Flow**:
```
Input: username = "newuser", password = "mypass"
Result: registeredAcc.put("newuser", "mypass")
```

---

#### `checkAccount(String username, String password)`
```java
public static String checkAccount(String username, String password)
```
**Description**: Validates user login credentials.

**Parameters**:
| Parameter | Type | Description |
|-----------|------|-------------|
| `username` | String | User's login identifier |
| `password` | String | User's password |

**Returns**:
| Return Value | Description |
|--------------|-------------|
| `null` | Valid credentials |
| `"Username doesn't exist!"` | Username not found |
| `"Wrong password!"` | Invalid password |

**Example**:
```java
// Valid login
String result = checkAccount("carlo", "morva");
// result = null

// Invalid username
String result = checkAccount("nonexistent", "password");
// result = "Username doesn't exist!"

// Wrong password
String result = checkAccount("carlo", "wrongpass");
// result = "Wrong password!"
```

---

### Borrowing Management

#### `borrowersHandler(String username)`
```java
public static boolean borrowersHandler(String username)
```
**Description**: Checks if a user has any borrowed books.

**Parameters**:
| Parameter | Type | Description |
|-----------|------|-------------|
| `username` | String | Username to check |

**Returns**:
| Return Value | Description |
|--------------|-------------|
| `true` | User has borrowed books |
| `false` | User has no borrowed books |

**Algorithm**:
1. Iterates through `borrowers` TreeMap keys
2. Splits each key by comma: `"username, date, (count)"`
3. Checks if any key contains the target username

**Example**:
```java
// Assuming borrowers contains: "john, 2024-01-15, (1)" → "Java Book"
boolean hasBorrowed = borrowersHandler("john");
// hasBorrowed = true

boolean hasBorrowed = borrowersHandler("mary");
// hasBorrowed = false (if mary has no borrowed books)
```

## Data Structures

### `registeredAcc: Hashtable<String, String>`

**Purpose**: Store user account credentials

**Structure**:
```
Key (Username) → Value (Password)
"carlo" → "morva"
"john" → "password123"
```

**Operations**:
```java
// Add user
registeredAcc.put(username, password);

// Check user exists
if (registeredAcc.get(username) != null) { }

// Validate password
if (password.equals(registeredAcc.get(username))) { }
```

---

### `borrowers: TreeMap<String, String>`

**Purpose**: Track borrowed books with timestamps and borrowing counts

**Key Format**: `"username, date, (borrowCount)"`
**Value**: Book title

**Structure**:
```
Key → Value
"john, 2024-01-15, (1)" → "Java Programming"
"john, 2024-01-15, (2)" → "Web Development"
"mary, 2024-01-16, (1)" → "Data Structures"
```

**Key Components**:
1. **Username**: Borrower's username
2. **Date**: Borrowing date (LocalDate format)
3. **Count**: Sequential borrowing number (1 or 2)

**Operations**:
```java
// Add borrowed book
borrowers.put(username + ", " + date + ", " + "(" + borrowCount + ")", bookName);

// Find user's borrowed books
for (String key : borrowers.keySet()) {
    if (key.contains(username)) {
        String borrowedBook = borrowers.get(key);
    }
}

// Remove returned book
borrowers.remove(key);
```

---

### `books: Hashtable<String, String>`

**Purpose**: Store available books with their access links

**Structure**:
```
Key (Book Title) → Value (Book Link)
"Java" → "https://pdfdrive.com/Java"
"JavaScript" → "https://pdfdrive.com/JavaScript"
```

**Operations**:
```java
// Add book
books.put(bookTitle, bookLink);

// Remove book
books.remove(bookTitle);

// Check book exists
if (books.get(bookTitle) != null) { }

// Get book link
String link = books.get(bookTitle);

// List all books
for (String title : books.keySet()) {
    System.out.println(title + " - " + books.get(title));
}
```

---

### `suggestedBooks: HashSet<String>`

**Purpose**: Store unique book suggestions from users

**Structure**:
```
Set of book titles (duplicates automatically removed)
{"Advanced Java", "Python Basics", "Database Design"}
```

**Operations**:
```java
// Add suggestion
suggestedBooks.add(bookTitle);

// Check if suggestion exists
if (suggestedBooks.contains(bookTitle)) { }

// Remove suggestion (when added to library)
suggestedBooks.remove(bookTitle);

// List all suggestions
for (String suggestion : suggestedBooks) {
    System.out.println(suggestion);
}
```

## Usage Examples

### Complete User Registration Flow
```java
// 1. User chooses to register
System.out.print("Do you have an account?[y/n]: ");
String response = input.nextLine(); // "n"

// 2. Registration process
if (response.equals("n")) {
    register(); // Handles input and validation
}
```

### Complete Login and Access Flow
```java
// 1. User provides credentials
System.out.print("Enter username: ");
String username = input.nextLine();
System.out.print("Enter password: ");
String password = input.nextLine();

// 2. Validate credentials
String validation = checkAccount(username, password);

// 3. Route based on validation and role
if (validation == null) {
    if (username.equals("carlo") && password.equals("morva")) {
        // Admin access
        handleAdminDashboard();
    } else {
        // Guest access
        handleGuestDashboard();
    }
} else {
    System.out.println(validation); // Error message
}
```

### Book Borrowing Flow
```java
// 1. Check borrowing eligibility
if (borrowCount < 2) {
    // 2. Display available books
    for (String book : books.keySet()) {
        System.out.println(book);
    }
    
    // 3. User selects book
    String selectedBook = input.nextLine();
    
    // 4. Validate book exists
    if (books.get(selectedBook) != null) {
        // 5. Update borrowing records
        borrowCount++;
        String key = username + ", " + date + ", (" + borrowCount + ")";
        borrowers.put(key, selectedBook);
    }
}
```

## Constants and Configuration

### Default Accounts
```java
// Admin account
Username: "carlo"
Password: "morva"

// Pre-registered guest accounts
"carlo2" → "morva2"
"carlo3" → "morva3"
"carlo32" → "morva"
```

### Initial Books
```java
"Java" → "https://pdfdrive.com/Java"
"JavaScript" → "https://pdfdrive.com/JavaScript"
```

### System Limits
```java
MAX_BORROW_LIMIT = 2;           // Maximum books per user
BORROW_DURATION_DAYS = 30;      // Maximum borrowing period
```

### Menu Options
```java
// Admin menu
String[] adminCommands = {
    "Add a new book",
    "Remove a book", 
    "See list of borrowers",
    "View suggestions",
    "Log-out"
};

// Guest menu
String[] guestCommands = {
    "Browse a book",
    "Borrow a book",
    "Return a book",
    "Suggest a book",
    "Log-out"
};
```

## Error Codes

### Authentication Errors
| Error Message | Cause | Solution |
|---------------|-------|----------|
| `"Username already taken!"` | Registration with existing username | Choose different username |
| `"Username doesn't exist!"` | Login with non-existent username | Register first or check spelling |
| `"Wrong password!"` | Incorrect password | Enter correct password |

### Borrowing Errors
| Error Message | Cause | Solution |
|---------------|-------|----------|
| `"You already borrowed 2 books!"` | Borrowing limit reached | Return a book first |
| `"You already borrowed this book!"` | Duplicate borrowing attempt | Choose different book |
| `"No book found!"` | Invalid book title | Check available books list |
| `"You have not borrowed any books."` | Return attempt with no borrowed books | Borrow books first |

### System Messages
| Message | Context | Meaning |
|---------|---------|---------|
| `"No books available at the moment"` | Empty library | Admin needs to add books |
| `"No guests borrowed for a moment.."` | Empty borrowers list | No current borrowers |
| `"No user-suggestions at the moment..."` | Empty suggestions | No pending suggestions |

## State Management

### Global State Variables
```java
static Hashtable<String, String> registeredAcc = new Hashtable<>();
static TreeMap<String, String> borrowers = new TreeMap<String, String>();
static Hashtable<String, String> books = new Hashtable<>();
static Set<String> keys = registeredAcc.keySet();
```

### Session Variables (Local to main)
```java
LocalDate date = LocalDate.now();                    // Current date
HashSet<String> suggestedBooks = new HashSet<>();    // User suggestions
ArrayList<String> borrowCountHistory = new ArrayList<>(); // Borrow count tracking
int borrowCount = 0;                                  // Current user's borrow count
```

### State Transitions
```
Application Start → User Choice (y/n for account)
    ↓
Registration/Login → Credential Validation
    ↓
Role Detection → Admin Dashboard / Guest Dashboard
    ↓
Menu Selection → Feature Execution → Back to Menu
    ↓
Logout → Back to Main Menu
```

---

**Version**: 1.0  
**Last Updated**: [Current Date]  
**Compatible with**: Java 8+