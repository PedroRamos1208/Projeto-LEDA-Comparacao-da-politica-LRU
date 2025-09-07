public class ArvoreSplay{
	Node root;
	private int size;

	public boolean isEmpty(){
		return this.root == null;
	}

	public Node add(int v, long timeStamp){
		Node newNode = new Node(v, timeStamp);

		if(isEmpty()){
			this.root = newNode;
			this.size++;
			return newNode;
		}

		Node aux = this.root;
		while(true){
			if(v < aux.value){
				if(aux.left == null){
					aux.left = newNode;
					newNode.parent = aux;
					moveToRoot(newNode);
					this.size++;
					return newNode;
				}else aux = aux.left;
			}else{
				if(aux.right == null){
					aux.right = newNode;
					newNode.parent = aux;
					moveToRoot(newNode);
					this.size++;
					return newNode;
				}else aux = aux.right;
			}
		}
	}


	public Node remove(Node node){
    	if(node == null) return null;

    	moveToRoot(node);

    	Node leftSub = node.left;
    	Node rightSub = node.right;

    	if(leftSub != null) leftSub.parent = null;
    	if(rightSub != null) rightSub.parent = null;

    	if(leftSub == null){
        	root = rightSub;
    	} else {
        	Node maxLeft = max(leftSub);
        	if(maxLeft != null){
            	moveToRoot(maxLeft);
            	root.right = rightSub;
            	if(rightSub != null) rightSub.parent = root;
        	}
    	}

    	node.left = null;
    	node.right = null;
    	node.parent = null;

    	size--;
    	return node;
	}


	public Node max(){
		return max(this.root);
	}
	private Node max(Node node){
		if(node == null) return null;
		while(node.right != null) node = node.right;
		return node;
	}

	public void moveToRoot(Node node) {
    if (node == null || node == root) return;

    while (node != root && node.parent != null) {
        Node p = node.parent;
        Node g = p.parent;

        if (g == null) {
            if (p.left == node) rot_dir(p);
            else if (p.right == node) rot_esq(p);
        } else {
            if (g.left == p && p.left == node) {
                rot_dir(g);
                rot_dir(p);
            } else if (g.right == p && p.right == node) {
                rot_esq(g);
                rot_esq(p);
            } else if (g.left == p && p.right == node) {
                rot_esq(p);
                rot_dir(g);
            } else if (g.right == p && p.left == node) {
                rot_dir(p);
                rot_esq(g);
            } else {
                break;
            }
        }
    }
}

	public Node rot_esq(Node node){
		Node y = node.right;
		if(y == null) return node;
		Node ey = y.left;
		Node parent = node.parent;

		if(parent != null){
			if(parent.left == node) parent.left = y;
			else parent.right = y;
		}

		y.left = node;
		y.parent = parent;
		node.right = ey;
		node.parent = y;
		if(ey != null) ey.parent = node;

		if(y.parent == null) this.root = y;

		return y;
	}

	public Node rot_dir(Node node){
		Node y = node.left;
		if(y == null) return node;
		Node dy = y.right;
		Node parent = node.parent;

		if(parent != null){
			if(parent.left == node) parent.left = y;
			else parent.right = y;
		}

		y.right = node;
		y.parent = parent;
		node.left = dy;
		node.parent = y;
		if(dy != null) dy.parent = node;

		if(y.parent == null) this.root = y;

		return y;
	}

	public int size(){
		return this.size;
	}

class Node{
	long timeStamp;
        int value;
        Node left;
        Node right;
        Node parent;

        public Node(int v, long timeStamp){
               	this.value = v;
		this.timeStamp = timeStamp;
        }
}
}
