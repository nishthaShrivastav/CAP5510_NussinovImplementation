package nussinov;

import java.io.*;
import java.util.*;

public class NussinovImpl {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		String filename = args[0];
		int eMatrix[][];

		String sequence =getSequence(filename);
		//take input
		if(args.length==0)
			System.out.println("No input given");
		else if (!Utils.validateInput(sequence)) 
			System.out.println("Invalid input");
		else {
//			initialize the matrix 
			int n = sequence.length();
			eMatrix = new int[n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					eMatrix[i][j] = -1;
			
			//start the algorithm
			int pairedBases = dpAlgo(0, sequence.length()-1, eMatrix, sequence);
			 System.out.println("Number of paired bases found " + pairedBases);
	         System.out.println("Secondary Structure after folding: " +  getResult(sequence,eMatrix));
	         long endTime = System.currentTimeMillis();
	         System.out.println("Computation time: " +(endTime-startTime)+" ms");

		}

	}

	public static String getSequence(String filename) {

		File file = new File(filename); 
		String rnaSequence = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String st;
			while ((st = br.readLine()) != null) {
				if(st.charAt(0)== '>') {
					continue;
				}
				else 
					rnaSequence = rnaSequence + st;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rnaSequence.toUpperCase();
	}
	
	   private static int dpAlgo(int i, int j, int[][] eMatrix, String sequence) {

	        if (eMatrix[i][j] != -1) {
	            return eMatrix[i][j];
	        }

	        if (i >= j) {
	        	eMatrix[i][j] = 0;
	            return 0;
	        }

	        int max = 0;

	        max = Math.max(max, Math.max(dpAlgo(i + 1, j,eMatrix,sequence), dpAlgo(i, j - 1,eMatrix,sequence)));

	        if (Utils.matchingPair(sequence.charAt(i),sequence.charAt(j))) {
	            max = Math.max(max, dpAlgo(i + 1, j - 1,eMatrix,sequence) + 1);
	        }
	        
	        for (int k = i + 1; k < j; k++)
	            max = Math.max(max, dpAlgo(i, k,eMatrix,sequence) + dpAlgo(k + 1, j,eMatrix,sequence));

	        eMatrix[i][j] = max;
	        return max;
	    }
	   /*
	    * This method takes the energy matrix and performs the traceback operation to get the final structure 
	    * */
	   public static String getResult(String sequence, int[][] eMatrix){

		   ArrayList<String> result = new ArrayList<>();
		   backtrackingAlgo(0, sequence.length()-1, eMatrix, sequence, result);
		   char dotMatrixFormat[] = new char[sequence.length()];

		   Arrays.fill(dotMatrixFormat, '.');
		   for(String match: result){
			   String[] pairedBases = match.split(",");
			   dotMatrixFormat[Utils.getIntValue(pairedBases[0])] = '(';
			   dotMatrixFormat[Utils.getIntValue(pairedBases[1])] = ')';
		   }
		   return new String(dotMatrixFormat);

	   }

	private static void backtrackingAlgo(int i, int j, int[][] eMatrix, String sequence, ArrayList<String> result) {
		if(j <= i)return;
		else if(eMatrix[i][j] == eMatrix[i+1][j]) {
			backtrackingAlgo(i+1, j, eMatrix,sequence, result);
		}
		else if(eMatrix[i][j] == eMatrix[i][j-1]) {
			backtrackingAlgo(i, j-1,eMatrix,sequence, result);
		}
		else if(eMatrix[i+1][j-1] + Utils.value(sequence.charAt(i), sequence.charAt(j)) == eMatrix[i][j]) {
			result.add(i+","+j);
			backtrackingAlgo(i+1, j-1, eMatrix,sequence, result);
		}
		else {
			for(int k= i+1; k < j-1; k++){
				if(eMatrix[i][j] == eMatrix[i][k] + eMatrix[k+1][j]) {
					backtrackingAlgo(i, k, eMatrix,sequence, result); 
					backtrackingAlgo(k + 1, j, eMatrix,sequence, result);
					return;
				}
			}
		}
	}
		
		
	}
