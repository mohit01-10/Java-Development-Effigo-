 public class Main {
  
    public static void main(String[] args) {
        Person person = new Person("Abc", "Xyz","12/12/1212", 5); 
        person.setSeatNumber(10);
        System.out.println("Name: " + person.getName() + "\n" + "Nationality: " + person.getNationality() + "\n" + "Date of Birth: " + person.getDateOfBirth() + "\n" + "Seat Number: " + person.getSeatNumber() + "\n");

    }
  
  
}
