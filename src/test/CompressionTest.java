package test;

import java.io.File;
import java.io.IOException;

import utils.HuffmanUtil;

public class CompressionTest {
	public static void main(String[] args) {
		try{
			HuffmanUtil.compress(new File("toCompress"), new File("compressed"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
