public class LinkedList {
    static class Node {
        int key;
        int value;
        Node next;
        Node prev;

        public Node(int k, int v) {
            this.key = k;
            this.value = v;
        }
    }

    private final Node head;
    private final Node tail;
    private int size;

    public LinkedList() {
        head = new Node(0, 0); 
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    void addFirst(Node node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
        size++;
    }

    void remove(Node node) {
	if(node == null || node == head || node == tail) return;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.next = null;
	node.prev = null;
	size--;
    }

    Node removeLast() {
        if (tail.prev == head) {
            return null;
        }
        Node lruNode = tail.prev;
        remove(lruNode); 
        return lruNode;
    }

    void moveToFront(Node node) {
        if (node == head.next) { 
            return;
        }
        remove(node);
        addFirst(node);
    }

    public int size() {
        return size;
    }


}
