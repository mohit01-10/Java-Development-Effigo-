import java.util.Scanner;

public class DickJackProject {
    public static void main(String[] args) {
        int r1= rollDice();
        int r2 = rollDice();

        System.out.println("\n------- Dice Project 1 ---------- ");

        System.out.println("Roll 1 result: "+ r1);
        System.out.println("Roll 2 result: "+ r2);

        System.out.println("\n------- Dice Project 2 ---------- ");

        int t1= rollDice();
        int t2 = rollDice();
        int t3 = rollDice();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter three numbers between 1 and 6 ");

        int n1  = scan.nextInt();
        int n2  = scan.nextInt();
        int n3  = scan.nextInt();

        if(isLessThan1(n1, n2, n3) || isGreaterThan6(n1, n2, n3))
        {
            System.out.println("Please follow the rules of the game.");
            System.exit(0);
        }
        int s1 = n1 + n2 + n3;
        int s2 = t1 + t2 + t3;

        System.out.println("your sum: " + s1 + " Computer sum: " + s2 );
        if(checkWin(s1, s2))
            System.out.println("You Won the game!");
        else{
            System.out.println("You lost the game");
        }
        scan.close();
        
    }

    public static int rollDice() {
        double randomNum = Math.random() * 6;
        randomNum += 1;
        
        return (int) randomNum;
    }

    public static boolean isLessThan1( int n1, int n2, int n3)
    {
        return( n1 < 1 || n2 < 1 || n3 < 1);
    }

    public static boolean isGreaterThan6( int n1, int n2, int n3)
    {
        return( n1 > 6 || n2 > 6 || n3 >6);
    }

    public static boolean checkWin (int sumnum, int sumDiceRolls){
        return (sumnum > sumDiceRolls);
    }
    
}
