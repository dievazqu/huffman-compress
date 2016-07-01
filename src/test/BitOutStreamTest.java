package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import utils.BitOutputStream;

public class BitOutStreamTest {

	public static void main(String[] args) throws IOException {
		File f = new File("test1");
		OutputStream os = new FileOutputStream(f);
		BitOutputStream bos = new BitOutputStream(os);
		System.out.println(String.format("%x\n", (int)'c'));
		bos.writeBit(false);
		bos.writeBit(true);
		bos.writeBit(true);
		bos.writeBit(false);
		bos.writeBit(false);
		bos.writeBit(false);
		bos.writeBit(true);
		bos.writeBit(true); // -> 01100011 -> 63 -> 'c'
		bos.writeBit(false);
		bos.writeBit(true);
		bos.writeBit(true);
		bos.writeBit(false);
		bos.writeBit(false);
		bos.writeBit(false);
		bos.writeBit(true);
		bos.flush();
	}
}
