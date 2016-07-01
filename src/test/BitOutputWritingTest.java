package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import utils.BitOutputStream;
import model.HuffmanTreeBuilder;
import model.Tree;

public class BitOutputWritingTest {
	public static void main(String[] args) throws IOException {
		String in = "aaaabbbcde";  
		Map<Character, Long> map = new HashMap<>();
		for(int i=0; i<in.length(); i++){
			map.put(in.charAt(i), map.getOrDefault(in.charAt(i), 0L)+1);
		}
		Tree<Character> tree = new HuffmanTreeBuilder<Character>(map).getHuffmanTree();
		System.out.println(tree.toStringByLevel());
		File f = new File("test2");
		OutputStream os = new FileOutputStream(f);
		BitOutputStream bos = new BitOutputStream(os);
		tree.write(bos, c->(int)c);
	}
}
