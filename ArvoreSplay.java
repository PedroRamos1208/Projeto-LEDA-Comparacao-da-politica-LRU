public class ArvoreSplay{
	private Node root;
	private int size;
	private final int CARGA_MAX;

	public ArvoreSplay(int cargaMaxCache){
		this.CARGA_MAX = cargaMaxCache;
	}

	public boolean isEmpty(){
		return this.root == null;
	}

	public boolean isFull(){
		return this.size >= this.CARGA_MAX;
	}

	public void add(int v){
		if(isFull()) remove();

		Node node = search(v);
		if(node == null) add(new Node(v));
	}
	private void add(Node newNode){
		if(isEmpty()){
			this.root = newNode;
			this.size++;
			return;
		}

		Node aux = this.root;
		while(aux != null){
			if(newNode.value < aux.value){
				if(aux.left == null){
					aux.left = newNode;
					newNode.parent = aux;
					moveToRoot(newNode);
					this.size++;
					return;
				}else aux = aux.left;
			}else{
				if(aux.right == null){
					aux.right = newNode;
					newNode.parent = aux;
					moveToRoot(newNode);
					this.size++;
					return;
				}else aux = aux.right;
			}
		}
	}

	public Node search(int v){
		if(isEmpty()) return null;
		else return search(this.root, v);
	}
	private Node search(Node node, int v){
		if(node == null) return null;

		if(v == node.value){
			moveToRoot(node);
		       	return node;
		}
		else if(v < node.value) return search(node.left,v);
		else return search(node.right,v);
	}

	 public Node remove(){
		Node lru = min();
		if(lru == null) return null;

		moveToRoot(lru);

		Node leftSub = this.root.left;
		Node rightSub = this.root.right;

		if (leftSub != null) leftSub.parent = null;
    		if (rightSub != null) rightSub.parent = null;

		Node removed = this.root;
    		
		if (leftSub == null) this.root = rightSub;
		else if(rightSub == null) this.root = leftSub;
	        else{       
        		Node maxLeft = max(leftSub);
			moveToRoot(maxLeft);
        	     	this.root.right = rightSub;
        		rightSub.parent = this.root;
    		}
		this.size--;
		return removed;
        }

	public Node min(){
                if(isEmpty()) return null;
                else return min(this.root);
        }
        private Node min(Node node){
                if(node.left == null) return node;
                else return min(node.left);
        }

	public Node max(){
		if(isEmpty()) return null;
		else return max(this.root);
	}
	private Node max(Node node){
		if(node.right == null) return node;
		else return max(node.right);
	}

	public void moveToRoot(Node node){
		if(node == null || node == this.root) return;
			
		while(node != this.root){
			if (zig(node)) {
            			if (node.parent.right == node) {
                			rot_esq(node.parent);
            			}else {
                			rot_dir(node.parent);
            			}
        		}else if (zigZigDir(node)) {
            			rot_dir(node.parent.parent);
            			rot_dir(node.parent);
        		}else if (zigZigEsq(node)) {
            			rot_esq(node.parent.parent);
            			rot_esq(node.parent);
        		}else if (zigZagDir(node)) {
            			rot_esq(node.parent);
            			rot_dir(node.parent);
        		}else if (zigZagEsq(node)) {
            			rot_dir(node.parent);
            			rot_esq(node.parent);
        		}else {
            		break;
       		 	}
		}
	}

	private boolean zig(Node node){
		return node.parent.parent == null;
	}

	private boolean zigZigDir(Node node){
		return node.parent.parent.left == node.parent && node.parent.left == node;
	}
	
	private boolean zigZigEsq(Node node){
		return node.parent.parent.right == node.parent && node.parent.right == node;
	}

	private boolean zigZagEsq(Node node){
		return node.parent.parent.right == node.parent && node.parent.left == node;
	}

	private boolean zigZagDir(Node node){
		return node.parent.parent.left == node.parent && node.parent.right == node;
	}

	public Node rot_esq(Node node){
		Node y = node.right;
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

	private static class Node{
        	int value;
        	Node left;
        	Node right;
        	Node parent;

        	public Node(int v){
               		this.value = v;
        	}
	}
}
