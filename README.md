# Library Management System

**Team Leader:** Barlam, Arjie

**Team Members:**
1. Ausejo, Althea Gayle
2. Guisijan, Rizaleda Gabriela
3. Morva, Nathaniel Carlo
4. Salvado, Shella
5. Vista, Valentino

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage Guide](#usage-guide)
- [User Roles](#user-roles)
- [Technical Documentation](#technical-documentation)
- [System Limitations](#system-limitations)
- [Contributing](#contributing)

## Overview

A console-based Library Management System that enables users to browse and borrow books while providing administrators with tools to manage the library's inventory. The system includes user authentication, book management, borrowing tracking, and suggestion features.

**Main Objective:** Provide a free book lending service to users while maintaining proper inventory control and borrowing limits.

## Features

### For Administrators
- ✅ **Book Management**: Add and remove books from the library inventory
- ✅ **Borrower Tracking**: View list of current borrowers and their borrowed books
- ✅ **Suggestion Management**: Review and approve user-suggested books
- ✅ **User Management**: Monitor borrowing patterns and limits

### For Guests/Users
- ✅ **Book Browsing**: Search and view available books with direct links
- ✅ **Book Borrowing**: Borrow up to 2 books for a maximum of 1 month
- ✅ **Book Returning**: Return borrowed books to free up borrowing slots
- ✅ **Book Suggestions**: Suggest new books for the library to consider
- ✅ **Account Management**: User registration and login system

## System Requirements

- **Java**: JDK 8 or higher
- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 512MB RAM
- **Storage**: 50MB free space

## Installation

1. **Clone or Download the Repository**
   ```bash
   git clone <repository-url>
   cd library-system
   ```

2. **Compile the Java Program**
   ```bash
   javac Library_System.java
   ```

3. **Run the Application**
   ```bash
   java Library_System
   ```

## Usage Guide

### Starting the Application

When you run the program, you'll see the main menu:
```
============LIBRARY_SYSTEM=================
Type "exit" in order to exit the program, thanks for trusting us!
===========================================
Do you have an account?[y/n]: 
```

### For New Users

1. Type `n` when asked if you have an account
2. Follow the registration prompts:
   - Enter a unique username
   - Enter a password
3. After registration, you can log in with your credentials

### For Existing Users

1. Type `y` when asked if you have an account
2. Enter your username and password
3. Access your respective dashboard (Admin or Guest)

## User Roles

### Administrator Access
- **Default Admin Account**: 
  - Username: `carlo`
  - Password: `morva`

**Admin Dashboard Options:**
1. **Add a new book** - Add books with name and link
2. **Remove a book** - Delete books from inventory
3. **See list of borrowers** - View current borrowing status
4. **View suggestions** - Review and approve user suggestions
5. **Log-out** - Exit admin session

### Guest Access

**Guest Dashboard Options:**
1. **Browse a book** - View available books and their links
2. **Borrow a book** - Borrow books (max 2 books, 1 month limit)
3. **Return a book** - Return borrowed books
4. **Suggest a book** - Recommend books for library addition
5. **Log-out** - Exit guest session

## Technical Documentation

### Class Structure

**Main Class:** `Library_System`

### Data Structures

| Data Structure | Purpose | Type |
|---------------|---------|------|
| `registeredAcc` | Store user accounts (username → password) | `Hashtable<String, String>` |
| `borrowers` | Track borrowed books (user+date+count → book) | `TreeMap<String, String>` |
| `books` | Store available books (title → link) | `Hashtable<String, String>` |
| `suggestedBooks` | Store user book suggestions | `HashSet<String>` |

### Key Methods

#### `register()`
- **Purpose**: Handle user registration
- **Parameters**: None (uses Scanner input)
- **Returns**: void
- **Functionality**: Validates unique usernames and stores credentials

#### `checkAccount(String username, String password)`
- **Purpose**: Validate user login credentials
- **Parameters**: 
  - `username`: User's login name
  - `password`: User's password
- **Returns**: String (null if valid, error message if invalid)

#### `borrowersHandler(String username)`
- **Purpose**: Check if user has borrowed books
- **Parameters**: `username`: User to check
- **Returns**: boolean (true if user has borrowed books)

### Borrowing System Logic

**Key Format**: `"username, date, (borrowCount)"`
**Value**: `"bookName"`

Example: `"john, 2024-01-15, (2)" → "Java Programming"`

### Default Data

**Pre-registered Accounts:**
- Admin: `carlo/morva`
- Guests: `carlo2/morva2`, `carlo3/morva3`, `carlo32/morva`

**Initial Books:**
- Java → https://pdfdrive.com/Java
- JavaScript → https://pdfdrive.com/JavaScript

## System Limitations

### Current Constraints
- ❌ **No Persistent Storage**: Data is lost when program exits
- ❌ **Single Admin Account**: Only one hardcoded admin
- ❌ **Limited Borrowing**: Maximum 2 books per user
- ❌ **Manual Setup**: Admin must add books each session
- ❌ **Console Interface**: No graphical user interface
- ❌ **Basic Authentication**: No password encryption

### Business Rules
- **Borrowing Limit**: 2 books maximum per user
- **Borrowing Duration**: 1 month maximum
- **Account Types**: Admin (full access) vs Guest (limited access)
- **Book Addition**: Only admins can add/remove books
- **Session-based**: No data persistence between runs

## Future Enhancements

### Recommended Improvements
1. **Database Integration**: Add persistent storage (MySQL, PostgreSQL)
2. **Password Security**: Implement password hashing
3. **GUI Development**: Create desktop or web interface
4. **Multiple Admins**: Support multiple administrator accounts
5. **Email Notifications**: Overdue book notifications
6. **Search Functionality**: Advanced book search and filtering
7. **Reporting System**: Generate borrowing statistics and reports
8. **Book Categories**: Organize books by genre/subject
9. **User Profiles**: Extended user information and preferences
10. **API Development**: REST API for mobile app integration

## Contributing

### Development Setup
1. Fork the repository
2. Create a feature branch
3. Make changes and test thoroughly
4. Submit a pull request with detailed description

### Code Style Guidelines
- Use meaningful variable and method names
- Add comments for complex logic
- Follow Java naming conventions
- Test all user flows before submission

### Testing Checklist
- [ ] Admin login and all admin functions
- [ ] Guest registration and login
- [ ] Book browsing and borrowing
- [ ] Book returning functionality
- [ ] Suggestion system
- [ ] Error handling for invalid inputs
- [ ] Borrowing limit enforcement

## Support

For questions or issues:
1. Check existing documentation
2. Review the code comments
3. Contact the development team
4. Submit an issue in the repository

---
**Note**: This system is designed for educational purposes and demonstrates basic object-oriented programming concepts in Java.
