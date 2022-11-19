package library_system;

import java.time.LocalDate;
import java.util.*;

public class Library_System {
    /*
    Features:
    1. Login/Register System
    2. Admin Dashboard (Add/remove book, see list of borrowers)
    3. Guest Dashboard (Browse/borrow/return book)
    
    Missing features:
    1. Alert admins if the borrowers exceeded their time limit of borrowing.
    2. Turn the array of available books to objects: name of book as key
    and link to read the book as value.
     */
    private static Hashtable<String, String> registeredAcc = new Hashtable<>();
    private static Hashtable<String, String> borrowers = new Hashtable<>();
    private static Set<String> keys = registeredAcc.keySet();

    public static void register() {
        Scanner input = new Scanner(System.in);
        String username, password;
        while (true) {
            System.out.println("================REGISTER===================");
            System.out.print("Enter username: ");
            username = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();
            boolean usernameAlrExist = false;
            for (String key : keys) {
                if (username.equals(key)) {
                    usernameAlrExist = true;
                }
            }
            if (usernameAlrExist == true) {
                System.out.println("Username already taken!");
            } else {
                break;
            }
        }

        registeredAcc.put(username, password);
    }

    public static String checkAccount(String username, String password) {
        if (registeredAcc.get(username) == null) {
            return "Username doesn't exist!";
        }
        if (registeredAcc.get(username) != null
                && !password.equals(registeredAcc.get(username))) {
            return "Wrong password!";
        }

        return null;
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        String[] adminCommands = {"Add a new book", "Remove a book", "See list of borrowers"};
        String[] guestCommands = {"Browse a book", "Borrow a book", "Return a book"};
        HashSet<String> books = new HashSet<>();
        HashSet<String> booksBorrowed = new HashSet<>();
        int borrowCount = 0;
        registeredAcc.put("carlo", "morva");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("============LIBRARY_SYSTEM=================");
            System.out.println("Type \"exit\" in order to exit the program"
                    + ", thanks for trusting us!");
            System.out.println("===========================================");
            System.out.print("Do you have an account?[y/n]: ");
            String doesUserHaveAcc = input.nextLine();
            if (doesUserHaveAcc.equals("exit")) {
                break;
            }

            if (doesUserHaveAcc.equals("y")) {
                System.out.print("Enter username: ");
                String username = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                // if the user is an admin, he will be presented the admin panel     
                if (checkAccount(username, password) == null
                        && username.equals("carlo")
                        && password.equals("morva")) {
                    System.out.println("================ADMIN======================");
                    for (int i = 0; i < adminCommands.length; i++) {
                        System.out.println((i + 1) + ". " + adminCommands[i]);
                    }
                    System.out.println("Good day, Admin! What do you wanna do?[1-3]");
                    String adminCommand = input.nextLine();
                    String bookName;
                    switch (adminCommand) {
                        case "1":
                            while (true) {
                                System.out.print("Name of the book you want to add: ");
                                bookName = input.nextLine();
                                books.add(bookName);
                                System.out.println("Book added!");
                                System.out.println(books);
                                System.out.print("Would you like to add more?[y/n]: ");
                                String addMore = input.nextLine();
                                if (addMore.equals("n")) {
                                    break;
                                }
                            }
                            break;
                        case "2":
                            System.out.print("Name of the book you want to remove: ");
                            bookName = input.nextLine();
                            books.remove(bookName);
                            System.out.println("Book removed!");
                            System.out.println(books);
                            break;
                        case "3":
                            System.out.println("Here are the list of borrowers: ");
                            for (String i : borrowers.keySet()) {
                                System.out.println(i + borrowers.get(i));
                            }
                    }
                    // guest panel
                } else if (checkAccount(username, password) == null) {
                    System.out.println("================GUEST======================");
                    for (int i = 0; i < guestCommands.length; i++) {
                        System.out.println((i + 1) + ". " + guestCommands[i]);
                    }
                    System.out.println("Good day, Guest! What do you wanna do?[1-3]");
                    String guestCommand = input.nextLine();
                    String bookName;
                    switch (guestCommand) {
                        case "1":
                            System.out.println("==============BOOKS_AVAILABLE==============");
                            if (books.size() == 0) {
                                System.out.println("No books available at the moment"
                                        + ", please come back later!");
                                continue;
                            } else {
                                for (String book : books) {
                                    System.out.println(book);
                                }
                            }
                            System.out.println("===========================================");
                            while (true) {
                                System.out.print("Name of the book you want to browse: ");
                                bookName = input.nextLine();
                                if (books.contains(bookName)) {
                                    System.out.println("You have chosen " + bookName + "!");
                                    break;
                                } else {
                                    System.out.println("No book found!");
                                }
                            }
                            break;
                        case "2":
                            if (borrowCount == 2) {
                                System.out.println("You already borrowed 2 books! Return at least "
                                        + "1 book in order to borrow again!");
                            } else {
                                System.out.println(borrowCount);
                                System.out.println("==========BORROWING_SYSTEM_RULES===========");
                                System.out.println("1. Maximum of 2 books can be borrowed");
                                System.out.println("2. You can borrow for as long as 1 month");
                                System.out.println("==============BOOKS_AVAILABLE==============");
                                if (books.size() == 0) {
                                    System.out.println("No books available at the moment"
                                            + ", please come back later!");
                                    continue;
                                } else {
                                    for (String book : books) {
                                        System.out.println(book);
                                    }
                                }
                                System.out.println("===========================================");
                                while (true) {
                                    System.out.print("Name of the book you want to borrow: ");
                                    bookName = input.nextLine();
                                    if (books.contains(bookName)) {
                                        borrowCount++;
                                        borrowers.put(username + ", " + date + ", " + "(" + borrowCount + ")", bookName);
                                        booksBorrowed.add(bookName);
                                        System.out.println("You have successfully borrowed " + bookName + "!");
                                        if (borrowCount != 2) {
                                            System.out.print("Would you like to borrow more?[y/n]: ");
                                            String borrowMore = input.nextLine();
                                            if (borrowMore.equals("y")) {
                                                continue;
                                            }
                                        }
                                        break;
                                    } else {
                                        System.out.println("No book found!");
                                    }
                                }
                            }
                            break;
                        case "3":
                            if (booksBorrowed.isEmpty()) {
                                System.out.println("You have not borrowed any books.");
                            } else {
                                while (true) {
                                    System.out.println("Here are the list of books you borrowed: ");
                                    for (String book : booksBorrowed) {
                                        System.out.println(book);
                                    }
                                    System.out.print("Name of the book you want to return: ");
                                    bookName = input.nextLine();
                                    if (booksBorrowed.contains(bookName)) {
                                        borrowCount--;
                                        booksBorrowed.remove(bookName);
                                        System.out.println("Book returned!");
                                        if (!booksBorrowed.isEmpty()) {
                                            System.out.print("Would you like to return more?[y/n]: ");
                                            String returnMore = input.nextLine();
                                            if (returnMore.equals("n")) {
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    } else {
                                        System.out.println("No book found!");
                                    }
                                }
                            }
                    }
                    // if the user doesn't have an account, they can register
                } else {
                    System.out.println(checkAccount(username, password));
                    System.out.print("Would you like to register?[y/n]: ");
                    String register = input.nextLine();
                    if (register.equals("y")) {
                        register();
                    }
                }
            } else {
                register();
            }
        }

    }
}
