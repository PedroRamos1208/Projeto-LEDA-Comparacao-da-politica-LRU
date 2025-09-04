import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] cargasLimitadas = {100, 3500, 7000, 10000, 35000, 70000, 100000, 250000, 350000, 500000};
        int[] cargas = {1000, 35000, 70000, 100000, 350000, 700000, 1000000, 2500000, 3500000, 5000000};

        for (int carga : cargasLimitadas) {
            long soma = 0;

            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

            for (int j = 0; j < 15; j++) {
                ArvoreSplay tree = new ArvoreSplay(carga);

                long inicio = System.nanoTime();
                for (int numero : elementos) {
                    tree.add(numero);
                }
                long fim = System.nanoTime();
                soma += (fim - inicio);
            }

            long media = soma / 15;
            gravarResultado("resultadosTestes/insercaoCacheNaoCheioSplay.txt", media);
        }

        for (int carga : cargas) {
            int capacidadeCache = (int) (carga * 0.1);
            long soma = 0;

            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

            for (int j = 0; j < 15; j++) {
                ArvoreSplay tree = new ArvoreSplay(capacidadeCache);

                long inicio = System.nanoTime();
                for (int numero : elementos) {
                    tree.add(numero);
                }
                long fim = System.nanoTime();
                soma += (fim - inicio);
            }

            long media = soma / 15;
            gravarResultado("resultadosTestes/insercaoCacheCheioSplay.txt", media);
        }

        for (int carga : cargasLimitadas) {
            int limite = carga;
            long somaPresente = 0, somaAusente = 0, somaUniforme = 0, somaScan = 0, somaZipf = 0;

            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", limite);

            int ultimoNum = elementos.get(elementos.size() - 1);
            int numInexistente = -1;
            Random rand = new Random();

            ArvoreSplay tree = new ArvoreSplay(limite);
            for (int numero : elementos) {
                tree.add(numero);
            }

            for (int j = 0; j < 15; j++) {
                long inicio = System.nanoTime();
                tree.search(ultimoNum);
                long fim = System.nanoTime();
                somaPresente += (fim - inicio);
            }

            for (int j = 0; j < 15; j++) {
                long inicio = System.nanoTime();
                tree.search(numInexistente);
                long fim = System.nanoTime();
                somaAusente += (fim - inicio);
            }

            for (int j = 0; j < 15; j++) {
                int elementoAleatorio = elementos.get(rand.nextInt(elementos.size()));
                long inicio = System.nanoTime();
                tree.search(elementoAleatorio);
                long fim = System.nanoTime();
                somaUniforme += (fim - inicio);
            }

            for (int j = 0; j < 15; j++) {
                long inicio = System.nanoTime();
                for (int numero : elementos) {
                    tree.search(numero);
                }
                long fim = System.nanoTime();
                somaScan += (fim - inicio);
            }

            double s = 1.0;
            int n = elementos.size();
            double[] probs = new double[n];
            double somaP = 0.0;
            for (int k = 1; k <= n; k++) {
                probs[k - 1] = 1.0 / Math.pow(k, s);
                somaP += probs[k - 1];
            }
            for (int k = 0; k < n; k++) probs[k] /= somaP;

            for (int j = 0; j < 15; j++) {
                long inicio = System.nanoTime();
                for (int acesso = 0; acesso < n; acesso++) {
                    double r = rand.nextDouble();
                    double acumulado = 0.0;
                    for (int k = 0; k < n; k++) {
                        acumulado += probs[k];
                        if (r <= acumulado) {
                            tree.search(elementos.get(k));
                            break;
                        }
                    }
                }
                long fim = System.nanoTime();
                somaZipf += (fim - inicio);
            }

            long mediaPresente = somaPresente / 15;
            long mediaAusente = somaAusente / 15;
            long mediaUniforme = somaUniforme / 15;
            long mediaScan = somaScan / 15;
            long mediaZipf = somaZipf / 15;

            gravarResultado("resultadosTestes/buscaPresenteSplay.txt", mediaPresente);
            gravarResultado("resultadosTestes/buscaNaoPresenteSplay.txt", mediaAusente);
            gravarResultado("resultadosTestes/buscaUniformeSplay.txt", mediaUniforme);
            gravarResultado("resultadosTestes/buscaScanSplay.txt", mediaScan);
            gravarResultado("resultadosTestes/buscaZipfSplay.txt", mediaZipf);

            System.out.println("Carga " + carga + " concluída.");
        }
    }

    private static void gravarResultado(String arquivo, long valor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(Long.toString(valor));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    private static ArrayList<Integer> lerArquivo(String caminho, int limite) {
        ArrayList<Integer> elementos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int contador = 0;
            while ((linha = reader.readLine()) != null && contador < limite) {
                elementos.add(Integer.parseInt(linha));
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return elementos;
    }
}
