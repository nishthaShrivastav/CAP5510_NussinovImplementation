package nussinov;

public class Testing {

	public static void main(String[] args) {
		boolean boo =matchingPair('A','U');
		System.out.println(boo);

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
	

}
