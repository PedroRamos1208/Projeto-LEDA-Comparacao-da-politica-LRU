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
		while(aux != null){
			if(newNode.value < aux.value){
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
		return null;
	}


	 public Node remove(Node node){
		moveToRoot(node);
		Node removed = this.root;

		Node leftSub = this.root.left;
		Node rightSub = this.root.right;

		if (leftSub != null) leftSub.parent = null;
        	if (rightSub != null) rightSub.parent = null;

    		
		if (leftSub == null) this.root = rightSub;
	    	else{       
        		Node maxLeft = max(leftSub);
			moveToRoot(maxLeft);
        	    	this.root.right = rightSub;
			if(rightSub != null) rightSub.parent = this.root;
    		}
		this.size--;
		return removed;
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
            			return;
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
