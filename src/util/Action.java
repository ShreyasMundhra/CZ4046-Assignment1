package util;
public class Action {
	private double northProb;
	private double southProb;
	private double eastProb;
	private double westProb;
	private char symbol;
	
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

	public double getNorthProb() {
		return northProb;
	}

	public double getSouthProb() {
		return southProb;
	}

	public double getEastProb() {
		return eastProb;
	}

	public double getWestProb() {
		return westProb;
	}
	
	public char getSymbol() {
		return symbol;
	}	
}