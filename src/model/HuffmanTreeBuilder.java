package model;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map.Entry;

public class HuffmanTreeBuilder<T> {
	
	private Map<T, Long> frecuencies;
	
	public HuffmanTreeBuilder(Map<T, Long> frecuencies){
		this.frecuencies=frecuencies;
	}
	
	public Tree<T> getHuffmanTree(){
		Queue<Tree<T>> q = new PriorityQueue<>();
		for(Entry<T, Long> entry : frecuencies.entrySet()){
			q.add(new Tree<T>(entry.getKey(), entry.getValue()));
		}
		while(q.size()>1){
			q.add(Tree.merge(q.remove(), q.remove()));
		}
		return q.remove();
	}
}
