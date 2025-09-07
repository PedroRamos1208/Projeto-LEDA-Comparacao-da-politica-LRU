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
        executarTesteInsercaoRepetidos(cargasLimitadas);
        executarTestesDeBusca(cargasLimitadas);
    }

    private static void executarTesteInsercaoCacheNaoCheio(int[] cargas) {
        for (int carga : cargas) {
            long soma = 0;
            ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

            for (int j = 0; j < NUM_REPETICOES; j++) {
                LRUSplay tree = new LRUSplay(carga);
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
                LRUSplay tree = new LRUSplay(capacidadeCache);
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

    private static void executarTesteInsercaoRepetidos(int[] cargas) {
    Random rand = new Random();

    for (int carga : cargas) {
        long soma = 0;
        ArrayList<Integer> elementos = lerArquivo("cargas/saida.txt", carga);

        if (elementos.isEmpty()) continue;

        int qtdInicial = (int) (carga * 0.9);  
        int qtdRepetidos = carga - qtdInicial; 

        for (int j = 0; j < NUM_REPETICOES; j++) {
            LRUSplay tree = new LRUSplay(carga);
            long inicio = System.nanoTime();

            for (int i = 0; i < qtdInicial; i++) {
                tree.add(elementos.get(i));
            }

            for (int r = 0; r < qtdRepetidos; r++) {
                int repetido = elementos.get(rand.nextInt(qtdInicial));
                tree.add(repetido);
            }

            long fim = System.nanoTime();
            soma += (fim - inicio);
        }

        long media = soma / NUM_REPETICOES;
        gravarResultado("resultadosTestes/insercaoRepetidosSplay.txt", media);
        System.out.println("Carga " + carga + " (inserção com repetidos) concluída.");
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

        int n = elementos.size();
        double[] probs = calcularProbabilidadesZipf(n, 1.0);

        for (int j = 0; j < NUM_REPETICOES; j++) {
            LRUSplay tree = new LRUSplay(carga);
            for (int numero : elementos) tree.add(numero);

            long inicio = System.nanoTime();
            tree.get(primeiroNum);
            long fim = System.nanoTime();
            somaPresente += (fim - inicio);
        }

        for (int j = 0; j < NUM_REPETICOES; j++) {
            LRUSplay tree = new LRUSplay(carga);
            for (int numero : elementos) tree.add(numero);

            long inicio = System.nanoTime();
            tree.get(numInexistente);
            long fim = System.nanoTime();
            somaAusente += (fim - inicio);
        }

        for (int j = 0; j < NUM_REPETICOES; j++) {
            LRUSplay tree = new LRUSplay(carga);
            for (int numero : elementos) tree.add(numero);

            int elementoAleatorio = elementos.get(rand.nextInt(elementos.size()));
            long inicio = System.nanoTime();
            tree.get(elementoAleatorio);
            long fim = System.nanoTime();
            somaUniforme += (fim - inicio);
        }

        for (int j = 0; j < NUM_REPETICOES; j++) {
            LRUSplay tree = new LRUSplay(carga);
            for (int numero : elementos) tree.add(numero);

            long inicio = System.nanoTime();
            for (int numero : elementos) tree.get(numero);
            long fim = System.nanoTime();
            somaScan += (fim - inicio);
        }

        for (int j = 0; j < NUM_REPETICOES; j++) {
            LRUSplay tree = new LRUSplay(carga);
            for (int numero : elementos) tree.add(numero);

            long inicio = System.nanoTime();
            for (int acesso = 0; acesso < n; acesso++) {
                double r = rand.nextDouble();
                double acumulado = 0.0;
                for (int k = 0; k < n; k++) {
                    acumulado += probs[k];
                    if (r <= acumulado) {
                        tree.get(elementos.get(k));
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
