public class LRUCache {

    private final HashMap cacheMap;
    private final LinkedList lruList;
    private final int capacity;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("A capacidade do cache deve ser maior que zero.");
        }
        this.capacity = capacity;
        this.cacheMap = new HashMap(capacity);
        this.lruList = new LinkedList();
    }

    public int get(int key) {
        LinkedList.Node node = cacheMap.get(key);
        if (node == null) {
            return -1;
        }
        lruList.moveToFront(node); 
        return node.value;
    }

    public void put(int key, int value) {
        LinkedList.Node existingNode = cacheMap.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            lruList.moveToFront(existingNode);
        } else {
            if (lruList.size() >= capacity) {
                LinkedList.Node lruNode = lruList.removeLast();
		if(lruNode != null) cacheMap.remove(lruNode.key);
            }
            LinkedList.Node newNode = new LinkedList.Node(key, value);
            lruList.addFirst(newNode);
            cacheMap.put(key, newNode);
        }
    }

    public int size() {
        return lruList.size();
    }

    public int getCapacity() {
        return capacity;
    }

}
