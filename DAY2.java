import java.util.*;

class Contact {
    String firstName;
    String lastName;
    String phone;

    Contact(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String toString() {
        return firstName + " " + lastName + " - " + phone;
    }
}

class PhoneBook {
    private List<Contact> contacts = new ArrayList<>();
    private Map<String, Contact> phoneMap = new HashMap<>();
    private Set<String> uniqueNumbers = new HashSet<>();

    public void addContact(Contact c) {
        if (uniqueNumbers.add(c.phone)) {
            contacts.add(c);
            phoneMap.put(c.phone, c);
        } else {
            System.out.println("Duplicate number: " + c.phone);
        }
    }

    public Contact searchByNumber(String number) {
        return phoneMap.get(number);
    }

    public List<Contact> listAllSorted() {
        contacts.sort(Comparator.comparing(c -> c.lastName));
        return contacts;
    }
}

public class DAY2 {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();

        pb.addContact(new Contact("John", "Doe", "111"));
        pb.addContact(new Contact("Alice", "Smith", "222"));
        pb.addContact(new Contact("Bob", "Brown", "333"));

        System.out.println("Search: " + pb.searchByNumber("222"));

        System.out.println("\nSorted Contacts:");
        for (Contact c : pb.listAllSorted()) {
            System.out.println(c);
        }
    }
}