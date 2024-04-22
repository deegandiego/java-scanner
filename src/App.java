import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("Não foi possível limpar o console cmd (windows)");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        clearScreen();

        Scanner in = new Scanner(System.in);

        System.out.println("Este é meu programa Java Scanner.");

        // Await 2s
        Thread.sleep(1000);
        System.out.println();

        System.out.println("Como posso te chamar?");

        String name = "";
        int count = 0;
        while (name.trim().isEmpty()) {
            name = in.nextLine();
            count++;
            if (count == 10) {
                count = 0;

                clearScreen();

                System.out.println("O que está fazendo?! Aqui, deixe-me limpar a tela para você!");

                // Await 2s
                Thread.sleep(2000);

                clearScreen();

                System.out.println("Como posso te chamar?");
            }

        }

        System.out.println("Olá, " + name + ", vamos jogar \"adivinha\"? (s=sim, n=não)");

        if (in.nextLine().toLowerCase().equals("n")) {
            GoodbyeFrame frame = new GoodbyeFrame();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);

            return;
        }

        System.out.println("Legal!");

        Thread.sleep(1000);

        System.out.print("Vamos começar");

        for (int i = 0; i < 3; i++) {
            Thread.sleep(500);
            System.out.print(".");
        }
        System.out.println();

        Random random = new Random();

        int numeroAleatorio = random.nextInt(100) + 1; // Gera um número aleatório entre 1 e 100
        int tentativas = 0;
        int palpite = -1;

        System.out.println("Já pensei em um número entre 1 e 100. Tente adivinhar!");

        while (palpite != numeroAleatorio) {
            System.out.print("Digite o seu palpite: ");

            // Verifica se a entrada é um inteiro
            while (!in.hasNextInt()) {
                System.out.println("Por favor, digite um número válido.");
                in.next();
            }

            palpite = in.nextInt();
            tentativas++;

            if (palpite < numeroAleatorio) {
                System.out.println("Tente um número maior.");
            } else if (palpite > numeroAleatorio) {
                System.out.println("Tente um número menor.");
            } else {
                System.out.println("Parabéns, " + name + "! Você acertou em " + tentativas + " tentativas!");
            }
        }

        in.close();

    }
}
