package model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

public class Tree<T> implements Comparable<Tree<T>> {

	private static class Node<T> {
		T data;
		Node<T> left, right;

		Node(T data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}

		Node(Node<T> left, Node<T> right) {
			this.data = null;
			this.left = left;
			this.right = right;
		}
	}

	private Node<T> root;
	private long frecuency;

	public Tree(T data, long frecuency) {
		root = new Node<>(data);
		this.frecuency = frecuency;
	}

	private Tree() {
		root = null;
	}

	public static <T> Tree<T> merge(Tree<T> t1, Tree<T> t2) {
		Tree<T> ans = new Tree<>();
		ans.root = new Node<T>(t1.root, t2.root);
		ans.frecuency = t1.frecuency + t2.frecuency;
		return ans;
	}

	public long getTreeSize() {
		return getTreeSize(root);
	}

	private long getTreeSize(Node<T> node) {
		if (node.data == null) {
			return getTreeSize(node.left) + getTreeSize(node.right) + 1;
		} else {
			return 9; // 1 + byte
		}
	}

	public void write(BitOutputStream bos, Function<T, Integer> mapToByte)
			throws IOException {
		write(root, bos, mapToByte);
	}

	private void write(Node<T> node, BitOutputStream bos,
			Function<T, Integer> mapToByte) throws IOException {
		if (node.data == null) {
			bos.writeBit(0); // 0 if it isn't a leaf
			write(node.left, bos, mapToByte);
			write(node.right, bos, mapToByte);
		} else {
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
		while (!q.isEmpty()) {
			Node n = q.poll();
			if (n == null) {
				str.append('\n');
				if (!q.isEmpty()) {
					q.add(null);
				}
			} else {
				str.append(n.data + " ");
				if (n.left != null)
					q.add(n.left);
				if (n.right != null)
					q.add(n.right);
			}
		}
		return str.toString();
	}

	public void fillingMap(Map<T, List<Integer>> map) {
		LinkedList<Integer> curr = new LinkedList<Integer>();
		fillingMap(root, map, curr);
		return;
	}

	private void fillingMap(Node<T> node, Map<T, List<Integer>> map,
			LinkedList<Integer> curr) {
		if (node.data == null) {
			curr.addLast(0);
			fillingMap(node.left, map, curr);
			curr.removeLast();
			curr.addLast(1);
			fillingMap(node.right, map, curr);
			curr.removeLast();
		} else {
			map.put(node.data, new LinkedList<Integer>(curr));
		}
	}

	public static Tree<Integer> getFromFile(BitInputStream bis)
			throws IOException {
		Tree<Integer> tree = new Tree<Integer>();
		tree.root = buildFromFile(bis);
		return tree;
	}

	private static Node<Integer> buildFromFile(BitInputStream bis)
			throws IOException {
		int bit = bis.readBit();
		if (bit == 0) {
			return new Node<Integer>(buildFromFile(bis), buildFromFile(bis));
		} else {
			return new Node<Integer>(bis.readByte());
		}
	}

	public void decode(BitInputStream bis, OutputStream os, long bitsLeft,
			Function<T, Integer> mapToByte) throws IOException {
		if (bitsLeft < 0) {
			throw new IllegalArgumentException();
		}
		while (bitsLeft > 0) {
			bitsLeft-=decode(bis, os, mapToByte, root);
		}
		
	}

	private int decode(BitInputStream bis, OutputStream os,
			Function<T, Integer> mapToByte, Node<T> node)
			throws IOException {
		int lenght=0;
		while(node.data == null) {
			lenght++;
			if (bis.readBit() == 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		os.write(mapToByte.apply(node.data));
		return lenght;
	}

}
