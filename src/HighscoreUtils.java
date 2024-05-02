import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighscoreUtils {
    public static final String JSON_SRC = "resources/highscores.json";

    public static final int MAX_PLAYERS = 5;

    public static List<Player> loadOrCreateHighscoreList() {
        File file = new File(JSON_SRC);
        List<Player> highscores;

        // Verificar se o arquivo já existe
        if (file.exists()) {
            // Se o arquivo existir, carregue os dados do arquivo JSON
            highscores = loadHighscoreListFromFile();
        } else {
            // Se o arquivo não existir, crie um novo e inicialize a lista de highscores
            highscores = new ArrayList<>();
            saveHighscoreListToFile(highscores);
        }
        return highscores;
    }

    public static List<Player> loadHighscoreListFromFile() {
        List<Player> highscores = new ArrayList<>();
        try (Reader reader = new FileReader(JSON_SRC)) {
            // Usando Gson para desserializar o arquivo JSON em uma lista de Players
            Type listType = new TypeToken<ArrayList<Player>>() {
            }.getType();
            highscores = new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highscores;
    }

    public static List<Player> updateHighscoreList(List<Player> highscores, Player p) {
        // Adiciona o novo jogador à lista
        highscores.add(p);

        // Ordena a lista de (por tentativas e duração)
        highscores.sort(Comparator.comparingInt(Player::getAttempts)
                .thenComparingLong(Player::getDurationMs));

        // Remove o último jogador da lista se ela exceder o número máximo de jogadores
        if (highscores.size() > MAX_PLAYERS) {
            highscores.remove(MAX_PLAYERS);
        }

        // Salva a lista atualizada no arquivo JSON
        saveHighscoreListToFile(highscores);

        // Retorna a lista atualizada
        return highscores;
    }

    public static void saveHighscoreListToFile(List<Player> highscores) {
        try (Writer writer = new FileWriter(JSON_SRC)) {
            // Usando Gson para serializar a lista em formato JSON e escrevê-la no arquivo
            new Gson().toJson(highscores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}