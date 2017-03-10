import java.util.ArrayList;

import javax.swing.text.Utilities;

public class PolicyIteration implements Environment{
	private Action[][] policy;
	private double[][] utilities;
	private ArrayList<Double> utilityVsIter;
	
	public PolicyIteration() {
		policy = new Action[6][6];
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(maze[i][j] == WALL)
					continue;
				policy[i][j] = GO_NORTH;
			}
		}
		
		utilities = new double[6][6];
		utilityVsIter = new ArrayList<>();
	}
	
	public void doPolicyIteration(){
		int epoch = 1;
		double[][] newUtilities = new double[6][6];
		double deltaMax;
		
		do{
			deltaMax = 0;
			System.out.println("Epoch: " + epoch);
			utilityVsIter.add(utilities[0][0]);
			for(int i = 0; i < 6; i++){
				for(int j = 0; j < 6; j++){
					if(Environment.maze[i][j] == Environment.WALL){
						continue;
					}
					
					double reward = maze[i][j].reward;
					Action action = policy[i][j];
					double futureUtility = findUtility(i,j,action);
					newUtilities[i][j] = reward + gamma*futureUtility;
					
					double deltaCur = Math.abs(utilities[i][j]-newUtilities[i][j]);
					deltaMax = Math.max(deltaCur, deltaMax);
//					deltaMin = Math.min(deltaCur, deltaMin);
				}
			}
			for(int i = 0; i < 6; i++)
				System.arraycopy(newUtilities[i], 0, utilities[i], 0, utilities[i].length);
			findPolicy();			
			epoch += 1;
		} while(deltaMax >= epsilon*(1-gamma)/gamma);
	}
	
	private void findPolicy(){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(maze[i][j] == WALL){
					continue;
				}
				policy[i][j] = findBestAction(i,j);
			}
		}
	}
	
	private Action findBestAction(int i, int j){
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
		double northProb = action.northProb;
		double westProb = action.westProb;
		double eastProb = action.eastProb;
		double southProb = action.southProb;
		
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

	private void displayAction(Action action){
		if(action == GO_NORTH)
			System.out.print("N");
		else if(action == GO_SOUTH)
			System.out.print("S");
		else if(action == GO_EAST)
			System.out.print("E");
		else if(action == GO_WEST)
			System.out.print("W");
	}
	
	public static void main(String[] args) {
		PolicyIteration policyIter = new PolicyIteration();
		policyIter.doPolicyIteration();
		
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				System.out.print(policyIter.utilities[i][j] + ",");
			}
			System.out.println();
		}
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				policyIter.displayAction(policyIter.policy[i][j]);
				System.out.print(",");
			}
			System.out.println();
		}
		
		Plotter plotter = new Plotter(policyIter.utilityVsIter);
		plotter.drawGraph();
	}

}
