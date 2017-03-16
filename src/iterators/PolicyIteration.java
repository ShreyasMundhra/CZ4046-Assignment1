package iterators;
import java.util.ArrayList;

import util.Action;
import util.Environment;
import util.FileIOHelper;
import util.UIHelper;

public class PolicyIteration implements Environment{
	private Action[][] policy;
	private double[][] utilities;
	private ArrayList<double[][]> utilsVsIter;
	
	public PolicyIteration() {
		policy = new Action[6][6];
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(maze[i][j] == WALL)
					policy[i][j] = new Action();
				policy[i][j] = GO_NORTH;
			}
		}
		
		utilities = new double[6][6];
		utilsVsIter = new ArrayList<double[][]>();
	}
	
	public ArrayList<double[][]> getUtilsVsIter() {
		return utilsVsIter;
	}

	public Action[][] getPolicy() {
		return policy;
	}

	public double[][] getUtilities() {
		return utilities;
	}

	public void doPolicyIteration(){
		int epoch = 0;
		double[][] newUtilities = new double[6][6];
		boolean isUtilUnchanged;
		
		do{
			isUtilUnchanged = true;
			
			double[][] utilitiesCopy = new double[6][6];
			for(int i = 0; i < 6; i++)
				System.arraycopy(utilities[i], 0, utilitiesCopy[i], 0, utilities[i].length);
			utilsVsIter.add(utilitiesCopy);
			
			for(int iter = 0; iter < k; iter++){
				for(int i = 0; i < 6; i++){
					for(int j = 0; j < 6; j++){
						if(Environment.maze[i][j] == Environment.WALL){
							continue;
						}

						double reward = maze[i][j].reward;
						Action action = policy[i][j];
						double futureUtility = findUtility(i,j,action);
						newUtilities[i][j] = reward + gamma*futureUtility;
					}
				}
				for(int i = 0; i < 6; i++)
					System.arraycopy(newUtilities[i], 0, utilities[i], 0, utilities[i].length);
			}
			
			for(int i = 0; i < 6; i++){
				for(int j = 0; j < 6; j++){
					double preFutureUtility = findUtility(i,j,policy[i][j]);
					Action bestAction = findBestAction(i, j);
					double currFutureUtility = findUtility(i,j,bestAction);
					
					if(currFutureUtility > preFutureUtility){
						policy[i][j] = bestAction;
						isUtilUnchanged = false;
					}
				}
			}
			
			epoch += 1;
		} while(!isUtilUnchanged);
		
		double[][] utilitiesCopy = new double[6][6];
		for(int i = 0; i < 6; i++)
			System.arraycopy(utilities[i], 0, utilitiesCopy[i], 0, utilities[i].length);
		utilsVsIter.add(utilitiesCopy);
		System.out.println("Number of iterations: " + epoch);
	}
	
	public void findPolicy(){
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
		PolicyIteration policyIter = new PolicyIteration();
		policyIter.doPolicyIteration();
		
		UIHelper.displayUtilities(policyIter.utilities);
		
		System.out.println("Utilities: ");
		UIHelper.displayUtilitiesGrid(policyIter.utilities);
		
		System.out.println("Policy: ");
		policyIter.findPolicy();
		UIHelper.displayPolicy(policyIter.policy);

		FileIOHelper.writeToFile(policyIter.utilsVsIter, "policy_iteration_utilities");
	}

}
