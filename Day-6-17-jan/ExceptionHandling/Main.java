
public class Main {

    public static void main(String[] args) {

        Magazine magazine1 = new Magazine("Magazine 1", "Publisher 1", 1, 2020);
        System.out.println(magazine1.getTitle());

        magazine1.setTitle("Magazine 2");

        magazine1.setPublisher("Publisher 2");

        magazine1.setPublicationYear(10);

    }

}
