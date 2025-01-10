public class ReturnValues {
    public static void main(String[] args) {
        double area = calarea(2.3, 3.6);
        System.out.println("Area: "+area);

        double area2 = calarea(1.6, 2.4);
        System.out.println("Area2: "+area2);

        double area3 = calarea(2.6, 4.2);
        System.out.println("Area3: "+area3);

        System.out.println(explainarea("Eng")); 
    }

    public static double calarea(double l, double b)
    {
        double area = l * b;
        return area;
    }

    public static String explainarea(String lang)
    {
        switch (lang) {
            case "Eng":
                return "Area equals l * w";
             
            case "French":
                return "La surface est egale a la longueur * la largeur ";
        
            case "Spanish":
                return "area es igual a largo * ancho";


        
            default: return "language not available";

        }
    }
}
