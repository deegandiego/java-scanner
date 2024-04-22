import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("Este é meu programa Java Scanner.");
        System.out.println("Como posso te chamar?");

        String name = in.nextLine();

        System.out.println("Olá, " + name + ".");
    }
}
