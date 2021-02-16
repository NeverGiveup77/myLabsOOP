public class Palindmore {
    public static void main(String[] args) {
        isPalindrome( "java Palindrome madam racecar apple kayak song noon");
    }
    // reverse string
    public static boolean reverseString(String s){
        String s1 = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            s1 += s.charAt(i);
        }
        return s1.equals(s);
    }
    // split sentence in words and checks each word whether its palindrome or not
    public static void isPalindrome(String s){
        String[] s1 = s.split(" ");
        for (int i = s1.length - 1; i > 0; i--)
            System.out.println("word " + s1[i] + " is palindrome = " + reverseString(s1[i]));
    }
}
