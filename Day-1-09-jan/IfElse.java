public class IfElse {

    public static void main(String[] args) {

        int chemistryGrade = 95;
        int biologyGrade = 75;

        System.out.println("Me: Hi Java, did I score better in biology?");
        // Add if-else statement here
        if (biologyGrade > chemistryGrade) {
            System.out.println("Java: Yes, I did score better in biology.");
        } else {
            System.out.println("Java: No, I did not score better in biology.");
        }
        double sales = 37.55;
        double costs = 5.55;
        System.out.println("Me: Hi Java, did we make money?");
        // Add if-else statement here
        if (sales > costs) {
            System.out.println("Java: Yes, we made money.");
        } else {
            System.out.println("Java: No, we did not make money.");
        }

        double temperature = 15.5;
        double targetTemperature = 20.0;
        System.out.println("Me: Hi Java, is the temperature colder than our target?");
        // Add if-else statement here
        if (temperature < targetTemperature) {
            System.out.println("Java: Yes, the temperature is colder than our target.");
        } else {
            System.out.println("Java: No, the temperature is not colder than our target.");
        }

        int distance = 100;
        int speed = 60;
        System.out.println("Me: Hi Java, how long will it take for me to cover that distance at this speed?");
        // Add if-else statement here

        if (distance < speed) {
            System.out.println("Java: It will take me " + distance / speed + " hours to cover that distance.");

        } else {
            System.out.println(
                    "Java: It will take me  hours and " + (distance % speed) + " minutes to cover that distance.");
        }

        int hours = 2;
        int minutes = 30;
        System.out.println(
                "Me: Hi Java, how long will it take for me to cover that distance at this speed in hours and minutes?");
        // Add if-else statement here
        if (hours < minutes) {
            System.out.println(
                    "Java: It will take me " + hours + " hours and " + minutes + " minutes to cover that distance.");
        } else {
            System.out.println("Java: It will take me " + hours + " hours and " + (minutes - 60)
                    + " minutes to cover that distance.");
        }

        int currentSpeed = 60;
        int speedLimit = 70;
        System.out.println("Me: Hi Java, am I driving slower than the speed limit?");
        // Add if-else statement here

        if (currentSpeed < speedLimit) {
            System.out.println("Java: Yes, I am driving slower than the speed limit.");
        } else {
            System.out.println("Java: No, I am not driving slower than the speed limit.");
        }

        int age = 45;
        int retirementAge = 65;
        System.out.println("Me: Hi Java, am I old enough to retire?");
        // Add if-else statement here
        if (age >= retirementAge) {
            System.out.println("Java: Yes, I am old enough to retire.");
        } else {
            System.out.println("Java: No, I am not old enough to retire.");
        }

        double grade = 95.5;
        double minimumGrade = 90.0;
        System.out.println("Me: Hi Java, did I get a good grade?");
        // Add if-else statement here
        if (grade >= minimumGrade) {
            System.out.println("Java: Yes, I got a good grade.");
        } else {
            System.out.println("Java: No, I did not get a good grade.");
        }

        char myGrade = 'A';
        char bestGrade = 'B';
        System.out.println("Me: Did I get the best possible grade?");
        // Add if-else statement here
        if (myGrade == bestGrade) {
            System.out.println("Java: Yes, I got the best possible grade.");
        } else {
            System.out.println("Java: No, I did not get the best possible grade.");
        }

        String word = "hello";
        String secondWord = "hello";
        System.out.println("Me: Are the two words the same?");
        // Add if-else statement here
        if (word.equals(secondWord)) {
            System.out.println("Yes, the two words are the same.");
        } else {
            System.out.println("No, the two words are not the same.");
        }


        String thirdWord = "hello";
        String fourthWord = "goodbye";
        System.out.println("Me: Are the two words different");
        // Add if-else statement here
        if (!thirdWord.equals(fourthWord)) {
            System.out.println("Yes, the two words are different.");
        } else {
            System.out.println("No, the two words are not different.");
        }
    }
}
