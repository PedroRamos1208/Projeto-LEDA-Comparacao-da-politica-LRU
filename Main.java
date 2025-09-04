import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main{
	public static void main(String[] args){
		int[] cargasLimitadas = {100,3500,7000,10000,35000,70000,100000,250000,350000,500000};
                int[] cargas = {1000,35000,70000,100000,350000,700000,1000000,2500000,3500000,5000000};
		
		for(int i = 0; i < cargasLimitadas.length; i++){
			int limite = cargasLimitadas[i];
			long soma = 0;

			for(int j = 0; j < 15; j++){
				ArvoreSplay tree = new ArvoreSplay(cargasLimitadas[i]);
                        	try (BufferedReader reader = new BufferedReader(new FileReader("cargas/saida.txt"))) {
                                	String linha;
                                	int contador = 0;
					
					long inicio = System.nanoTime();
                                	while ((linha = reader.readLine()) != null && contador < limite) {
                                        	int numero = Integer.parseInt(linha);
                                        	tree.add(numero);
                                        	contador++;
                                	}
					long fim = System.nanoTime();
					soma += (fim-inicio);
                        	} catch (IOException e) {
                                	System.out.println("Erro ao ler o arquivo: " + e.getMessage());
                        	}
			}
			long media = soma/15;
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultadosTestes/insercaoCacheNaoCheioSplay.txt", true))) {
                        	writer.write(Long.toString(media));
                        	writer.newLine();
                	} catch (IOException e) {
                        	System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                	}
                }

		for(int i = 0; i < cargas.length; i++){
			int capacidadeCache = (int) (cargas[i]*0.1);
			int limite = cargas[i];
			long soma = 0;

			 for(int j = 0; j < 15; j++){
                                ArvoreSplay tree = new ArvoreSplay(capacidadeCache);
                                try (BufferedReader reader = new BufferedReader(new FileReader("cargas/saida.txt"))) {
                                        String linha;
                                        int contador = 0;

                                        long inicio = System.nanoTime();
                                        while ((linha = reader.readLine()) != null && contador < limite) {
                                                int numero = Integer.parseInt(linha);
                                                tree.add(numero);
                                                contador++;
                                        }
                                        long fim = System.nanoTime();
                                        soma += (fim-inicio);
                                } catch (IOException e) {
                                        System.out.println("Erro ao ler o arquivo: " + e.getMessage());
                                }
                        }
                        long media = soma/15;
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultadosTestes/insercaoCacheCheioSplay.txt", true))) {
                                writer.write(Long.toString(media));
                                writer.newLine();
                        } catch (IOException e) {
                                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
			}
		}

		for(int i = 0; i < cargasLimitadas.length; i++){
                        int limite = cargasLimitadas[i];
                        long somaPresente = 0;
			long somaAusente = 0;
			long somaUniforme = 0;
			long somaScan = 0;
			long somaZipf = 0;

                        ArvoreSplay tree = new ArvoreSplay(cargasLimitadas[i]);
                        ArrayList<Integer> elementos = new ArrayList<>();
			int ultimoNum = 0;
                        int numInexistente = -1;

			try (BufferedReader reader = new BufferedReader(new FileReader("cargas/saida.txt"))) {
                        	String linha;
                                int contador = 0;

                                while ((linha = reader.readLine()) != null && contador < limite) {
                                	int numero = Integer.parseInt(linha);
                                        tree.add(numero);
					elementos.add(numero);
                                        contador++;
					ultimoNum = numero;
                                }
			}catch (IOException e) {
                                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
                        }
					
			for(int j = 0; j < 15; j++){
				long inicio = System.nanoTime();
				tree.search(ultimoNum);
				long fim = System.nanoTime();
                                somaPresente += (fim-inicio);
			}

			for(int j = 0; j < 15; j++){
                                long inicio = System.nanoTime();
                                tree.search(numInexistente);
                                long fim = System.nanoTime();
                                somaAusente += (fim-inicio);
                        }

			Random rand = new Random();
			for(int j = 0; i < 15; j++){
				int elementoAleatorio = elementos.get(rand.nextInt(elementos.size()));
				long inicio = System.nanoTime();
				tree.search(elementoAleatorio);
				long fim = System.nanoTime();
				somaUniforme += (fim-inicio);
			}

			for(int j = 0; j < 15; j++){
				long inicio = System.nanoTime();
				for(int numero: elementos)
				tree.search(numero);
				long fim = System.nanoTime();
				somaScan += (fim-inicio);
			}

			double s = 1.0;
			int n = elementos.size();
			double[] probs = new double[n];
			double somaP = 0.0;
			for(int k = 1; k <= n; k++){
				probs[k-1] = 1.0 / Math.pow(k,s);
				somaP += probs[k-1];
			}
			for(int k = 0; k < n; k++) probs[k] /= somaP;
			for(int j = 0; j < 15; j++){
				long inicio = System.nanoTime();
				for(int acesso = 0; acesso < n; acesso++){
					double r = rand.nextDouble();
					double acumulado = 0.0;
					for(int k = 0; k < n; k++){
						acumulado += probs[k];
						if(r <= acumulado){
							tree.search(elementos.get(k));
							break;
						}
					}
				}
				long fim = System.nanoTime();
				somaZipf += (fim-inicio);
			}	
                 
                        long mediaPresente = somaPresente/15;
			long mediaAusente = somaAusente/15;
			long mediaUniforme = somaUniforme/15;
			long mediaScan = somaScan/15;
			long mediaZipf = somaZipf/15;

			gravarResultado("resultadosTestes/buscaPresenteSplay.txt", mediaPresente);
            		gravarResultado("resultadosTestes/buscaNaoPresenteSplay.txt", mediaAusente);
            		gravarResultado("resultadosTestes/buscaUniformeSplay.txt", mediaUniforme);
            		gravarResultado("resultadosTestes/buscaScanSplay.txt", mediaScan);
            		gravarResultado("resultadosTestes/buscaZipfSplay.txt", mediaZipf);
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
}
