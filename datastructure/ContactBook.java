package datastructure;

import model.Contact;
import java.util.ArrayList;
import java.util.HashMap;

// Keeps every contact in two places:
// 1) HashMap (phone -> contact) for fast phone search
// 2) BST (ordered by name) for fast name search and sorted display
public class ContactBook {
    private HashMap<String, Contact> phoneMap = new HashMap<>();
    private ContactBST nameTree = new ContactBST();
    private int nextId = 1;

    public boolean phoneExists(String phone) {
        return phoneMap.containsKey(phone);
    }

    public Contact addContact(String name, String phone, String email) {
        Contact contact = new Contact(nextId, name, phone, email);
        nextId++;
        phoneMap.put(phone, contact);
        nameTree.insert(contact);
        return contact;
    }

    public Contact findByPhone(String phone) {
        return phoneMap.get(phone);
    }

    public ArrayList<Contact> findByName(String name) {
        return nameTree.searchByName(name);
    }

    public ArrayList<Contact> getAllSorted() {
        return nameTree.getSortedList();
    }

    public int getTotal() {
        return phoneMap.size();
    }

    // Name and phone are the keys of the BST and HashMap,
    // so remove the contact first, change it, then add it back.
    public void updateContact(Contact contact, String newName, String newPhone, String newEmail) {
        nameTree.delete(contact);
        phoneMap.remove(contact.getPhone());

        if (!newName.isEmpty()) {
            contact.setName(newName);
        }
        if (!newPhone.isEmpty()) {
            contact.setPhone(newPhone);
        }
        if (!newEmail.isEmpty()) {
            contact.setEmail(newEmail);
        }

        phoneMap.put(contact.getPhone(), contact);
        nameTree.insert(contact);
    }

    public boolean deleteContact(String phone) {
        Contact contact = phoneMap.get(phone);
        if (contact == null) {
            return false;
        }
        nameTree.delete(contact);
        phoneMap.remove(phone);
        return true;
    }
}