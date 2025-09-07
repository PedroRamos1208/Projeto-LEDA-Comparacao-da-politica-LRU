/**
 * Implementação de uma Double Linked List especializada para ser usada
 * em um cache com política LRU.
 *
 * A lista usa head e tail para simplificar operações
 * de inserção e remoção. Quanto mais próximo de head (MRU), mais frequêntemente usados
 * são os itens, já os mais proximos de tail (LRU) são os itens menos 
 * frequentemente usados.
 */
public class LinkedList {

    /**
     * Classe aninhada que representa um nó na lista.
     */
    static class Node {
        int key;
        int value;
        Node next;
        Node prev;

        /**
         * Construtor do nó.
         * @param k A chave do elemento.
         * @param v O valor do elemento.
         */
        public Node(int k, int v) {
            this.key = k;
            this.value = v;
        }
    }

    // Nós para marcar o início e o fim da lista.
    private final Node head;
    private final Node tail;
    private int size;

    /**
     * Construtor da lista ligada.
     */
    public LinkedList() {
        head = new Node(0, 0); 
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /**
     * Adiciona um nó no início da lista, tornando-o o MRU.
     * @param node O nó a ser adicionado.
     */
    void addFirst(Node node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
        size++;
    }

    /**
     * Remove um nó específico da lista.
     * @param node O nó a ser removido.
     */
    void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    /**
     * Remove e retorna o LRU.
     * Utilizado pelo cache quando precisa de espaço.
     * @return O nó removido, ou null caso a lista esteja vazia.
     */
    Node removeLast() {
        if (tail.prev == head) {
            return null;
        }
        Node lruNode = tail.prev;
        remove(lruNode); 
        return lruNode;
    }

    /**
     * Move um nó da lista para o início, tornando-o o MRU.
     * @param node O nó a ser movido.
     */
    void moveToFront(Node node) {
        if (node == head.next) { 
            return;
        }
        remove(node);
        addFirst(node);
    }

    /**
     * Retorna o número de elementos na lista.
     * @return O tamanho da lista.
     */
    public int size() {
        return size;
    }

    /**
     * Retorna uma representação em String da lista.
     * @return A String formatada.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = head.next;
        while (current != tail) { 
            sb.append("(").append(current.key).append(":").append(current.value).append(") ");
            current = current.next;
        }
        return sb.toString();
    }

    // --- MÉTODO MAIN PARA TESTES UNITÁRIOS ---
    public static void main(String[] args) {
        System.out.println("--- Testando a LinkedList ---");
        LinkedList list = new LinkedList();
        
        System.out.println("Adicionando 3 nós: (1,10), (2,20), (3,30)");
        Node node1 = new Node(1, 10);
        Node node2 = new Node(2, 20);
        Node node3 = new Node(3, 30);
        
        list.addFirst(node1); // Lista: (1:10)
        list.addFirst(node2); // Lista: (2:20) (1:10)
        list.addFirst(node3); // Lista: (3:30) (2:20) (1:10)
        
        System.out.println("Estado atual: " + list);
        System.out.println("Tamanho: " + list.size()); // Esperado: 3

        System.out.println("\nMovendo o nó (2,20) para o início...");
        list.moveToFront(node2);
        System.out.println("Estado atual: " + list); // Esperado: (2:20) (3:30) (1:10)
        
        System.out.println("\nRemovendo o último nó...");
        Node removed = list.removeLast();
        System.out.println("Nó removido: (" + removed.key + ":" + removed.value + ")"); // Esperado: (1:10)
        System.out.println("Estado atual: " + list); // Esperado: (2:20) (3:30)
        System.out.println("Tamanho: " + list.size()); // Esperado: 2
        
        System.out.println("\nRemovendo o nó (3,30) especificamente...");
        list.remove(node3);
        System.out.println("Estado atual: " + list); // Esperado: (2:20)
        System.out.println("Tamanho: " + list.size()); // Esperado: 1
    }
}