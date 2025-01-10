public class Parameters {
    public static void main(String[] args) {
        calarea(2.3, 3.6);
        calarea(1.6, 2.4);
        calarea(2.6, 4.2);
    }

    public static void calarea(double l, double b) {
        double area = l * b;
        System.out.println("Area : " + area);
    }
}
