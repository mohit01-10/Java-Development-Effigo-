import java.util.Scanner;

public class RockPaperScissor {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Let's play Rock Paper Scissors.");
        System.out.println("When I say 'shoot', Choose: rock, paper, or scissors.\n");
        System.out.println("Are you ready? Write 'yes' if you are");

        String ch = scan.nextLine();

        if(ch.equals("yes")){

            System.out.println("\nGreat!");
            System.out.println("\nrock - paper - scissors, shoot!\n");

            String userInput = scan.nextLine();

            String computerInput = computerChoice();

            

           String r = result(userInput, computerInput);

           printResult(userInput, computerInput, r);

        } else {
            System.out.println("Darn, some other time...");
        }
        scan.close();

    }

    public static String computerChoice(){
        int n = (int)(Math.random()*3);

        switch (n) {
            case 0:
                return "rock";
            case 1:
                return "paper";
            case 2:
                return "scissors";
        
            default:
             return "Sorry, the computer ran into some problem!";
        }
    }

    public static String result(String userInput, String computerInput) {
        if( (userInput.equals("rock") && computerInput.equals("scissors")) || 
        (userInput.equals("paper") && computerInput.equals("rock")) || 
        (userInput.equals("scissors") && computerInput.equals("paper")) ) {

          return ("You Win");
        }
        
        else if( (userInput.equals("scissors") && computerInput.equals("rock")) || 
        (userInput.equals("rock") && computerInput.equals("paper")) || 
        (userInput.equals("paper") && computerInput.equals("scissors")) ) {

            return "You Lose";
        }
        else if (userInput.equals(computerInput)){
            return "It's a tie";
        }
        else{
            return "INVALID CHOICE";
        }
    }

    public static void printResult(String userInput, String computerInput, String result){

        System.out.println("\nYour Choice:   " + userInput);
        System.out.println("\nComputer Choice:   " + computerInput);
        System.out.println("\n\n" + result);
    }
}
