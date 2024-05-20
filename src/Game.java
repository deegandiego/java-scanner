import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static final String JSON_SRC = "resources/highscores.json";

    public static final int MAX_ATTEMPTS = 65;

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

        Player p = new Player();

        System.out.println("Este é meu programa Java Scanner.");

        // Await 2s
        Thread.sleep(1000);
        System.out.println();

        System.out.println("Como posso te chamar?");

        p.setName("");
        int count = 0;
        while (p.getName().trim().isEmpty()) {
            p.setName(in.nextLine());
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

        System.out.println("Olá, " + p.getName() + ", vamos jogar \"adivinha\"? (s=sim, n=não)");

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

        int numeroAleatorio = random.nextInt(100) + 1;
        p.setAttempts(0);
        p.setGuess(-1);

        System.out.println("Já pensei em um número entre 1 e 100. Tente adivinhar!");

        long inicioJogo = System.currentTimeMillis();

        while (p.getGuess() != numeroAleatorio) {
            p.sumAttempt(1);

            if (p.getAttempts() < MAX_ATTEMPTS) {
                System.out.print("Digite o seu palpite: ");

                // Verifica se a entrada é um inteiro
                while (!in.hasNextInt()) {
                    System.out.println("Por favor, digite um número válido.");
                    in.next();
                }

                p.setGuess(in.nextInt());

                if (p.getGuess() < numeroAleatorio) {
                    System.out.println("Tente um número maior.");
                } else if (p.getGuess() > numeroAleatorio) {
                    System.out.println("Tente um número menor.");
                }
            } else {
                p.setGuess(numeroAleatorio);
                System.out.println();
                System.out.println("Ei, o número que tinha pensado é: " + numeroAleatorio);
                p.sumAttempt(-1);
            }

            if (p.getGuess() == numeroAleatorio) {
                p.setDurationMs(System.currentTimeMillis() - inicioJogo);
                System.out.println();
                System.out.println("Parabéns, " + p.getName() + "!");
                System.out.println("Você descobriu em " + p.getAttempts() + " tentativas!");

                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println("*** T O P   H I G H S C O R E S ***");
                System.out.println();

                try {
                    List<Player> pList = HighscoreUtils.loadOrCreateHighscoreList();
                    pList = HighscoreUtils.updateHighscoreList(pList, p);

                    for (int i = 0; i < pList.size(); i++) {
                        Player thisPlayer = pList.get(i);
                        String ln0 = "", ln1 = "", ln2 = "", ln3 = "";

                        switch (i) {
                            case 0:
                                ln0 = "░░░░░░░";
                                ln1 = "░▀█░░░░";
                                ln2 = "░░█░░░░";
                                ln3 = "░▀▀▀░▀░";
                                break;
                            case 1:
                                ln0 = "░░░░░░░";
                                ln1 = "░▀▀▄░░░";
                                ln2 = "░▄▀░░░░";
                                ln3 = "░▀▀▀░▀░";
                                break;
                            case 2:
                                ln0 = "░░░░░░░";
                                ln1 = "░▀▀▄░░░";
                                ln2 = "░░▀▄░░░";
                                ln3 = "░▀▀░░▀░";
                                break;
                            case 3:
                                ln0 = "░░░░░░░";
                                ln1 = "░█░█░░░";
                                ln2 = "░░▀█░░░";
                                ln3 = "░░░▀░▀░";
                                break;
                            case 4:
                                ln0 = "░░░░░░░";
                                ln1 = "░█▀▀░░░";
                                ln2 = "░▀▀█░░░";
                                ln3 = "░▀▀░░▀░";
                                break;
                        }

                        if (thisPlayer.getName() != null) {
                            System.out.println(ln0 + " [Nome] " + thisPlayer.getName());

                            String pattern = "yyyy MMM dd";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                            String dateStr = simpleDateFormat.format(thisPlayer.getDateTime());
                            System.out.println(ln1 + " [Número] " + thisPlayer.getGuess());
                            System.out.println(ln2 + " [Tentativas] " + thisPlayer.getAttempts() + "x");
                            System.out.println(ln3 + " [Duração] " + thisPlayer.getDurationMs() + "ms");
                            System.out.println(ln0 + " [Data] " + simpleDateFormat.format(thisPlayer.getDateTime()));

                            if (i < 4)
                                System.out.println(ln0);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        in.close();

    }
}
