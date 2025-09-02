public class AVLSplay{
	private Node root;

	public boolean isEmpty(){
		return this.root == null;
	}

	public void add(int v){
		this.root = add(this.root, v);
	}
	private Node add(Node node, int v){
		if(node == null) return new Node(v);
	       	
		if(v < node.value) node.left = add(node.left,v);
		else node.right = add(node.right,v);

		updateHeight(node);
		return rebalance(node);	
	}

	public Node rebalance(Node node){
		if(isLeftPending(node) && balance(node.left) >= 0)
			return rot_dir(node);
		if(isLeftPending(node) && balance(node.left) < 0){
			node.left = rot_esq(node.left);
			return rot_dir(node);
		}
		if(isRightPending(node) && balance(node.right) <= 0)
			return rot_esq(node);
		if(isRightPending(node) && balance(node.right) > 0){
			node.right = rot_dir(node.right);
			return rot_esq(node);
		}
		return node;
	}

	public int height(Node node){
		if(node == null) return -1;
		else return node.height;
	}

	public int balance(Node node){
		if(node == null) return 0;
		else return height(node.left) - height(node.right);
	}

	public void updateHeight(Node node){
		if(node != null)
			node.height = 1 + Math.max(height(node.left), height(node.right));
	}

	public boolean isLeftPending(Node node){
		return balance(node) > 1;
	}

	public boolean isRightPending(Node node){
		return balance(node) < -1;
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
		node.parent = y;
		node.right = ey;
		if(ey != null) ey.parent = node;

		updateHeight(y);
		updateHeight(node);

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
		node.parent = y;
		node.left = dy;
		if(dy != null) dy.parent = node;

		updateHeight(y);
		updateHeight(node);

		if(y.parent == null) this.root = y;

		return y;
	}
}

class Node{
	int value;
	Node left;
	Node right;
	Node parent;
	int height;

	public Node(int v){
		this.value = v;
	}

	public boolean isLeaf(){
		return left == null && right == null;
	}

	public boolean hasOnlyLeftChild(){
		return left != null && right == null;
	}

	public boolean hasOnlyRightChild(){
		return left == null && right != null;
	}
}
