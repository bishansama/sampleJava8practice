import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class PrintNumberMultipliesBy5 {

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(2,25, 15,34,23,78,100);

        integerList.stream().filter(number -> number % 5 == 0).forEach(System.out::println);
    }
}
