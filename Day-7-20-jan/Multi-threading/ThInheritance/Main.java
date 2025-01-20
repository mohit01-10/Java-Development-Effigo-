public class Main {

    public static void main(String[] args) {
        AtheleteThread firstAthlete=new AtheleteThread("Mark",Thread.NORM_PRIORITY,84291);
        AtheleteThread secondAthlete=new AtheleteThread("Sam",Thread.MAX_PRIORITY,47256);

        // Starting the threads
        firstAthlete.start();
        secondAthlete.start();
    }

}