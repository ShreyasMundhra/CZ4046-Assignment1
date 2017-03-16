package util;

import java.text.DecimalFormat;

//import classes.ActionUtilPair;
//import classes.State;

public class UIHelper implements Environment{
	
	/** Display the policy, i.e. the action to be taken at each state **/
	public static void displayPolicy(final Action[][] policyArr) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("|");
        for(int col = 0 ; col < 6 ; col++) {
        	sb.append("---|");
        }
        sb.append("\n");
        
		for (int row = 0; row < 6; row++) {
			sb.append("|");
			for (int col = 0; col < 6; col++) {
				
				sb.append(String.format(" %s |", policyArr[row][col].getSymbol()));
			}
	        
	        sb.append("\n|");
	        for(int col = 0 ; col < 6 ; col++) {
	        	sb.append("---|");
	        }
	        sb.append("\n");
	    }
		
		System.out.println(sb.toString());
		
	}
	
	/*
	 * Display the utilities of all the (non-wall) states
	 */
	public static void displayUtilities(final double[][] utilArr) {
		
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 6; col++) {

				if (maze[row][col] != WALL) {
					System.out.printf("(%1d, %1d): %-2.6f%n", col, row,
							utilArr[row][col]);
				}
			}
		}
	}
	
	/*
	 * Display the utilities of all the states, in a grid format
	 */
	public static void displayUtilitiesGrid(final double[][] utilArr) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("|");
        for(int col = 0 ; col < 6 ; col++) {
        	sb.append("--------|");
        }
        sb.append("\n");
        
        String pattern = "00.000";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        
		for (int row = 0; row < 6; row++) {
			
			sb.append("|");
	        for(int col = 0 ; col < 6 ; col++) {
	        	sb.append("--------|".replace('-', ' '));
	        }
	        sb.append("\n");
			
			sb.append("|");
			for (int col = 0; col < 6; col++) {
				
				sb.append(String.format(" %s |",
						decimalFormat.format(utilArr[row][col]).substring(0, 6)));
			}
			
			sb.append("\n|");
	        for(int col = 0 ; col < 6 ; col++) {
	        	sb.append("--------|".replace('-', ' '));
	        }
	        sb.append("\n");
	        
	        sb.append("|");
	        for(int col = 0 ; col < 6 ; col++) {
	        	sb.append("--------|");
	        }
	        sb.append("\n");
	    }
		
		System.out.println(sb.toString());
	}

}
