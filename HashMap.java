public class HashMap {

    private MapEntry[] table;
    private int capacity;
    private int size;

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new MapEntry[this.capacity];
        this.size = 0;
    }

    private int hash(int key) {
        int hashCode = key;
        hashCode *= 31; 
        hashCode = hashCode ^ (hashCode >>> 16);

        return (hashCode & 0x7FFFFFFF) % table.length;
    }

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

    LinkedList.Node get(int key) {
        int bucketIndex = hash(key);
        for (MapEntry current = table[bucketIndex]; current != null; current = current.next) {
            if (current.key == key) {
                return current.value;
            }
        }
        return null;
    }

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
		current.next = null;
		current.value = null;
                return;
            }
        }
    }
    
    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2;
        MapEntry[] oldBuckets = table;
        
        this.table = new MapEntry[capacity];
        this.size = 0;

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

    private static class MapEntry {
        final int key;
        LinkedList.Node value; 
        MapEntry next;         

        public MapEntry(int key, LinkedList.Node value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}

