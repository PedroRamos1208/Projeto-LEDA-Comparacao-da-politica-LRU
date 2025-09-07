public class HashMapTest {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES: HashMap ---");
        LinkedList.Node node1 = new LinkedList.Node(0, 100);
        LinkedList.Node node2 = new LinkedList.Node(0, 200);
        LinkedList.Node node3 = new LinkedList.Node(0, 300);

        // Cenário 1: Colisão
        System.out.println("\n[Cenário 1: Tratamento de Colisão]");
        HashMap collisionMap = new HashMap(8);
        collisionMap.put(5, node1);
        collisionMap.put(13, node2); // Colide com 5
        collisionMap.put(21, node3); // Colide com 5 e 13
        collisionMap.remove(13);      // Remove do meio da cadeia
        System.out.println("-> Após inserir 5, 13, 21 e remover 13:");
        System.out.println("   - Get 5: " + collisionMap.get(5).value + " (Esperado: 100)");
        System.out.println("   - Get 13: " + collisionMap.get(13) + " (Esperado: null)");
        System.out.println("   - Get 21: " + collisionMap.get(21).value + " (Esperado: 300)");
        
        // Cenário 2: Redimensionamento (Resize)
        System.out.println("\n[Cenário 2: Redimensionamento (Resize)]");
        HashMap resizeMap = new HashMap(2);
        resizeMap.put(10, node1);
        resizeMap.put(20, node2); // Dispara resize de 2->4
        resizeMap.put(30, node3); // Dispara resize de 4->8
        System.out.println("-> Após inserir 3 itens com resize no meio:");
        System.out.println("   - Tamanho: " + resizeMap.size() + " (Esperado: 3)");
        System.out.println("   - Get 10: " + resizeMap.get(10).value + " (Esperado: 100)");
        System.out.println("   - Get 20: " + resizeMap.get(20).value + " (Esperado: 200)");
        System.out.println("   - Get 30: " + resizeMap.get(30).value + " (Esperado: 300)");

        System.out.println("\n--- TESTES FINALIZADOS: HashMap ---");
    }
}