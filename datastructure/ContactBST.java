package datastructure;

import model.Contact;
import java.util.ArrayList;

// Binary Search Tree that keeps contacts ordered by name (case ignored).
// Smaller names go to the left, bigger or equal names go to the right.
public class ContactBST {

    private static class Node {
        Contact data;
        Node left;
        Node right;

        Node(Contact data) {
            this.data = data;
        }
    }

    private Node root;

    // ---------- Insert ----------
    public void insert(Contact contact) {
        root = insert(root, contact);
    }

    private Node insert(Node node, Contact contact) {
        if (node == null) {
            return new Node(contact);
        }
        if (contact.getName().compareToIgnoreCase(node.data.getName()) < 0) {
            node.left = insert(node.left, contact);
        } else {
            node.right = insert(node.right, contact);
        }
        return node;
    }

    // ---------- Search by name ----------
    public ArrayList<Contact> searchByName(String name) {
        ArrayList<Contact> result = new ArrayList<>();
        search(root, name, result);
        return result;
    }

    private void search(Node node, String name, ArrayList<Contact> result) {
        if (node == null) {
            return;
        }
        int cmp = name.compareToIgnoreCase(node.data.getName());
        if (cmp < 0) {
            search(node.left, name, result);
        } else if (cmp > 0) {
            search(node.right, name, result);
        } else {
            result.add(node.data);
            search(node.right, name, result); // same name may exist again on the right
        }
    }

    // ---------- Delete ----------
    public void delete(Contact contact) {
        root = delete(root, contact);
    }

    private Node delete(Node node, Contact contact) {
        if (node == null) {
            return null;
        }
        int cmp = contact.getName().compareToIgnoreCase(node.data.getName());
        if (cmp < 0) {
            node.left = delete(node.left, contact);
        } else if (cmp > 0) {
            node.right = delete(node.right, contact);
        } else if (node.data != contact) {
            node.right = delete(node.right, contact); // same name, different contact
        } else {
            // Case 1 and 2: zero or one child
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Case 3: two children -> replace with smallest node of right side
            Node successor = findMin(node.right);
            node.data = successor.data;
            node.right = delete(node.right, successor.data);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // ---------- Sorted list (in-order traversal) ----------
    public ArrayList<Contact> getSortedList() {
        ArrayList<Contact> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }

    private void inorder(Node node, ArrayList<Contact> list) {
        if (node == null) {
            return;
        }
        inorder(node.left, list);
        list.add(node.data);
        inorder(node.right, list);
    }
}