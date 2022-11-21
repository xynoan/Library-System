import java.time.LocalDate;
import java.util.*;

public class App {
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
    private static HashSet<String> suggremove = new HashSet<>();

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
        String[] adminCommands = {"Add a new book", "Remove a book", "See list of borrowers", "View suggestions"};
        String[] guestCommands = {"Browse a book", "Borrow a book", "Return a book", "Suggest a book"};
        HashSet<String> books = new HashSet<>();
        HashSet<String> booksBorrowed = new HashSet<>();
        HashSet<String> suggestedBooks = new HashSet<>();
        suggestedBooks = suggremove;
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
                    System.out.println("Good day, Admin! " + "Today is: " + date + "\nWhat do you wanna do?[1-4]");
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
                                System.out.println(registeredAcc.get(i));
                            }
                        case "4":
                            if (suggestedBooks.size() == 0){ //Checks user-suggestion inventory
                                System.out.println("===========================================");
                                System.out.println("No user-suggestions at the moment..."); //Alerts admin that there are no suggestions
                            }else {
                                System.out.println("Here are the user-suggested books: "); //Alerts admin that there are suggestions
                                for (String sugg : suggestedBooks){ //Prints suggested books by the users
                                    System.out.println(sugg);
                                }
                                System.out.println("===========================================");
                                System.out.println("Would you like to add any of these? [y/n]"); //Prompts admin if it wants to add anything on the list
                                String decision = input.nextLine();
                                if (decision.equals("y")){
                                    while (true){
                                        System.out.println("Please select one: "); //Prompts to select one from the list
                                        System.out.println(suggestedBooks);
                                        String add = input.nextLine();
                                        if (suggestedBooks.contains(add)){ //Checks if the name exists on the list
                                            books.add(add);
                                            System.out.println("Book has been added to our Library!"); //Notifies the admin that the book has been added
                                        }else {
                                            System.out.println("Book not found!"); //If no such book has been found on the list
                                        }
                                        System.out.println("Would you like to add another? [y/n]"); //Prompts the admin if he wants to add more (Looping it again)
                                        String dec = input.nextLine();
                                        if (dec.equals("y")){
                                            continue; //If "Y" the system loops
                                        }else{
                                            break; //If "N" the loop breaks
                                        } 
                                    }                
                                }else {
                                    break; //If entered "N" the loop breaks        
                                }                                    
                            }
                    }
                    // guest panel
                } else if (checkAccount(username, password) == null) {
                    System.out.println("================GUEST======================");
                    for (int i = 0; i < guestCommands.length; i++) {
                        System.out.println((i + 1) + ". " + guestCommands[i]);
                    }
                    System.out.println("Good day, Guest!" + "Today is: " + date + "\nWhat do you wanna do?[1-4]");
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
                        case "4":
                            System.out.println("==========SUGGEST_A_BOOK===========");
                            while (true){
                                System.out.println("Please enter the name of the book: "); //Asks the user the name of the book
                                String sugBook = input.nextLine(); 
                                suggestedBooks.add(sugBook); //Adds the book to the suggestions list
                                System.out.println("Book has been added to user-recommendations!"); //Notifies the user that the book has been added
                                System.out.println("Would you like to recommend more? [y/n]"); //Prompts if the user wants to add more (System loops)
                                String sugMore = input.nextLine();
                                if (sugMore.equals("n")){ //If entered "N" the loop breaks
                                    break;
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