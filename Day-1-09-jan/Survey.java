import java.util.Scanner;

public class Survey {
    public static void main(String[] args) {

        // *********PART A: PICKING UP THE USER'S ANSWERS*********

        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome. Thank you for taking the survey");

        System.out.println("\nWhat is your name?");
        String name = sc.nextLine();

        System.out.println("\nHow much money do you spend on coffee?");
        double coffeePrice = sc.nextDouble();

        System.out.println("\nHow much money do you spend on fast food?");
        double foodPrice = sc.nextDouble();

        System.out.println("\nHow many times a week do you buy coffee?");
        int coffeeAmount = sc.nextInt();

        System.out.println("\nHow many times a week do you buy fast food?");
        int foodAmount = sc.nextInt();

        // *********PART B: RESPONDING TO THE USER**********
        System.out.println("Thank you " + name + " for answering all <counter> questions");
        System.out.println("Weekly, you spend " + (coffeePrice * coffeeAmount) + " on coffee");
        System.out.println("Weekly, you spend " + (foodPrice * foodAmount) + " on food");

        sc.close();

    }
}
