import java.util.Scanner;

public class CounterTool {
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        System.out.println("I hear you like to count by threes");

        System.out.println("Jimmy: It depends.\n Oh, ok...");

        
        System.out.print("\n1. Pick a number to count by: ");
        int n = scan.nextInt();

        System.out.print("\n2. Pick a number to start counting from: ");
        int s = scan.nextInt();

        System.out.print("\n3. Pick a number to count to: ");
        int e = scan.nextInt();

        for(int st = s; st<= e; st+=n)
        {
            System.out.print(st+" ");
        }

        scan.close();
    }


}
