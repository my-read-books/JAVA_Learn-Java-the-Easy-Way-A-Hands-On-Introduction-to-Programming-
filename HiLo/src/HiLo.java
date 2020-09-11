import java.util.Scanner;

public class HiLo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String playAgain = "";

        do {
            int theNumber = (int) (Math.random() * 100 + 1);
            int guess = 0;
            int counter = 0;
            System.out.println("Guess a number between 1 and 100:");
            while (guess != theNumber) {
                guess = scan.nextInt();
                counter++;
                if (guess < theNumber)
                    System.out.println(guess + " is low. Try again.");else if (guess > theNumber)
                    System.out.println(guess + " is high. Try again");
                else { System.out.println(guess + " is correct. You win!");
                    System.out.println("It only took you " + counter + " tries! Good work!");
                }
            }
            System.out.println("Would you like to play again (y/n)?");
            playAgain = scan.next();
        } while (playAgain.equalsIgnoreCase("y"));
        System.out.println("Thank for playing Goodbye!");
        scan.close();
    }
}
