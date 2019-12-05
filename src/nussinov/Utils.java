package nussinov;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Utils {
	public static void main(String[] args) {
		boolean boo =validateInput("AUCCCCG");
		System.out.println(boo);

	}
	
	
	public static boolean validateInput(String sequence) {
		HashSet<Character> validChars = new HashSet<>();
		List<Character> validCharList = Arrays.asList('A','U','C','G');
		validChars.addAll(validCharList);
		char[] input = sequence.toCharArray();
		for(char c: input) {
			if(!validChars.contains(c))
				return false;	
		}
		return true;
		
	}
	public static boolean matchingPair(char i, char j) {
		switch(i) {
		case 'A':
			if(j== 'U') return true;
		case 'U':
			if(j== 'A') return true;
		case 'C':
			if(j== 'G') return true;
		case 'G':
			if(j== 'C') return true;
		
		default:
			return false;			
			
		}
	}
	
	public static int value(char i, char j) {
		if(matchingPair(i,j)) return 1;
		else return 0;
	}
	
	public static int getIntValue(String pairedBases) {
		return Integer.valueOf(pairedBases);
		
	}
	

}
