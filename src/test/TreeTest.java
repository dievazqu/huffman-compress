package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.HuffmanTreeBuilder;

public class TreeTest {

	public static void main(String[] args) throws IOException {
		String in = "aaaabbbcde";  
		Map<Character, Long> map = new HashMap<>();
		for(int i=0; i<in.length(); i++){
			map.put(in.charAt(i), map.getOrDefault(in.charAt(i), 0L)+1);
		}
		System.out.println(new HuffmanTreeBuilder<Character>(map).getHuffmanTree().toStringByLevel());
	}

}
