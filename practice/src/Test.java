public class Test {
    public static void main(String[] args) {
        String input = "Bishan Samaddar";
        main(input);
    }

    
    public static  void reverseSimple(String input) {
        if (input == null || input.length() <= 1) {
            System.out.println("Error: Input is null or empty");
            return;
        }
        
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        System.out.println(chars);
    }

    public static void reverseString(String input) {
       String result = input.chars()
       .mapToObj(c -> (char) c)
       .reduce("", (a, b) -> b + a, String::concat);
       System.out.println(result);
    }

    public static void main(String input) {
        String text = "A\uD83D\uDE00B"; // A😀B (😀 is U+1F600)

        // Using codePoints()
        text.codePoints().forEach(cp -> 
            System.out.println(cp + " -> " + new String(Character.toChars(cp)))
        );
    }
    
}
