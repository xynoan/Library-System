# FLAWS SO FAR: LOGGING IN IS REPETITIVE, THERE SHOULD BE AN OPTION IF YOU WANT TO LOG OUT/GO BACK TO A CERTAIN OPTION.
# Implement friendly navigation
# checkpoint: borrow system
from datetime import date

registeredAcc = {
    "carlo": "morva"
}
books = list()
borrowers = dict()
adminCommands = ["Add a new book", "Remove a book", "See list of borrowers"]
guestCommands = ["Browse a book", "Borrow a book", "Return a book"]
registerFunc = True
borrowCount = 0

def borrowFunc():
    if len(borrowers) == 0:
        return "\nNone so far."
    else:
        for user in borrowers:
            return user

while True:
    print("============LIBRARY_SYSTEM=================")    
    doesUserHaveAcc = input("Do you have an account?[y/n]: ")
    if doesUserHaveAcc == "y":
        username = input("Enter username: ")
        password = input("Enter password: ")
        try:
            registeredAcc[username]
        except:
            print("Username doesn't exist or wrong password!")
            exit()
        # if the user is an admin, he will be presented the admin panel
        if registeredAcc[username] == password and username == "carlo" and password == "morva":
            print("================ADMIN======================")
            for idx, command in enumerate(adminCommands):
                print(f"{idx + 1}.", command)
            adminCommand = input("Good day, Admin! What do you wanna do?[1-3]\n")
            if adminCommand == "1":
                bookName = input("Name of the book you want to add: ")
                books.append(bookName)
                print("Book added!")
                continue
            elif adminCommand == "2":
                bookName = input("Name of the book you want to remove: ")
                books.remove(bookName)
                print("Book removed!")
                continue
            else:
                print(f"Here are the list of borrowers: {borrowFunc()}")
                continue
        # guest panel
        elif registeredAcc[username] == password:
            print("================GUEST======================")
            for idx, command in enumerate(guestCommands):
                print(f"{idx + 1}.", command)
            guestCommand = input(f"Good day, Guest! What do you wanna do?[1-{len(adminCommands)}]\n")
            if guestCommand == "1":
                print("==============BOOKS_AVAILABLE==============")
                if len(books) == 0:
                    print("\nNone so far.")
                else:
                    for idx, book in enumerate(books):
                        print(f"{idx + 1}. {book}")
                print("===========================================")
                bookName = input("Name of the book you want to browse: ")
                try:
                    books.index(bookName)
                except:
                    print("No book found!")
                    exit()
                print(f"You have chosen {books[books.index(bookName)]}!")
                continue
            elif guestCommand == "2":
                print("==========BORROWING_SYSTEM_RULES===========")
                print("1. Maximum of 2 books can be borrowed")
                print("2. You can borrow for as long as 1 month")
                print("==============BOOKS_AVAILABLE==============")
                if len(books) == 0:
                    print("\nNone so far.")
                else:
                    for idx, book in enumerate(books):
                        print(f"{idx + 1}. {book}")
                print("===========================================")
                bookName = input("Name of the book you want to borrow: ")
                try:
                    books.index(bookName)
                except:
                    print("No book found!")
                    exit()
                borrowCount = borrowCount + 1
                borrowers[username] = books[books.index(bookName)]
                print(borrowers)
                print(borrowCount)
                print(f"You have successfully borrowed {books[books.index(bookName)]}!")
                continue
            else:
                print("You have chosen 3!")
        else:
            print("Username doesn't exist or wrong password!")
            exit()
    # If the user doesn't have an account, he can register
    else:
        registerFunc = True
        while registerFunc is True:
            print("================REGISTER===================")
            regUsername = input("Enter username: ")
            regPassword = input("Enter password: ")
            usernameAlrExist = False
            for key in registeredAcc.keys():
                if regUsername == key:
                    usernameAlrExist = True
            if usernameAlrExist is True:
                print("Username already taken!")
                continue
            else:
                registerFunc = False
        registeredAcc[regUsername] = regPassword
        continue