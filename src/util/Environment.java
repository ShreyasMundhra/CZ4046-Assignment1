package util;

public interface Environment {	
	public static Cell WALL = new Cell(1,0);
	public static Cell WHITE = new Cell(2,-0.04);
	public static Cell ORANGE = new Cell(3,-1);
	public static Cell GREEN = new Cell(4,1);
	public static Cell[][] maze = {{GREEN,WALL,GREEN,WHITE,WHITE,GREEN},
								{WHITE,ORANGE,WHITE,GREEN,WALL,ORANGE},
								{WHITE,WHITE,ORANGE,WHITE,GREEN,WHITE},
								{WHITE,WHITE,WHITE,ORANGE,WHITE,GREEN},
								{WHITE,WALL,WALL,WALL,ORANGE,WHITE},
								{WHITE,WHITE,WHITE,WHITE,WHITE,WHITE}};
	
	public static double gamma = 0.99;
	public static double epsilon = 46.0;
	
	public static Action GO_NORTH = new Action(0.8,0,0.1,0.1,'^');
	public static Action GO_WEST = new Action(0.1,0.1,0,0.8,'<');
	public static Action GO_SOUTH = new Action(0,0.8,0.1,0.1,'v');
	public static Action GO_EAST = new Action(0.1,0.1,0.8,0,'>');
}
