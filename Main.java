import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static final int NUM_REPETICOES = 15;

    public static void main(String[] args) {
        int[] cargasLimitadas = {100, 3500, 7000, 10000, 35000, 70000, 100000, 250000, 350000, 500000};
        int[] cargas = {1000, 35000, 70000, 100000, 350000, 700000, 1000000, 2500000, 3500000, 5000000};

        executarTesteInsercaoCacheNaoCheio(cargasLimitadas);
        executarTesteInsercaoCacheCheio(cargas);
        executarTestesDeBusca(cargasLimitadas);
    }

    private static void executarTesteInsercaoCacheNaoCheio(int[] cargas) {
        for (int carga : cargas) {
            long soma = 0;
            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

            for (int j = 0; j < NUM_REPETICOES; j++) {
                ArvoreSplay tree = new ArvoreSplay(carga);
                long inicio = System.nanoTime();
                for (int numero : elementos) {
                    tree.add(numero);
                }
                long fim = System.nanoTime();
                soma += (fim - inicio);
            }

            long media = soma / NUM_REPETICOES;
            gravarResultado("resultadosTestes/insercaoCacheNaoCheioSplay.txt", media);
            System.out.println("Carga " + carga + " concluída.");
        }
    }

    private static void executarTesteInsercaoCacheCheio(int[] cargas) {
        for (int carga : cargas) {
            int capacidadeCache = (int) (carga * 0.1);
            long soma = 0;
            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

            for (int j = 0; j < NUM_REPETICOES; j++) {
                ArvoreSplay tree = new ArvoreSplay(capacidadeCache);
                long inicio = System.nanoTime();
                for (int numero : elementos) {
                    tree.add(numero);
                }
                long fim = System.nanoTime();
                soma += (fim - inicio);
            }

            long media = soma / NUM_REPETICOES;
            gravarResultado("resultadosTestes/insercaoCacheCheioSplay.txt", media);
            System.out.println("Carga " + carga + " concluída.");
        }
    }

    private static void executarTestesDeBusca(int[] cargas) {
        for (int carga : cargas) {
            long somaPresente = 0, somaAusente = 0, somaUniforme = 0, somaScan = 0, somaZipf = 0;
            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

            if (elementos.isEmpty()) {
                System.out.println("Carga " + carga + " vazia. Pulando.");
                continue;
            }

            int primeiroNum = elementos.get(0);
            int numInexistente = -1;
            Random rand = new Random();

            ArvoreSplay tree = new ArvoreSplay(carga);
            for (int numero : elementos) {
                tree.add(numero);
            }

            for (int j = 0; j < NUM_REPETICOES; j++) {
                long inicio = System.nanoTime();
                tree.search(primeiroNum);
                long fim = System.nanoTime();
                somaPresente += (fim - inicio);
            }

            for (int j = 0; j < NUM_REPETICOES; j++) {
                long inicio = System.nanoTime();
                tree.search(numInexistente);
                long fim = System.nanoTime();
                somaAusente += (fim - inicio);
            }

            for (int j = 0; j < NUM_REPETICOES; j++) {
                int elementoAleatorio = elementos.get(rand.nextInt(elementos.size()));
                long inicio = System.nanoTime();
                tree.search(elementoAleatorio);
                long fim = System.nanoTime();
                somaUniforme += (fim - inicio);
            }

            for (int j = 0; j < NUM_REPETICOES; j++) {
                long inicio = System.nanoTime();
                for (int numero : elementos) {
                    tree.search(numero);
                }
                long fim = System.nanoTime();
                somaScan += (fim - inicio);
            }

            int n = elementos.size();
            double[] probs = calcularProbabilidadesZipf(n, 1.0);
            for (int j = 0; j < NUM_REPETICOES; j++) {
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

            gravarResultado("resultadosTestes/buscaPresenteSplay.txt", somaPresente / NUM_REPETICOES);
            gravarResultado("resultadosTestes/buscaNaoPresenteSplay.txt", somaAusente / NUM_REPETICOES);
            gravarResultado("resultadosTestes/buscaUniformeSplay.txt", somaUniforme / NUM_REPETICOES);
            gravarResultado("resultadosTestes/buscaScanSplay.txt", somaScan / NUM_REPETICOES);
            gravarResultado("resultadosTestes/buscaZipfSplay.txt", somaZipf / NUM_REPETICOES);
            
            System.out.println("Carga " + carga + " concluída.");
        }
    }

    private static double[] calcularProbabilidadesZipf(int n, double s) {
        double[] probs = new double[n];
        double somaP = 0.0;
        for (int k = 1; k <= n; k++) {
            probs[k - 1] = 1.0 / Math.pow(k, s);
            somaP += probs[k - 1];
        }
        for (int k = 0; k < n; k++) {
            probs[k] /= somaP;
        }
        return probs;
    }

    private static void gravarResultado(String arquivo, long valor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(Long.toString(valor));
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo " + arquivo + ": " + e.getMessage());
        }
    }

    private static ArrayList<Integer> lerArquivo(String caminho, int limite) {
        ArrayList<Integer> elementos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null && elementos.size() < limite) {
                elementos.add(Integer.parseInt(linha));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo " + caminho + ": " + e.getMessage());
        }
        return elementos;
    }
}
