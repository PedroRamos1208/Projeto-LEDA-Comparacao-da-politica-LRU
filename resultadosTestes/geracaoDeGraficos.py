import matplotlib.pyplot as plt
import os

pasta = '.' 

cargas_limitadas = [100, 3500, 7000, 10000, 35000, 70000, 100000, 250000, 350000, 500000]
cargas_amplas = [1000, 35000, 70000, 100000, 350000, 700000, 1000000, 2500000, 3500000, 5000000]

testes_e_cargas = {
    'buscaNaoPresente': cargas_limitadas,
    'buscaPresente': cargas_limitadas,
    'buscaScan': cargas_limitadas,
    'buscaUniforme': cargas_limitadas,
    'buscaZipf': cargas_limitadas,
    'insercaoCacheNaoCheio': cargas_limitadas,
    
    'insercaoCacheCheio': cargas_amplas,
    'insercaoOrdenada': cargas_amplas,
    'insercaoRepetidos': cargas_amplas
}

def ler_arquivo(caminho):
    try:
        with open(caminho, 'r') as f:
            return [float(l.strip()) for l in f.readlines() if l.strip()]
    except FileNotFoundError:
        print(f"AVISO: O arquivo {caminho} não foi encontrado. Pulando este gráfico.")
        return None

for teste, cargas_atuais in testes_e_cargas.items():
    arquivo_linked = os.path.join(pasta, f'{teste}Linked.txt')
    arquivo_splay = os.path.join(pasta, f'{teste}Splay.txt')

    linked_data = ler_arquivo(arquivo_linked)
    splay_data = ler_arquivo(arquivo_splay)

    if linked_data is None or splay_data is None:
        continue
        
    if len(linked_data) != len(cargas_atuais) or len(splay_data) != len(cargas_atuais):
        print(f"AVISO para o teste '{teste}': O número de pontos de dados ({len(linked_data)}, {len(splay_data)}) "
              f"não corresponde ao número de cargas ({len(cargas_atuais)}). O gráfico pode ficar incorreto.")

    plt.figure(figsize=(10, 6))
    plt.plot(cargas_atuais, linked_data, marker='o', linestyle='-', label='LinkedList + HashMap')
    plt.plot(cargas_atuais, splay_data, marker='s', linestyle='-', label='Splay Tree')
    
    plt.xlabel('Carga (Número de Elementos)')
    plt.ylabel('Tempo Médio de Execução (ns)')
    plt.title(f'Desempenho: {teste}')
    plt.legend()
    plt.grid(True)
    
    plt.ticklabel_format(style='plain', axis='x') 
    plt.xticks(rotation=30)
    plt.tight_layout() 

    nome_arquivo = f"grafico_{teste}.png"
    plt.savefig(nome_arquivo)
    plt.close() 
    print(f"Gráfico salvo como: {nome_arquivo}")
