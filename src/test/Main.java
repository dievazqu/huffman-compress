package test;

import java.io.File;
import java.io.IOException;

import utils.HuffmanUtil;

public class Main {

	public static void main(String[] args) {
		if(args.length!=3){
			System.out.println("Wrong arguments.\n"+hints());
			return;
		}
		switch (args[0].toUpperCase()) {
		case "COMPRESS":
			try{
				HuffmanUtil.compress(new File(args[1]), new File(args[2]));
			}catch (IOException e) {
				System.out.println("Error whlie compressing\n"+hints());
				return;
			}
			System.out.println("Compression successful");
			break;
		case "UNCOMPRESS":
			try{
				HuffmanUtil.uncompress(new File(args[1]), new File(args[2]));
			}catch (IOException e) {
				System.out.println("Error whlie uncompressing\n"+hints());
				return;
			}
			System.out.println("Unompression successful");
			break;
		}
	}
	
	public static String hints(){
		return "(compress | uncompress) sourceFile destinationFile";
	}
}
