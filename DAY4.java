import java.util.*;
import java.util.function.*;

class Contact {
    String name;
    String phone;

    Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String toString() {
        return name + " - " + phone;
    }
}

class PhoneBook {
    List<Contact> contacts = new ArrayList<>();

    void add(Contact c) {
        contacts.add(c);
    }

    List<Contact> filter(Predicate<Contact> condition) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            if (condition.test(c)) {
                result.add(c);
            }
        }
        return result;
    }

    void applyToAll(Consumer<Contact> action) {
        contacts.forEach(action);
    }
}

public class DAY4 {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();

        pb.add(new Contact("John", "111"));
        pb.add(new Contact("Alice", "222"));

        List<Contact> filtered = pb.filter(c -> c.name.startsWith("A"));
        filtered.forEach(System.out::println);

        pb.applyToAll(c -> System.out.println("Contact: " + c));
    }
}