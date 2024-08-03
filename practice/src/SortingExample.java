import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortingExample {

    public static void main(String[] args) {

        ArrayList<Employee> employeeArrayList =  new ArrayList<>();
        employeeArrayList.add(new Employee("Bishan", "samaddar", 230000));
        employeeArrayList.add(new Employee("SHreya", "paul", 330000));
        employeeArrayList.add(new Employee("Surya", "majee", 130000));
        employeeArrayList.add(new Employee("Bikash", "kumar", 430000));
        employeeArrayList.add(new Employee("Prakash", "Mallick", 10000));
        employeeArrayList.add(new Employee("Tuhin", "Sapui", 400));
        employeeArrayList.add(new Employee("Tuhin", "Sapui", 400));

       employeeArrayList.stream()
               .distinct()
               .collect(Collectors.groupingBy(Employee::getFirstName, Collectors.counting()))
               .forEach((key, value) -> {System.out.println(key);});
                //.filter(employee -> employee.getSalary() > 10000)
               // .sorted(Comparator.comparing(Employee::getSalary))
              //  .collect(Collectors.partitioningBy(employee -> employee.getSalary() > 10000, Collectors.counting()))
              /*  .forEach((key , value) -> {
                    if (key) {
                        System.out.println("Salary for mangers " + value);
                    } else {
                        System.out.println("Salary for employees " + value);
                    }
                });*/
    }
}
