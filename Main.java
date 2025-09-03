import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main{
	public static void main(String[] args){
		int[] cargasLimitadas = {100,3500,7000,10000,35000,70000,100000,250000,350000,500000};
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
                        	System.out.println("Total lido");
			}
			long media = soma/15;
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultadosTestes/insercaoCacheNaoCheioSplay.txt", true))) {
                        	writer.write(Long.toString(media));
                        	writer.newLine();
                	} catch (IOException e) {
                        	System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                	}
                }

		int[] cargas = {1000,35000,70000,100000,350000,700000,1000000,2500000,3500000,5000000};
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
                                System.out.println("Total lido");
                        }
                        long media = soma/15;
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultadosTestes/insercaoCacheCheioSplay.txt", true))) {
                                writer.write(Long.toString(media));
                                writer.newLine();
                        } catch (IOException e) {
                                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
			}
		}
				
	}
	
	public static void searchPresente(){
	
	}

	public static void searchNaoPresente(){
	
	}

	public static void searchUniforme(){
	
	}

	public static void searchScanSequencial(){
	
	}

	public static void zipf(){
	
	}
}
