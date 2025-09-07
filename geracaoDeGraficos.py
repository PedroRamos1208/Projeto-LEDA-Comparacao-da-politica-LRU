import matplotlib.pyplot as plt
import numpy as np
import sys
from io import BytesIO

def plot_cache_lines(title, cargas, lru_linked_list_data, splay_tree_data):
    """
    Gera um gráfico comparativo entre duas implementações de cache: 
    LinkedList+HashMap vs Splay Tree
    """
    
    # Extrai os dados de tempo e taxa de acerto para cada implementação
    lru_time = lru_linked_list_data['time_ms']
    lru_hits = lru_linked_list_data['hit_rate']
    splay_time = splay_tree_data['time_ms']
    splay_hits = splay_tree_data['hit_rate']
    
    # Cria um array de índices para o eixo X baseado no número de cargas
    x = np.arange(len(cargas))
    
    # Cria a figura e o primeiro eixo com tamanho específico
    fig, ax1 = plt.subplots(figsize=(12, 7))
    fig.suptitle(title, fontsize=16)  # Define o título principal
    
    # Plota as taxas de acerto no primeiro eixo (ax1)
    line1, = ax1.plot(x, lru_hits, marker='o', color='darkblue', label='Hit LinkedList+HashMap')
    line2, = ax1.plot(x, splay_hits, marker='o', color='darkorange', label='Hit Splay')
    
    # Configura o primeiro eixo
    ax1.set_xlabel('Carga')  # Rótulo do eixo X
    ax1.set_ylabel('Taxa de Acerto (%)')  # Rótulo do eixo Y esquerdo
    ax1.set_xticks(x)  # Define as posições dos ticks no eixo X
    ax1.set_xticklabels([str(c) for c in cargas])  # Rótulos dos ticks (valores de carga)
    ax1.set_ylim(0, 105)  # Limite do eixo Y para porcentagem (0-100% com margem)
    
    # Cria um segundo eixo Y compartilhando o mesmo eixo X
    ax2 = ax1.twinx()
    
    # Plota os tempos de execução no segundo eixo (ax2) com escala logarítmica
    line3, = ax2.plot(x, lru_time, marker='x', color='blue', linestyle='--', label='Tempo LinkedList+HashMap')
    line4, = ax2.plot(x, splay_time, marker='x', color='orange', linestyle='--', label='Tempo Splay')
    
    # Configura o segundo eixo
    ax2.set_ylabel('Tempo (ms)')  # Rótulo do eixo Y direito
    ax2.set_yscale('log')  # Escala logarítmica para melhor visualização de grandes variações
    
    # Ajusta o layout para evitar sobreposição
    fig.tight_layout()
    
    # Cria a legenda combinando handles de ambos os eixos
    handles = [line1, line2, line3, line4]
    ax1.legend(handles, [h.get_label() for h in handles], loc='upper left')
    
    # Salva o gráfico em um buffer de memória no formato PDF
    buf = BytesIO()
    plt.savefig(buf, format="pdf")
    plt.close(fig)  # Fecha a figura para liberar memória
    
    # Escreve o conteúdo do PDF no buffer de saída padrão
    sys.stdout.buffer.write(buf.getvalue())
