import java.util.Scanner;

public class Javapedia {
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);

        System.out.println("\n*********JAVAPEDIA*********");
        System.out.println("How many historical figures will you register?");
    
        int people = scan.nextInt();
        scan.nextLine();

        String[][] dataBase = new String[people][3];

        for(int i = 0; i<dataBase.length; i++)
        {
            System.out.println("\nFigure: "+ (i+1));
            System.out.print("\t - Name: ");
            dataBase[i][0] = scan.nextLine();
            System.out.print("\t - Date of Birth: ");
            dataBase[i][1] = scan.nextLine();
            System.out.print("\t - Occupation: ");
            dataBase[i][2] = scan.nextLine();
            System.out.print("\n");

        }

        System.out.println("These are the values you stored: ");
        printArr(dataBase);


        System.out.println("\nWhose information do you need? ");

        String name = scan.nextLine();
        for( int i=0; i<dataBase.length; i++)
        {
            if(dataBase[i][0].equals(name)){
                System.out.println("\tName: " + dataBase[i][0]);
                System.out.println("\tDate of birth: " + dataBase[i][1]);
                System.out.println("\tOccupation: " + dataBase[i][2]);

            }
        }
        scan.close();

    }

    public static void printArr(String[][] arr){
        for(int i=0; i< arr.length; i++)
        {
            System.out.println("\t");
            for(int j=0; j< arr[i].length; j++){
                System.out.println(arr[i][j] + " ");
            }
            System.out.println("\n");
        }
    }
}
