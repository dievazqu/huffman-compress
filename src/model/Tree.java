package model;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

import utils.BitOutputStream;

public class Tree<T> implements Comparable<Tree<T>>{

	private static class Node<T>{
		T data;
		Node<T> left, right;
		
		Node(T data){
			this.data=data;
			this.left=null;
			this.right=null;
		}
		
		Node(Node<T> left, Node<T> right){
			this.data=null;
			this.left=left;
			this.right=right;
		}
	}
	
	private Node<T> root;
	private long frecuency;
	
	public Tree(T data, long frecuency){
		root = new Node<>(data);
		this.frecuency=frecuency;
	}
	
	private Tree(){
		root = null;
	}
	
	public static <T> Tree<T> merge(Tree<T> t1, Tree<T> t2){
		Tree<T> ans = new Tree<>();
		ans.root=new Node<T>(t1.root, t2.root);
		ans.frecuency=t1.frecuency+t2.frecuency;
		return ans;
	}
	
	public void write(BitOutputStream bos, Function<T, Integer> mapToByte) throws IOException{
		write(root, bos, mapToByte);
	}
	
	private void write(Node<T> node, BitOutputStream bos, Function<T, Integer> mapToByte) throws IOException{
		if(node.data==null){
			bos.writeBit(0); // 0 if it isn't a leaf 
			write(node.left, bos, mapToByte);
			write(node.right, bos, mapToByte);
		}else{
			bos.writeBit(1); // 1 if it's a leaf
			bos.writeByte(mapToByte.apply(node.data)); // data on the leaf
		}
	}

	@Override
	public int compareTo(Tree<T> o) {
		return Long.compare(frecuency, o.frecuency);
	}

	public String toStringByLevel() {
		Queue<Node> q = new LinkedList<Node>();
		q.add(root);
		q.add(null);
		StringBuilder str = new StringBuilder();
		while(!q.isEmpty()){
			Node n = q.poll();
			if(n==null){
				str.append('\n');
				if(!q.isEmpty()){
					q.add(null);
				}
			}else{
				str.append(n.data+" ");
				if(n.left!=null) q.add(n.left);
				if(n.right!=null) q.add(n.right);
			}
		}
		return str.toString();
	}
}
