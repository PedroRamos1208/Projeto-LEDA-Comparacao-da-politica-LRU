/**
 * Implementação de uma tabela hash especializado para o LRU Cache.
 * Esse HashMap usa encadeamento separado com uma classe interna 'MapEntry' para resolver colisões.
 */
public class HashMap {

    private MapEntry[] table;
    private int capacity;
    private int size;

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Construtor padrão com capacidade inicial de 16.
     */
    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construtor do Hash Map
     * @param initialCapacity A capacidade inicial da tabela hash.
     */
    public HashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new MapEntry[this.capacity];
        this.size = 0;
    }

    /**
     * Calcula o índice do balde para uma determinada chave usando hashing multiplicativo.
     * @param key A chave a ser mapeada.
     * @return O índice no array de baldes.
     */
    private int hash(int key) {
        int hashCode = key;
        hashCode *= 31; // Multiplicação por primo
        hashCode = hashCode ^ (hashCode >>> 16); // Táica para misturar bits, garantindo maior espalhamento

        // Tática para garantir que seja positivo
        return (hashCode & 0x7FFFFFFF) % table.length;
    }

    /**
     * Associa um valor (nó da Linked List) a uma chave.
     * Caso a chave já existir, o valor do nó é atualizado.
     * @param key A chave para associar.
     * @param value O nó da LinkedList a ser associado a chave.
     */
    void put(int key, LinkedList.Node value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int bucketIndex = hash(key);
        MapEntry head = table[bucketIndex];

        for (MapEntry current = head; current != null; current = current.next) {
            if (current.key == key) {
                current.value = value;
                return;
            }
        }

        size++;
        MapEntry newEntry = new MapEntry(key, value);
        newEntry.next = head;
        table[bucketIndex] = newEntry;
    }

    /**
     * Retorna nó da Linked List associado a uma chave.
     * @param key A chave a ser buscada.
     * @return O nó associado ou null se a chave não for encontrada.
     */
    LinkedList.Node get(int key) {
        int bucketIndex = hash(key);
        for (MapEntry current = table[bucketIndex]; current != null; current = current.next) {
            if (current.key == key) {
                return current.value;
            }
        }
        return null;
    }

    /**
     * Remove a entrada associada a uma chave.
     * @param key A chave da entrada a ser removida.
     */
    public void remove(int key) {
        int bucketIndex = hash(key);
        MapEntry head = table[bucketIndex];
        MapEntry prev = null;

        for (MapEntry current = head; current != null; prev = current, current = current.next) {
            if (current.key == key) {
                size--;
                if (prev == null) {
                    table[bucketIndex] = current.next;
                } else {
                    prev.next = current.next;
                }
                return;
            }
        }
    }
    
    /**
     * Dobra capacidade da tabela e redistribui todas as entradas.
     */
    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2;
        MapEntry[] oldBuckets = table;
        
        this.table = new MapEntry[capacity];
        this.size = 0;

        // Recalcula o hash de todos os elementos e os redistribui
        for (int i = 0; i < oldCapacity; i++) {
            for (MapEntry current = oldBuckets[i]; current != null; current = current.next) {
                put(current.key, current.value);
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

     /**
     * Representa uma entrada (par chave-valor) dentro de um balde do HashMap.
     * Funciona como um nó de uma lista simplesmente encadeada para o encadeamento
     */
    private static class MapEntry {
        final int key;
        LinkedList.Node value;   // O valor é uma referência direta ao nó da Linked List
        MapEntry next;           // Para o caso de colisão, aponta para a próxima entrada no mesmo balde

        public MapEntry(int key, LinkedList.Node value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // --- MÉTODO MAIN PARA TESTES UNITÁRIOS ---
    public static void main(String[] args) {
        System.out.println("--- Testando a CustomHashMap ---");
        // Usamos uma capacidade pequena (4) e 3 nós dummy para o valor
        // O Fator de Carga é 0.75, então o resize ocorrerá ao adicionar o 4º elemento (4 * 0.75 = 3)
        HashMap map = new HashMap(4);
        LinkedList.Node dummyNode1 = new LinkedList.Node(0, 1);
        LinkedList.Node dummyNode2 = new LinkedList.Node(0, 2);
        LinkedList.Node dummyNode3 = new LinkedList.Node(0, 3);
        LinkedList.Node dummyNode4 = new LinkedList.Node(0, 4);

        System.out.println("Adicionando 3 elementos: 1, 2, 3");
        map.put(1, dummyNode1);
        map.put(2, dummyNode2);
        map.put(3, dummyNode3);
        System.out.println("Tamanho atual: " + map.size()); // Esperado: 3

        System.out.println("\nBuscando valor da chave 2: " + map.get(2).value); // Esperado: 2

        System.out.println("\nAdicionando elemento 4, o que deve disparar o RESIZE...");
        map.put(4, dummyNode4); // Deve imprimir a mensagem de resize
        System.out.println("Tamanho atual: " + map.size()); // Esperado: 4

        System.out.println("\nVerificando se todos os elementos ainda existem após o resize:");
        System.out.println("Valor da chave 1: " + map.get(1).value); // Esperado: 1
        System.out.println("Valor da chave 2: " + map.get(2).value); // Esperado: 2
        System.out.println("Valor da chave 3: " + map.get(3).value); // Esperado: 3
        System.out.println("Valor da chave 4: " + map.get(4).value); // Esperado: 4

        System.out.println("\nTestando colisão (chaves 5 e 21 devem colidir com capacidade 16 após resize):");
        // O resize dobrou a capacidade de 4 para 8, depois para 16...
        // Para forçar uma colisão, precisamos de chaves que resultem no mesmo hash
        // Com capacidade 8, (5 % 8 = 5) e (13 % 8 = 5).
        map.put(5, dummyNode1);
        map.put(13, dummyNode2);
        System.out.println("Tamanho atual: " + map.size()); // Esperado: 6
        System.out.println("Valor da chave 5: " + map.get(5).value); // Esperado: 1
        System.out.println("Valor da chave 13: " + map.get(13).value); // Esperado: 2

        System.out.println("\nRemovendo a chave 2...");
        map.remove(2);
        System.out.println("Tamanho atual: " + map.size()); // Esperado: 5
        System.out.println("Buscando a chave 2 novamente: " + map.get(2)); // Esperado: null
    }
}