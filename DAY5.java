import java.util.*;
import java.util.stream.*;

class Employee {
    String name;
    String department;
    double salary;

    Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String toString() {
        return name + " (" + salary + ")";
    }
}

public class DAY5 {
    public static void main(String[] args) {

        List<Employee> employees = List.of(
            new Employee("A", "IT", 50000),
            new Employee("B", "IT", 70000),
            new Employee("C", "IT", 90000),
            new Employee("D", "HR", 40000),
            new Employee("E", "HR", 60000),
            new Employee("F", "HR", 80000)
        );

        // Average salary per department
        Map<String, Double> avgSalary =
            employees.stream()
                     .collect(Collectors.groupingBy(
                         e -> e.department,
                         Collectors.averagingDouble(e -> e.salary)
                     ));

        System.out.println("Average Salary:");
        avgSalary.forEach((k, v) -> System.out.println(k + " -> " + v));

        // Top 3 earners per department
        Map<String, List<Employee>> top3 =
            employees.stream()
                     .collect(Collectors.groupingBy(
                         e -> e.department,
                         Collectors.collectingAndThen(
                             Collectors.toList(),
                             list -> list.stream()
                                         .sorted(Comparator.comparingDouble((Employee e) -> e.salary).reversed())
                                         .limit(3)
                                         .collect(Collectors.toList())
                         )
                     ));

        System.out.println("\nTop 3 per department:");
        top3.forEach((dept, list) -> {
            System.out.println(dept + " -> " + list);
        });
    }
}