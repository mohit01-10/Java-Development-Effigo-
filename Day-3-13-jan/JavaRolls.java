import java.util.Scanner;

public class JavaRolls {
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        System.out.println("Let's play Rolling Java. Type anything to start.");
        scan.nextLine();

        System.out.println("Great, here are the rules:\n\n - If you roll a 6, the game stops.\n - If you roll a 4, nothing happens.\n - Otherwise, you get 1 point.\n" + //
                        "\nYou must collect at least 3 points to win. Enter anything to roll:");

        int score=0;
        while (true)
        {
            scan.nextLine();
            int diceRoll = rollDice();
            System.out.print("You rolled a "+diceRoll+".");
            if(diceRoll == 6)
            {
                System.out.println(" End of game.\n");
                System.out.println("Your score is: "+ score);
                if(score >=3) System.out.println("Wow, that's lucky. You win!");
                else
                    System.out.println("Tough luck, you lose :(");
                break;
            }

            else if(diceRoll == 4) System.out.println(" Zero points. Keep rolling.\n");
            else{
                System.out.println(" One point. Keep rolling.\n");
                score += 1;
            }

        }

        scan.close();

    }

    public static int rollDice(){

        return (int) (Math.random()*6) +1;
    }


}
