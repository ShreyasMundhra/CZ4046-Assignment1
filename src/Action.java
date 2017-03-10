public class Action {
	public double northProb;
	public double southProb;
	public double eastProb;
	public double westProb;
	public char symbol;

//	public Action(double northProb, double southProb, double eastProb, double westProb) {
//		this.northProb = northProb;
//		this.southProb = southProb;
//		this.eastProb = eastProb;
//		this.westProb = westProb;
//	}
	
	public Action(double northProb, double southProb, double eastProb, double westProb, char symbol) {
		this(symbol);
		this.northProb = northProb;
		this.southProb = southProb;
		this.eastProb = eastProb;
		this.westProb = westProb;
	}
	
	public Action(char symbol){
		this.symbol = symbol;
	}
	
	public Action(){
		this('W');
	}
}