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

import model.BitOutputStream;
import model.HuffmanTreeBuilder;
import model.Tree;

public final class HuffmanUtil {
	
	private HuffmanUtil(){}
	
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
		tree.write(bos, c->c);
		Map<Integer, List<Integer>> representation = new TreeMap<>();
		tree.fillingMap(representation);
		is = new FileInputStream(toCompress);
		while((byteRead=is.read())!=-1){
			byteRead&=0xFF;
			bos.writeBit(representation.get(byteRead));
		}
		bos.flush();
		os.close();
		is.close();
		
	}
	
}
