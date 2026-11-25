package app;

import datastructure.ContactBook;
import model.Contact;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ContactBook book = new ContactBook();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readChoice();
            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    searchByName();
                    break;
                case 3:
                    searchByPhone();
                    break;
                case 4:
                    displayAll();
                    break;
                case 5:
                    updateContact();
                    break;
                case 6:
                    deleteContact();
                    break;
                case 7:
                    System.out.println("Total contacts: " + book.getTotal());
                    break;
                case 8:
                    System.out.println("Program closed. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 to 8.");
            }
        } while (choice != 8);
    }

    // ---------- Menu actions ----------
    private static void addContact() {
        String name = readName();
        String phone = readNewPhone();
        String email = readEmail();

        Contact contact = book.addContact(name, phone, email);
        System.out.println("Contact added with ID " + contact.getId());
    }

    private static void searchByName() {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine().trim();

        ArrayList<Contact> result = book.findByName(name);
        if (result.isEmpty()) {
            System.out.println("No contact found with this name.");
        } else {
            System.out.println("Found " + result.size() + " contact(s):");
            printList(result);
        }
    }

    private static void searchByPhone() {
        System.out.print("Enter phone number to search: ");
        Contact contact = book.findByPhone(sc.nextLine().trim());

        if (contact == null) {
            System.out.println("No contact found with this phone number.");
        } else {
            System.out.println("Contact found:");
            System.out.println(contact);
        }
    }

    private static void displayAll() {
        ArrayList<Contact> list = book.getAllSorted();
        if (list.isEmpty()) {
            System.out.println("No contacts to show.");
        } else {
            System.out.println("---- Contacts in alphabetical order ----");
            printList(list);
        }
    }

    private static void updateContact() {
        System.out.print("Enter phone number of the contact to update: ");
        Contact contact = book.findByPhone(sc.nextLine().trim());
        if (contact == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println("Current details: " + contact);
        System.out.println("(Press Enter to keep the old value)");
        System.out.print("New name: ");
        String newName = sc.nextLine().trim();
        System.out.print("New phone: ");
        String newPhone = sc.nextLine().trim();
        System.out.print("New email: ");
        String newEmail = sc.nextLine().trim();

        if (!newPhone.isEmpty()) {
            if (!isValidPhone(newPhone)) {
                System.out.println("Invalid phone number. Update cancelled.");
                return;
            }
            if (!newPhone.equals(contact.getPhone()) && book.phoneExists(newPhone)) {
                System.out.println("This phone number already exists. Update cancelled.");
                return;
            }
        }
        if (!newEmail.isEmpty() && !isValidEmail(newEmail)) {
            System.out.println("Invalid email. Update cancelled.");
            return;
        }

        book.updateContact(contact, newName, newPhone, newEmail);
        System.out.println("Contact updated.");
    }

    private static void deleteContact() {
        System.out.print("Enter phone number of the contact to delete: ");
        String phone = sc.nextLine().trim();

        if (book.deleteContact(phone)) {
            System.out.println("Contact deleted.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    // ---------- Input helpers ----------
    private static void showMenu() {
        System.out.println();
        System.out.println("===== CONTACT MANAGER =====");
        System.out.println("1. Add a new contact");
        System.out.println("2. Search contact by name");
        System.out.println("3. Search contact by phone number");
        System.out.println("4. Display all contacts (alphabetical)");
        System.out.println("5. Update a contact");
        System.out.println("6. Delete a contact");
        System.out.println("7. Total number of contacts");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int readChoice() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String readName() {
        while (true) {
            System.out.print("Enter name: ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) {
                return name;
            }
            System.out.println("Name cannot be empty.");
        }
    }

    private static String readNewPhone() {
        while (true) {
            System.out.print("Enter phone number (10 digits): ");
            String phone = sc.nextLine().trim();
            if (!isValidPhone(phone)) {
                System.out.println("Invalid phone number.");
            } else if (book.phoneExists(phone)) {
                System.out.println("This phone number already exists.");
            } else {
                return phone;
            }
        }
    }

    private static String readEmail() {
        while (true) {
            System.out.print("Enter email: ");
            String email = sc.nextLine().trim();
            if (isValidEmail(email)) {
                return email;
            }
            System.out.println("Invalid email.");
        }
    }

    private static boolean isValidPhone(String phone) {
        return phone.matches("[0-9]{10}");
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static void printList(ArrayList<Contact> list) {
        for (Contact contact : list) {
            System.out.println(contact);
        }
    }
}
