public class Primes {

    public static void main(String[] args) {
        for (int t = 2; t < 100; t++){
            if (IsPrime(t))
                System.out.println(t + " is prime = " + IsPrime(t));
        }
    }

    // check if number is prime
    public static boolean IsPrime(int n) {
        for(int t = 2; t < n; t++){
            if (n % t == 0)
                return false;
        }
        return true;
    }
}
