/**
 * Implementação de um Cache com política de LRU utilizando uma tabela hash para acesso rápido O(1)
 * e uma LinkedList para manter a ordem de uso dos elementos. 
 */
public class LRUCache {

    private final HashMap cacheMap;
    private final LinkedList lruList;
    private final int capacity;

    /**
     * Construtor para o LRUCache.
     * @param capacity A capacidade máxima do cache.
     */
    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("A capacidade do cache deve ser maior que zero.");
        }
        this.capacity = capacity;
        this.cacheMap = new HashMap(capacity);
        this.lruList = new LinkedList();
    }

    /**
     * Retorna o valor associado à chave no cache.
     * Caso a chave seja encontrada o elemento é movido para o início da lista (MRU).
     * @param key A chave do elemento a ser buscado.
     * @return O valor associado à chave (um hit), ou -1 se a chave não for encontrada (um miss).
     */
    public int get(int key) {
        LinkedList.Node node = cacheMap.get(key);
        if (node == null) {
            return -1;
        }
        lruList.moveToFront(node); 
        return node.value;
    }

    /**
     * Se a chave não exite ainda, é criado e adicionado um novo nó no MRU.
     * Se a chave já existe, seu valor é atualizado e o elemento é movido para o MRU.
     * @param key A chave a ser adicionada/atualizada.
     * @param value O valor a ser associado à chave.
     */
    public void put(int key, int value) {
        LinkedList.Node existingNode = cacheMap.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            lruList.moveToFront(existingNode);
        } else {
            if (lruList.size() >= capacity) {
                LinkedList.Node lruNode = lruList.removeLast();
                cacheMap.remove(lruNode.key);
            }
            LinkedList.Node newNode = new LinkedList.Node(key, value);
            lruList.addFirst(newNode);
            cacheMap.put(key, newNode);
        }
    }

    /**
     * Mostra o número de elementos no cache.
     * @return O tamanho atual do cache.
     */
    public int size() {
        return lruList.size();
    }

    /**
     * Mostra a capacidade máxima do cache.
     * @return A capacidade máxima.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Imprime oconteúdo do cache.
    */
    public void printCache() {
    System.out.println("Cache (MRU -> LRU): " + lruList.toString());
    }

    // --- MÉTODO MAIN PARA TESTES DE INTEGRAÇÃO ---
    public static void main(String[] args) {
        System.out.println("--- Testando o LRUCache com capacidade 3 ---");
        LRUCache cache = new LRUCache(3);

        System.out.println("\nInserindo (1, 10), (2, 20), (3, 30)");
        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(3, 30);
        cache.printCache(); // Esperado: (3:30) (2:20) (1:10)

        System.out.println("\nBuscando pela chave 1 (get(1))");
        int valor = cache.get(1);
        System.out.println("Valor retornado: " + valor); // Esperado: 10
        System.out.println("A chave 1 agora deve ser a mais recente.");
        cache.printCache(); // Esperado: (1:10) (3:30) (2:20)

        System.out.println("\nInserindo (4, 40). O cache está cheio.");
        System.out.println("A chave 2 (LRU) deve ser removida.");
        cache.put(4, 40);
        cache.printCache(); // Esperado: (4:40) (1:10) (3:30)

        System.out.println("\nAtualizando o valor da chave 3 para 333 (put(3, 333))");
        cache.put(3, 333);
        System.out.println("A chave 3 agora deve ser a mais recente.");
        cache.printCache(); // Esperado: (3:333) (4:40) (1:10)
        
        System.out.println("\nBuscando por uma chave inexistente (get(99))");
        valor = cache.get(99);
        System.out.println("Valor retornado: " + valor); // Esperado: -1
        System.out.println("A ordem do cache não deve mudar.");
        cache.printCache(); // Esperado: (3:333) (4:40) (1:10)
    }
}