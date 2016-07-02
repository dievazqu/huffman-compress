package test;

import java.io.File;
import java.io.IOException;

import utils.HuffmanUtil;

public class UncompressionTest {

	public static void main(String[] args) {
		try{
			HuffmanUtil.uncompress(new File("compressed"), new File("uncompressed"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
