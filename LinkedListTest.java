public class LinkedListTest {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES: LinkedList ---");
        LinkedList list = new LinkedList();
        LinkedList.Node node1 = new LinkedList.Node(10, 1);
        LinkedList.Node node2 = new LinkedList.Node(20, 2);
        LinkedList.Node node3 = new LinkedList.Node(30, 3);

        // Cenário 1: Inserção
        System.out.println("\n[Cenário 1: Inserção]");
        list.addFirst(node1);
        list.addFirst(node2);
        list.addFirst(node3);
        System.out.println("-> Resultado: " + list + "(Esperado: (30:3) (20:2) (10:1))");
        
        // Cenário 2: Movimentação
        System.out.println("\n[Cenário 2: Movimentação]");
        list.moveToFront(node1);
        System.out.println("-> Resultado: " + list + "(Esperado: (10:1) (30:3) (20:2))");
        
        // Cenário 3: Remoção
        System.out.println("\n[Cenário 3: Remoção]");
        LinkedList.Node removedNode = list.removeLast(); // Remove o (20,2)
        list.remove(node3);                              // Remove o (30,3)
        System.out.println("-> Nó removido por último: (" + removedNode.key + ":" + removedNode.value + ") (Esperado: 20:2)");
        System.out.println("-> Resultado: " + list + "(Esperado: (10:1))");

        System.out.println("\n--- TESTES FINALIZADOS: LinkedList ---");
    }
}