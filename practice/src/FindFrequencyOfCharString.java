import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindFrequencyOfCharString {

    public static void main(String[] args) {
        String input = "Java Concept Of The Day";

            Map<Character, Long> resultMap  = input.chars()
                    .mapToObj(s -> (char) s)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            resultMap.forEach((key, value) -> {
                System.out.println(key + " : " + value);
            });
        }
    }

