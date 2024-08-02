import java.util.Arrays;
import java.util.List;

public class RemoveDuplicateFromList {

    public static void main(String[] args) {
        List<String> listName = Arrays.asList("John", "John", "Doe", "Jane", "Doe", "Doe");
        listName.stream().distinct().forEach(System.out::println);
    }

}
