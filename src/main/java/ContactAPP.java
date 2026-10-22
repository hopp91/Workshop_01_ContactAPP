import java.util.ArrayList;
import java.util.Comparator;

public class ContactAPP {

    void main() {
        ArrayList<Contact> contacts = new ArrayList<>();
        String choice;

        do {
            IO.println("\n===== CONTACT MANAGEMENT APP =====");
            IO.println("1. Add Contact");
            IO.println("2. Look Up Contact by Name");
            IO.println("3. View All Contacts");
            IO.println("4. Exit");
            IO.println("Enter your choice: ");

            choice = IO.readln();

            switch (choice) {
                case "1" -> {
                    IO.println("Enter name: ");
                    String name = IO.readln();

                    IO.println("Enter email: ");
                    String email = IO.readln();

                    IO.println("Enter phone number: ");
                    String phone = IO.readln();

                    if (name.isBlank() || email.isBlank()
                            || phone.isBlank()) {
                        IO.println("All fields are required.");
                    } else {
                        contacts.add(new Contact(name, email, phone));
                        contacts.sort(Comparator.comparing(
                                Contact::name,
                                String.CASE_INSENSITIVE_ORDER
                        ));
                        IO.println("Contact added successfully!");
                    }
                }

                case "2" -> {
                    IO.println("Enter name to look up: ");
                    String searchName = IO.readln();
                    boolean found = false;

                    for (Contact contact : contacts) {
                        if (contact.name().equalsIgnoreCase(searchName)) {
                            IO.println("\nContact Found:");
                            IO.println("Name: " + contact.name());
                            IO.println("Email: " + contact.email());
                            IO.println("Phone: " + contact.phone());
                            found = true;
                        }
                    }

                    if (!found) {
                        IO.println("Contact not found.");
                    }
                }

                case "3" -> {
                    if (contacts.isEmpty()) {
                        IO.println("No contacts available.");
                    } else {
                        for (Contact contact : contacts) {
                            IO.println("\nName: " + contact.name());
                            IO.println("Email: " + contact.email());
                            IO.println("Phone: " + contact.phone());
                        }
                    }
                }

                case "4" -> IO.println("Goodbye!");

                default -> IO.println("Invalid choice. Please try again.");
            }

        } while (!choice.equals("4"));
    }

    record Contact(String name, String email, String phone) {}

}
