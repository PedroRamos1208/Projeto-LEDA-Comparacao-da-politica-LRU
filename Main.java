import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main{
	public static void main(String[] args){
		int[] cargas = {1000,35000,70000,100000,350000,700000,1000000,2500000,3500000,5000000};
		for(int i = 0; i < cargas.length; i++){
			ArvoreSplay tree = new ArvoreSplay(cargas[i]);
			int limite = cargas[i];

        		try (BufferedReader reader = new BufferedReader(new FileReader("cargas/saida.txt"))) {
            			String linha;
            			int contador = 0;

            			while ((linha = reader.readLine()) != null && contador < limite) {
                			int numero = Integer.parseInt(linha);
					insercaoCacheNaoCheio(tree, numero);
                			contador++;
            			}
        		} catch (IOException e) {
            			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        		}

        		System.out.println("Total lido");
		}
	}

	public static void insercaoCacheNaoCheio(ArvoreSplay tree, int v){
		long soma = 0;
		
		for(int i = 0; i < 15; i++){
			long inicio = System.nanoTime();
			tree.add(v);
			long fim = System.nanoTime();
			soma += (fim-inicio);
		}

		long media = soma/15;

		try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultadosTestes/insercaoSimples.txt", true))) {
        		writer.write(Long.toString(media));
        		writer.newLine();
    		} catch (IOException e) {
        		System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
    		}
	}
	
	public static void insercaoCacheCheio(){
	
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
