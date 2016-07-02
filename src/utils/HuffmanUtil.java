package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.BitInputStream;
import model.BitOutputStream;
import model.HuffmanTreeBuilder;
import model.Tree;

public final class HuffmanUtil {
	
	private HuffmanUtil(){}
	
	public static long getFileSize(Map<Integer, Long> frecuencies, Map<Integer, List<Integer>> representation){
		long ans=0;
		for(Integer i : frecuencies.keySet()){
			ans+=frecuencies.get(i)*representation.get(i).size();
		}
		return ans;
	}
	
	public static void compress(File toCompress, File compressed) throws IOException{
		InputStream is = new FileInputStream(toCompress);
		Map<Integer, Long> frecuency = new TreeMap<Integer, Long>();
		int byteRead;
		while((byteRead=is.read())!=-1){
			byteRead&=0xFF;
			frecuency.put(byteRead, frecuency.getOrDefault(byteRead, 0L)+1);
		}
		is.close();
		Tree<Integer> tree = new HuffmanTreeBuilder<Integer>(frecuency).getHuffmanTree();
		OutputStream os = new FileOutputStream(compressed);
		BitOutputStream bos = new BitOutputStream(os);
		Map<Integer, List<Integer>> representation = new TreeMap<>();
		tree.fillingMap(representation);
		long size=tree.getTreeSize()+HuffmanUtil.getFileSize(frecuency, representation);
		/*System.out.println(size);
		System.out.println(tree.toStringByLevel());*/
		os.write(ByteUtils.longToBytes(size));
		tree.write(bos, b->b);
		is = new FileInputStream(toCompress);
		while((byteRead=is.read())!=-1){
			byteRead&=0xFF;
			bos.writeBit(representation.get(byteRead));
		}
		bos.flush();
		os.close();
		is.close();
	}
	
	public static void uncompress(File toUncompress, File uncompressed) throws IOException{
		InputStream is = new FileInputStream(toUncompress);
		byte[] byteArray = new byte[8];
		is.read(byteArray);
		long sizeInBits = ByteUtils.bytesToLong(byteArray);
		BitInputStream bis = new BitInputStream(is);
		Tree<Integer> tree = Tree.getFromFile(bis);
		sizeInBits-=tree.getTreeSize();
		/*System.out.println(sizeInBits);
		System.out.println(tree.toStringByLevel());*/
		OutputStream os = new FileOutputStream(uncompressed);
		tree.decode(bis, os, sizeInBits, b->b);
		is.close();
		os.close();
	}
	
}
