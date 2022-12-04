
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library_System {

    /*
    Features:
    1. Login/Register System
    2. Admin Dashboard (Add/remove book, see list of borrowers, see recommendations)
    3. Guest Dashboard (Browse/borrow/return/recommend book)
     */
    private static Hashtable<String, String> registeredAcc = new Hashtable<>();
    private static Hashtable<String, String> borrowers = new Hashtable<>();
    private static Hashtable<String, String> books = new Hashtable<>();
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

    public static boolean borrowersHandler(String username) {
        for (String i : borrowers.keySet()) {
            if (i.contains(username)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        String[] adminCommands = {"Add a new book", "Remove a book", "See list of borrowers", "View suggestions", "Log-out"};
        String[] guestCommands = {"Browse a book", "Borrow a book", "Return a book", "Suggest a book", "Log-out"};
        HashSet<String> suggestedBooks = new HashSet<>();
        HashSet<String> loginsHistory = new HashSet<String>();
        ArrayList<String> borrowCountHistory = new ArrayList<String>();
        Pattern p = Pattern.compile("(?<!\\d)\\d(?!\\d)");
        int borrowCount = 0;
        // admins
        registeredAcc.put("carlo", "morva");
        // initial guest accounts
        registeredAcc.put("carlo2", "morva2");
        registeredAcc.put("carlo3", "morva3");
        // initial available books
        books.put("Java", "https://pdfdrive.com/Java");
        books.put("JavaScript", "https://pdfdrive.com/JavaScript");
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
                    loginsHistory.add(username);
                    boolean adminLogIn = true;
                    while (adminLogIn) {
                        System.out.println("================ADMIN_" + username + "======================");
                        for (int i = 0; i < adminCommands.length; i++) {
                            System.out.println((i + 1) + ". " + adminCommands[i]);
                        }
                        System.out.println("Good day, Admin! " + "Today is: " + date + "\nWhat do you wanna do?[1-5]");
                        String adminCommand = input.nextLine();
                        String bookName, bookLink;
                        switch (adminCommand) {
                            case "1":
                                while (true) {
                                    System.out.print("Name of the book you want to add: ");
                                    bookName = input.nextLine();
                                    System.out.print("Name of the link: ");
                                    bookLink = input.nextLine();
                                    books.put(bookName, bookLink);
                                    System.out.println("===============BOOK_ADDED==================");
                                    for (String i : books.keySet()) {
                                        System.out.println(i + ", Link: " + books.get(i));
                                    }
                                    System.out.println("===========================================");
                                    System.out.print("Would you like to add more?[y/n]: ");
                                    String addMore = input.nextLine();
                                    if (addMore.equals("n")) {
                                        break;
                                    }
                                }
                                break;
                            case "2":
                                System.out.println("Name of the book you want to remove: ");
                                for (String i : books.keySet()) {
                                    System.out.println(i);
                                }
                                bookName = input.nextLine();
                                books.remove(bookName);
                                System.out.println("Book removed!");
                                System.out.println(books);
                                break;
                            case "3":
                                System.out.println("Here are the list of borrowers: ");
                                if (borrowers.isEmpty()) {
                                    System.out.println("No guests borrowed for a moment..");
                                } else {
                                    for (String i : borrowers.keySet()) {
                                        System.out.println(i + borrowers.get(i));
                                    }
                                }
                                break;
                            case "4":
                                if (suggestedBooks.isEmpty()) { //Checks user-suggestion inventory
                                    System.out.println("===========================================");
                                    System.out.println("No user-suggestions at the moment..."); //Alerts admin that there are no suggestions
                                } else {
                                    System.out.println("Here are the user-suggested books: "); //Alerts admin that there are suggestions
                                    for (String sugg : suggestedBooks) { //Prints suggested books by the users
                                        System.out.println(sugg);
                                    }
                                    System.out.println("===========================================");
                                    System.out.println("Would you like to add any of these? [y/n]"); //Prompts admin if it wants to add anything on the list
                                    String decision = input.nextLine();
                                    if (decision.equals("y")) {
                                        while (true) {
                                            System.out.println("Please select one: "); //Prompts to select one from the list
                                            System.out.println(suggestedBooks);
                                            String add = input.nextLine();
                                            if (suggestedBooks.contains(add)) { //Checks if the name exists on the list
                                                System.out.println("Please input link for this book: ");
                                                String link = input.nextLine();
                                                books.put(add, link);
                                                suggestedBooks.remove(add);
                                                System.out.println("Book has been added to our Library!"); //Notifies the admin that the book has been added
                                            } else {
                                                System.out.println("Book not found!"); //If no such book has been found on the list
                                            }
                                            if (!suggestedBooks.isEmpty()) {
                                                System.out.println("Would you like to add another? [y/n]"); //Prompts the admin if he wants to add more (Looping it again)
                                                String dec = input.nextLine();
                                                if (dec.equals("n")) {
                                                    break; //If "Y" the system loops
                                                } 
                                                continue;
                                            }
                                            break;
                                        }
                                    } else {
                                        break; //If entered "N" the loop breaks        
                                    }
                                }
                                break;
                            case "5":
                                System.out.println("Do you want to Log-out? [y/n]"); //Asks for confirmation (for accidental input of actions)
                                String answer = input.nextLine();
                                if (answer.equals("y")) {
                                    adminLogIn = false;
                                }
                        }
                    }
                    // guest panel
                } else if (checkAccount(username, password) == null) {
                    if (!loginsHistory.contains(username)) {
                        borrowCount = 0;
                    }
                    for (String i : borrowers.keySet()) {
                        if (i.contains(username)) {
                            System.out.println(i);
                            Matcher m = p.matcher(i);
                            while (m.find()) {
                                borrowCountHistory.add(m.group());
                            }
                            borrowCount = Integer.parseInt(borrowCountHistory.get(borrowCountHistory.size() - 1));
                        }
                    }
                    loginsHistory.add(username);
                    boolean guestLogIn = true;
                    while (guestLogIn) {
                        System.out.println("================GUEST_" + username + "======================");
                        for (int i = 0; i < guestCommands.length; i++) {
                            System.out.println((i + 1) + ". " + guestCommands[i]);
                        }
                        System.out.println("Good day, Guest!" + " Today is: " + date + "\nWhat do you wanna do?[1-4]");
                        String guestCommand = input.nextLine();
                        String bookName;
                        switch (guestCommand) {
                            case "1":
                                System.out.println("==============BOOKS_AVAILABLE==============");
                                if (books.isEmpty()) {
                                    System.out.println("No books available at the moment"
                                            + ", please come back later!");
                                    continue;
                                } else {
                                    for (String i : books.keySet()) {
                                        System.out.println(i);
                                    }
                                }
                                System.out.println("===========================================");
                                while (true) {
                                    System.out.print("Name of the book you want to browse: ");
                                    bookName = input.nextLine();
                                    if (books.get(bookName) != null) {
                                        System.out.println("You have chosen " + bookName + "!");
                                        System.out.print("Here is the link: ");
                                        for (String i : books.keySet()) {
                                            if (bookName.equals(i)) {
                                                System.out.println(books.get(i));
                                            }
                                        }
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
                                    System.out.println("==========BORROWING_SYSTEM_RULES===========");
                                    System.out.println("1. Maximum of 2 books can be borrowed");
                                    System.out.println("2. You can borrow for as long as 1 month");
                                    System.out.println("==============BOOKS_AVAILABLE==============");
                                    if (books.isEmpty()) {
                                        System.out.println("No books available at the moment"
                                                + ", please come back later!");
                                        continue;
                                    } else {
                                        for (String i : books.keySet()) {
                                            System.out.println(i);
                                        }
                                    }
                                    System.out.println("===========================================");
                                    while (true) {
                                        System.out.print("Name of the book you want to borrow: ");
                                        bookName = input.nextLine();
                                        // if the bookName exists on books
                                        if (books.get(bookName) != null) {
                                            borrowCount++;
                                            borrowers.put(username + ", " + date + ", " + "(" + borrowCount + ")", bookName);
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
                                if (borrowersHandler(username) == false) {
                                    System.out.println("You have not borrowed any books.");
                                } else {
                                    while (true) {
                                        System.out.println("Here are the list of books you borrowed: ");
                                        for (String i : borrowers.keySet()) {
                                            if (i.contains(username)) {
                                                System.out.println(borrowers.get(i));
                                            }
                                        }
                                        System.out.print("Name of the book you want to return: ");
                                        bookName = input.nextLine();
                                        boolean noBookFound = false;
                                        for (String i : borrowers.keySet()) {
                                            if (i.contains(username)) {
                                                if (borrowers.get(i).equals(bookName)) {
                                                    borrowCount--;
                                                    borrowers.remove(i);
                                                    break;
                                                } else {
                                                    noBookFound = true;
                                                    System.out.println("No book found!");
                                                    break;
                                                }
                                            }
                                        }
                                        if (noBookFound) {
                                            continue;
                                        }
                                        System.out.println("Book returned!");
                                        if (borrowersHandler(username) == true) {
                                            System.out.print("Would you like to return more?[y/n]: ");
                                            String returnMore = input.nextLine();
                                            if (returnMore.equals("n")) {
                                                break;
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                }
                                break;
                            case "4":
                                System.out.println("==========SUGGEST_A_BOOK===========");
                                while (true) {
                                    System.out.println("Please enter the name of the book: "); //Asks the user the name of the book
                                    String suggestedBook = input.nextLine();
                                    suggestedBooks.add(suggestedBook); //Adds the book to the suggestions list
                                    System.out.println("Book has been added to user-recommendations!"); //Notifies the user that the book has been added
                                    System.out.println("Would you like to recommend more? [y/n]"); //Prompts if the user wants to add more (System loops)
                                    String suggestMore = input.nextLine();
                                    if (suggestMore.equals("n")) { //If entered "N" the loop breaks
                                        break;
                                    }
                                }
                                break;
                            case "5":
                                System.out.println("Do you want to Log-out? [y/n]"); //Asks for confirmation (for accidental input of actions)
                                String answer = input.nextLine();
                                if (answer.equals("y")) {
                                    guestLogIn = false;
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
