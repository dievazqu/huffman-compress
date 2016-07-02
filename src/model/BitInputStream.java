package model;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream {

	private InputStream is;
	private int byteToRead;
	private int size;
	private boolean finished;
	
	public BitInputStream(InputStream is){
		this.is=is;
		byteToRead=0;
		size=0;
		finished=false;
	}
	
	public boolean isFinished(){
		return finished;
	}
	
	public int readBit() throws IOException{
		if(finished){
			return -1;
		}
		return read()?1:0;
	}
	
	public int readByte() throws IOException{
		int ans = 0;
		for(int i=0; i<8; i++){
			int bit=readBit();
			if(finished){
				return ans;
			}
			ans<<=1;
			ans|=bit;
		}
		return ans;
	}
	
	private boolean read() throws IOException{
		tryToReadInputStream();
		byteToRead<<=1;
		size--;
		return (byteToRead&0x0100)>0;
	}
	
	private void tryToReadInputStream() throws IOException{
		if(size>8){
			throw new IllegalStateException();
		}
		if(size==0){
			byteToRead=is.read();
			if(byteToRead==-1){
				finished=true;
			}
			size=8;
		}
	}
}
