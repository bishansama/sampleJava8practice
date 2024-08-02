import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintOddAndEvenNumber {

    public static void main(String args[]) {
        Integer[] numbers = {71, 18, 42, 21, 67, 32, 95, 14, 56, 87};

        //List<Integer> numbersList = Arrays.asList(numbers);

        //Arrays.stream(numbers)
        //Stream.of(numbers)
        //Arrays.asList(numbers).stream()


      Map<Boolean, List<Integer>> booleanListMap = Stream.of(numbers).collect(Collectors.partitioningBy(number -> number % 2 == 0));

        booleanListMap.forEach((key, value) -> {
            if (key) {
                System.out.println("Even numbers: " + value);
            } else {
                System.out.println("Odd numbers: " + value);
            }
        });
    }

}
