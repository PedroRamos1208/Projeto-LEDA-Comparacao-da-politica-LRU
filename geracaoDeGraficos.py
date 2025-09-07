import matplotlib.pyplot as plt
import numpy as np
import sys
from io import BytesIO

def plot_cache_lines(title, cargas, lru_linked_list_data, splay_tree_data):
    lru_time = lru_linked_list_data['time_ms']
    lru_hits = lru_linked_list_data['hit_rate']
    splay_time = splay_tree_data['time_ms']
    splay_hits = splay_tree_data['hit_rate']
    x = np.arange(len(cargas))

    fig, ax1 = plt.subplots(figsize=(12, 7))
    fig.suptitle(title, fontsize=16)

    line1, = ax1.plot(x, lru_hits, marker='o', color='darkblue', label='Hit LinkedList+HashMap')
    line2, = ax1.plot(x, splay_hits, marker='o', color='darkorange', label='Hit Splay')
    ax1.set_xlabel('Carga')
    ax1.set_ylabel('Taxa de Acerto (%)')
    ax1.set_xticks(x)
    ax1.set_xticklabels([str(c) for c in cargas])
    ax1.set_ylim(0, 105)

    ax2 = ax1.twinx()
    line3, = ax2.plot(x, lru_time, marker='x', color='blue', linestyle='--', label='Tempo LinkedList+HashMap')
    line4, = ax2.plot(x, splay_time, marker='x', color='orange', linestyle='--', label='Tempo Splay')
    ax2.set_ylabel('Tempo (ms)')
    ax2.set_yscale('log')

    fig.tight_layout()
    handles = [line1, line2, line3, line4]
    ax1.legend(handles, [h.get_label() for h in handles], loc='upper left')

    buf = BytesIO()
    plt.savefig(buf, format="pdf")
    plt.close(fig)

    sys.stdout.buffer.write(buf.getvalue())
