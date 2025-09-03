import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeradorDeSaida {
    public static void main(String[] args) {
        int total = 5_000_000;
        List<Integer> numeros = new ArrayList<>(total);

        for (int i = 0; i < total; i++) {
            numeros.add(i);
        }

        Collections.shuffle(numeros);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("saida.txt"))) {
            for (int num : numeros) {
                writer.write(Integer.toString(num));
                writer.newLine();
            }
            System.out.println("Arquivo gerado com 5 milhões de números únicos!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }
}

