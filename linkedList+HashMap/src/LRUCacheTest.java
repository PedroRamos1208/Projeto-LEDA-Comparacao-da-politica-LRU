public class LRUCacheTest {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES: LRUCache ---");
        LRUCache cache = new LRUCache(3); // Capacidade máxima de 3 itens

        // Cenário 1: Preenchimento e Cache Hit
        cache.put(10, 100);
        cache.put(20, 200);
        cache.put(30, 300); 
        cache.get(10);
        System.out.println("-> Após encher e acessar a chave 10:");
        cache.printCache(); // Esperado: Cache (MRU -> LRU): (10:100) (30:300) (20:200) 
        
        // Cenário 2: Eviction (Remoção do LRU)
        cache.put(40, 400);
        System.out.println("-> Após adicionar (40,400) em cache cheio:");
        cache.printCache(); 
        System.out.println("-> Verificando se a chave 20 foi removida (get(20)): " + cache.get(20));
        
        // Cenário 3: Atualização de Valor
        cache.put(30, 999); // Atualiza a chave 30, que deve ir para o início
        cache.printCache(); // Esperado: Cache (MRU -> LRU): (30:999) (40:400) (10:100) 

        System.out.println("\n--- TESTES FINALIZADOS: LRUCache ---");
    }
}