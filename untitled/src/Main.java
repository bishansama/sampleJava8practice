import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();


        employees.add(new Employee("John", "Doe", 1000));
        employees.add(new Employee("Jane", "Doe", 2000));
        employees.add(new Employee("Jim", "Doe", 3000));
        employees.add(new Employee("Jill", "Doe", 4000));
        employees.add(new Employee("Jack", "Doe", 5000));

        List<Employee> employees2 = employees.stream().filter(employee -> employee.getSalary() > 3000).toList();

        employees2.forEach(System.out::println);


    }
}