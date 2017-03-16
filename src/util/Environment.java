package util;

public interface Environment {	
	public static final Cell WALL = new Cell(1,0);
	public static final Cell WHITE = new Cell(2,-0.04);
	public static final Cell ORANGE = new Cell(3,-1);
	public static final Cell GREEN = new Cell(4,1);
	public static final Cell[][] maze = {{GREEN,WALL,GREEN,WHITE,WHITE,GREEN},
										{WHITE,ORANGE,WHITE,GREEN,WALL,ORANGE},
										{WHITE,WHITE,ORANGE,WHITE,GREEN,WHITE},
										{WHITE,WHITE,WHITE,ORANGE,WHITE,GREEN},
										{WHITE,WALL,WALL,WALL,ORANGE,WHITE},
										{WHITE,WHITE,WHITE,WHITE,WHITE,WHITE}};
	
	public static final double gamma = 0.99;
	public static final double epsilon = 46.0;
	public static final int k = 10;
	
	public static final Action GO_NORTH = new Action(0.8,0,0.1,0.1,'^');
	public static final Action GO_WEST = new Action(0.1,0.1,0,0.8,'<');
	public static final Action GO_SOUTH = new Action(0,0.8,0.1,0.1,'v');
	public static final Action GO_EAST = new Action(0.1,0.1,0.8,0,'>');
}
