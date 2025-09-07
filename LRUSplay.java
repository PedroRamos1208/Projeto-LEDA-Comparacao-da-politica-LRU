import java.util.HashMap;

public class LRUSplay{
	private long contGlobal;
	private long contMenor;
	private HashMap<Integer, ArvoreSplay.Node> cacheMap;
	private HashMap<Long, ArvoreSplay.Node> timeMap;
	private int capacidade;
	private ArvoreSplay splay;

	public LRUSplay(int capacidade){
		this.cacheMap = new HashMap<>();
		this.timeMap = new HashMap<>();
		this.capacidade = capacidade;
		this.contGlobal = 0;
		this.contMenor = 0;
		this.splay = new ArvoreSplay();
	}

	public boolean isFull(){
		return splay.size() == this.capacidade;
	}

	public boolean isEmpty(){
		return splay.isEmpty();
	}

	public void add(int v){
		if(this.cacheMap.containsKey(v)){
			get(v);	
			return;
		}
		else{
			if(isFull()){
				checaContMenor(); 
				ArvoreSplay.Node node = this.timeMap.get(this.contMenor);
				if(node != null){
					splay.remove(node);
					this.cacheMap.remove(node.value);
					this.timeMap.remove(this.contMenor);
				}
				checaContMenor();
			}
			ArvoreSplay.Node newNode = splay.add(v, this.contGlobal);
			this.cacheMap.put(v,newNode);
			this.timeMap.put(this.contGlobal++,newNode);
		}
	}

	public void checaContMenor(){
		while(!this.timeMap.containsKey(this.contMenor)) this.contMenor++;
	}

	public Integer get(int v){ 
		ArvoreSplay.Node node = this.cacheMap.get(v);
		if(node != null){	
			splay.moveToRoot(node);
			this.timeMap.remove(node.timeStamp);
			node.timeStamp = this.contGlobal;
			this.timeMap.put(this.contGlobal++,node);
			return v;
		}return null;
	}

	public int getMRU(){
		if(contGlobal == 0) return -1;
		return this.timeMap.get(this.contGlobal-1).value;
	}

	public int getLRU(){
		if(this.timeMap.isEmpty()) return -1;
		checaContMenor();
		return this.timeMap.get(this.contMenor).value;
	}

	public int size(){
		return splay.size();
	}
}
