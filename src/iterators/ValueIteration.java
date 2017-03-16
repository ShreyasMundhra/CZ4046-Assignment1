package iterators;
import java.util.ArrayList;

import util.Action;
import util.Environment;
import util.FileIOHelper;
//import util.Plotter;
import util.UIHelper;

public class ValueIteration implements Environment{
	private double[][] utilities;
	private Action[][] policy;
	private ArrayList<double[][]> utilsVsIter;

	public ValueIteration() {
		policy = new Action[6][6];
		utilities = new double[6][6];
		utilsVsIter = new ArrayList<double[][]>();
	}
		
	public ArrayList<double[][]> getUtilsVsIter() {
		return utilsVsIter;
	}

	public void doValueIteration(){
		int epoch = 0;
		double[][] newUtilities = new double[6][6];		
		double deltaMax;
		
		do{
			deltaMax = 0;
			
			double[][] utilitiesCopy = new double[6][6];
			for(int i = 0; i < 6; i++)
				System.arraycopy(utilities[i], 0, utilitiesCopy[i], 0, utilities[i].length);
			utilsVsIter.add(utilitiesCopy);
			
			for(int i = 0; i < 6; i++){
				for(int j = 0; j < 6; j++){
					if(Environment.maze[i][j] == Environment.WALL){
						continue;
					}
					double reward = Environment.maze[i][j].reward;
					double futureUtility = findBestActionUtility(i,j);
					newUtilities[i][j] = reward + gamma*futureUtility;
					
					double deltaCur = Math.abs(utilities[i][j]-newUtilities[i][j]);
					deltaMax = Math.max(deltaCur, deltaMax);
				}
			}
			
			for(int i = 0; i < 6; i++)
				System.arraycopy(newUtilities[i], 0, utilities[i], 0, utilities[i].length);
						
			epoch += 1;
		} while(deltaMax >= epsilon*(1-gamma)/gamma);
		
		double[][] utilitiesCopy = new double[6][6];
		for(int i = 0; i < 6; i++)
			System.arraycopy(utilities[i], 0, utilitiesCopy[i], 0, utilities[i].length);
		utilsVsIter.add(utilitiesCopy);
		System.out.println("Number of iterations: " + epoch);
	}

	private double findBestActionUtility(int i, int j) {
		double northUtility = findUtility(i,j,GO_NORTH);
		double westUtility = findUtility(i,j,GO_WEST);
		double eastUtility = findUtility(i,j,GO_EAST);
		double southUtility = findUtility(i,j,GO_SOUTH);
		
		double maxVertical = Math.max(northUtility, southUtility);
		double maxHorizontal = Math.max(eastUtility, westUtility);
		return Math.max(maxVertical, maxHorizontal);
	}
	
	private void findPolicy(){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				policy[i][j] = findBestAction(i,j);
			}
		}
	}
	
	private Action findBestAction(int i, int j){
		if(maze[i][j] == WALL)
			return new Action();
		
		double northUtility = findUtility(i,j,GO_NORTH);
		double westUtility = findUtility(i,j,GO_WEST);
		double eastUtility = findUtility(i,j,GO_EAST);
		double southUtility = findUtility(i,j,GO_SOUTH);
		
		Action vertical = GO_SOUTH;	
		if(northUtility > southUtility)
			vertical = GO_NORTH;
		
		Action horizontal = GO_WEST;
		if(eastUtility > westUtility)
			horizontal = GO_EAST;
		
		if(findUtility(i,j,vertical) > findUtility(i,j,horizontal))
			return vertical;
		
		return horizontal;
	}
	private double findUtility(int i, int j, Action action) {
		double northProb = action.getNorthProb();
		double westProb = action.getWestProb();
		double eastProb = action.getEastProb();
		double southProb = action.getSouthProb();
		
		double northUtility;
		double westUtility;
		double eastUtility;
		double southUtility;
				
		// If agent moves into a wall, it stays on the same square. So, the future utility is equal to the current utility.
		if(i-1 < 0 || Environment.maze[i-1][j] == Environment.WALL)
			northUtility = utilities[i][j];
		else
			northUtility = utilities[i-1][j];
		
		if(j-1 < 0 || Environment.maze[i][j-1] == Environment.WALL)
			westUtility = utilities[i][j];
		else
			westUtility = utilities[i][j-1];
		
		if(j+1 > 5 || Environment.maze[i][j+1] == Environment.WALL)
			eastUtility = utilities[i][j];
		else
			eastUtility = utilities[i][j+1];
		
		if(i+1 > 5 || Environment.maze[i+1][j] == Environment.WALL)
			southUtility = utilities[i][j];
		else
			southUtility = utilities[i+1][j];
		
		double expectedUtility = northUtility*northProb + westUtility*westProb + eastUtility*eastProb + southUtility*southProb;
		return expectedUtility;
	}
	
	public static void main(String[] args) {
		ValueIteration valueIter = new ValueIteration();
		valueIter.doValueIteration();
		
		UIHelper.displayUtilities(valueIter.utilities);
	
		System.out.println("Utilities: ");
		UIHelper.displayUtilitiesGrid(valueIter.utilities);
		
		System.out.println("Policy: ");
		valueIter.findPolicy();
		UIHelper.displayPolicy(valueIter.policy);
		
		FileIOHelper.writeToFile(valueIter.utilsVsIter, "value_iteration_utilities");
//		Plotter plotter = new Plotter(valueIter.utilsVsIter);
//		plotter.drawGraph();
		
//		System.out.println("Utility vs iter: ");
//		for(Double d: valueIter.utilsVsIter){
//			System.out.print(d + " ");
//		}
	}
}
