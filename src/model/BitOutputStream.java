package model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BitOutputStream {

	private OutputStream os;
	private int byteToWrite;
	private int size;
	
	public BitOutputStream(OutputStream os){
		this.os=os;
		byteToWrite=0;
		size=0;
	}
	
	public void writeBit(List<Integer> bits) throws IOException{
		for(Integer b : bits){
			writeBit(b);
		}
	}
	
	/**
	 * writes a bit in the stream.
	 * 
	 * @param b - uses only least significant bit.
	 * @throws IOException
	 */
	public void writeBit(int b) throws IOException{
		write(b&1);
	}
	
	/**
	 * writes a bit in the stream.
	 * 
	 * @param b - true if 1, false if 0.
	 * @throws IOException
	 */
	public void writeBit(boolean b) throws IOException{
		write(b?1:0);
	}
	
	/**
	 * writes a byte in the stream.
	 * 
	 * @param b - uses only least significant byte.
	 * @throws IOException
	 */
	public void writeByte(int b) throws IOException{
		for(int i=0; i<8; i++){
			writeBit((b&0x80)>0);
			b<<=1;
		}
	}
	
	/**
	 * writes the last byte (if needed), filling it with leading 0.
	 * @throws IOException 
	 * 
	 */
	public void flush() throws IOException{
		while(size!=0){
			write(0);
		}	
	}
	
	private void write(int b) throws IOException{
		if((b|1)!=1){
			throw new IllegalArgumentException();
		}
	//	auxiliarLiteralBitWriting(b);
		byteToWrite=(byteToWrite<<1)|b;
		size++;
		tryToWriteOutputStream();
		return;
	}
	
	//private void auxiliarLiteralBitWriting(int b) throws IOException{os.write(b>0?'1':'0');}
	
	private void tryToWriteOutputStream() throws IOException{
		if(size>8){
			throw new IllegalStateException();
		}
		if(size==8){
			os.write(byteToWrite);
			byteToWrite>>=8;
			size-=8;
		}
	}
	
}
