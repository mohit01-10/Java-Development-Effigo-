public class temp {
    public static void main(String[] args) {
        String a="abc";
        String b=a;

        System.out.println(a.equals(b));
        System.out.println(a == b);

        String c = "abc";

        System.out.println(a.equals(c));
        System.out.println(a == c);

        String d = new String("abc");

        System.out.println(c.equals(d));
        System.out.println(c == d);
    }
}
