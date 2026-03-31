import java.util.*;

class Contact {
    String firstName;
    String lastName;
    String phone;

    Contact(String f, String l, String p) {
        firstName = f;
        lastName = l;
        phone = p;
    }

    public String toString() {
        return firstName + " " + lastName + " - " + phone;
    }
}

class PhoneBook {
    private List<Contact> contacts = new ArrayList<>();

    void add(Contact c) {
        contacts.add(c);
    }

    Optional<Contact> findByNumber(String number) {
        return contacts.stream()
                .filter(c -> c.phone.equals(number))
                .findFirst();
    }

    void sortContacts() {
        contacts.sort(
            Comparator.comparing((Contact c) -> c.lastName)
                      .thenComparing(c -> c.firstName)
        );
    }

    void display() {
        contacts.forEach(System.out::println);
    }
}

public class DAY3 {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();

        pb.add(new Contact("John", "Doe", "111"));
        pb.add(new Contact("Alice", "Smith", "222"));

        pb.sortContacts();
        pb.display();

        pb.findByNumber("222")
          .ifPresentOrElse(
              System.out::println,
              () -> System.out.println("Not found")
          );
    }
}