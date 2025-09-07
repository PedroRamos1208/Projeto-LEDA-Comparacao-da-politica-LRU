public class ArvoreSplay {
    Node root;
    private int size;

    public boolean isEmpty() {
        return root == null;
    }

    public Node add(int v, long timeStamp) {
        Node newNode = new Node(v, timeStamp);
        if (isEmpty()) {
            root = newNode;
            size++;
            return newNode;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;
            if (v < current.value) current = current.left;
            else current = current.right;
        }

        newNode.parent = parent;
        if (v < parent.value) parent.left = newNode;
        else parent.right = newNode;

        moveToRoot(newNode);
        size++;
        return newNode;
    }

    public Node remove(Node node) {
        if (node == null) return null;

        moveToRoot(node);

        Node leftSub = node.left;
        Node rightSub = node.right;

        if (leftSub != null) leftSub.parent = null;
        if (rightSub != null) rightSub.parent = null;

        if (leftSub == null) {
            root = rightSub;
        } else {
            Node maxLeft = max(leftSub);
            if (maxLeft != null) {
                moveToRoot(maxLeft);
                root.right = rightSub;
                if (rightSub != null) rightSub.parent = root;
            }
        }

        node.left = null;
        node.right = null;
        node.parent = null;

        size--;
        return node;
    }

    public Node max(Node node) {
        if (node == null) return null;
        while (node.right != null) node = node.right;
        return node;
    }

    public Node max() {
        return max(root);
    }

    public void moveToRoot(Node node) {
        if (node == null || node == root) return;

        while (node.parent != null) {
            Node p = node.parent;
            Node g = p.parent;

            if (g == null) { 
                if (p.left == node) rotDir(p);
                else if (p.right == node) rotEsq(p);
            } else if (g.left == p && p.left == node) {
                rotDir(g);
                rotDir(p);
            } else if (g.right == p && p.right == node) { 
                rotEsq(g);
                rotEsq(p);
            } else if (g.left == p && p.right == node) { 
                rotEsq(p);
                rotDir(g);
            } else if (g.right == p && p.left == node) { 
                rotDir(p);
                rotEsq(g);
            } else {
                break;
            }
        }
    }

    private void rotEsq(Node x) {
        if (x == null || x.right == null) return;
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;

        y.left = x;
        y.parent = x.parent;

        if (x.parent == null) root = y;
        else if (x.parent.left == x) x.parent.left = y;
        else x.parent.right = y;

        x.parent = y;
    }

    private void rotDir(Node x) {
        if (x == null || x.left == null) return;
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;

        y.right = x;
        y.parent = x.parent;

        if (x.parent == null) root = y;
        else if (x.parent.left == x) x.parent.left = y;
        else x.parent.right = y;

        x.parent = y;
    }

    public int size() {
        return size;
    }

    class Node {
        long timeStamp;
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int v, long timeStamp) {
            this.value = v;
            this.timeStamp = timeStamp;
        }
    }
}
