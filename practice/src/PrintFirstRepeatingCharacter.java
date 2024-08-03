import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrintFirstRepeatingCharacter {

    public static void main(String[] args) {
         String input = "Bishan Samaddar";

         // Map<Character, Long>   characterLongMap =
         Character output  =
            input.chars().mapToObj(c -> Character.toLowerCase((char) c))
                 .collect(Collectors.groupingBy(Function.identity() , LinkedHashMap::new, Collectors.counting()))
                 .entrySet()
                 .stream().filter( x -> x.getValue() > 1)
                 .map(Map.Entry::getKey)
                         .findFirst().get();

            System.out.println(output);
    }
}
