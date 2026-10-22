import java.util.ArrayList;
import java.util.Comparator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ContactAPP {

    private final ArrayList<Contact> contacts = new ArrayList<>();
    private final Path FILE_PATH = Path.of("contacts.txt");

    void main() {
        loadContacts();

        String choice;

        do {
            displayMenu();
            choice = IO.readln().trim();

            switch (choice) {
                case "1" -> addContact();
                case "2" -> searchContact();
                case "3" -> displayContacts();
                case "4" -> {
                    saveContacts();
                    IO.println("Goodbye!");
                }
                default -> IO.println(
                        "Invalid choice. Please try again."
                );
            }

        } while (!choice.equals("4"));
    }

    // Display the main menu

    void displayMenu() {
        IO.println("\n===== CONTACT MANAGEMENT APP =====");
        IO.println("1. Add Contact");
        IO.println("2. Look Up Contact by Name");
        IO.println("3. View All Contacts");
        IO.println("4. Exit");
        IO.println("Enter your choice: ");
    }

    // Add a new contact

    void addContact() {
        IO.println("Enter name: ");
        String name = IO.readln().trim();

        IO.println("Enter email: ");
        String email = IO.readln().trim();

        IO.println("Enter phone number: ");
        String phone = IO.readln().trim();

        if (name.isBlank() || email.isBlank()
                || phone.isBlank()) {
            IO.println("All fields are required.");
            return;
        }

        if (!validateEmail(email)) {
            IO.println("Invalid email address.");
            return;
        }

        if (!validatePhone(phone)) {
            IO.println("Invalid phone number.");
            return;
        }

        if (findContact(name) != null) {
            IO.println("A contact with this name already exists.");
            return;
        }

        contacts.add(new Contact(name, email, phone));
        sortContactsList();

        IO.println("Contact added successfully!");
    }

    void searchContact() {
        IO.println("Enter name to look up: ");
        String name = IO.readln().trim();

        Contact contact = findContact(name);

        if (contact == null) {
            IO.println("Contact not found.");
        } else {
            displayContact(contact);
        }
    }

    Contact findContact(String name) {
        for (Contact contact : contacts) {
            if (contact.name().equalsIgnoreCase(name)) {
                return contact;
            }
        }

        return null;
    }

    void displayContacts() {
        if (contacts.isEmpty()) {
            IO.println("No contacts available.");
            return;
        }

        IO.println("\n===== ALL CONTACTS =====");

        for (Contact contact : contacts) {
            displayContact(contact);
        }
    }

    void displayContact(Contact contact) {
        IO.println("\nName: " + contact.name());
        IO.println("Email: " + contact.email());
        IO.println("Phone: " + contact.phone());
    }


    int findContactIndex(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).name().equalsIgnoreCase(name)) {
                return i;
            }
        }

        return -1;
    }


    boolean validateEmail(String email) {
        return email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        );
    }

    boolean validatePhone(String phone) {
        return phone.matches("[+0-9() .-]{7,20}")
                && phone.matches(".*[0-9].*");
    }

    void sortContactsList() {
        contacts.sort(
                Comparator.comparing(
                        Contact::name,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }


    void saveContacts() {
        ArrayList<String> lines = new ArrayList<>();

        for (Contact contact : contacts) {
            lines.add(
                    contact.name() + "\t"
                            + contact.email() + "\t"
                            + contact.phone()
            );
        }

        try {
            Files.write(FILE_PATH, lines);
            IO.println("Contacts saved successfully.");
        } catch (IOException e) {
            IO.println("Error saving contacts: " + e.getMessage());
        }
    }

    void loadContacts() {
        if (!Files.exists(FILE_PATH)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(FILE_PATH)) {
                String[] parts = line.split("\t", -1);

                if (parts.length == 3
                        && !parts[0].isBlank()
                        && validateEmail(parts[1])
                        && validatePhone(parts[2])
                        && findContact(parts[0]) == null) {

                    contacts.add(
                            new Contact(parts[0], parts[1], parts[2])

                    );
                }
            }
            sortContactsList();

        } catch (IOException e) {
            IO.println("Error loading contacts: " + e.getMessage());
        }
    }

    record Contact(String name, String email, String phone) {}}