package javagame;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame
{
	public static final String gameName = "Java Game";
	public static final int loading = 0;
	public static final int menu = 1;
	public static final int play = 2;
	public static final int hiscores = 3;
	public static final int play2 = 4;
	public static final int over = 5;
	public static final int play3 = 6;
	public static boolean death = false;
	public static int timer =0;
	public static int prevState = -1;
	public static boolean win = false;
	public static ArrayList <Integer> scores = new ArrayList <Integer>();
	public static int score=0;
	public static int health = 5;
	
	// constructor
	public Game(String gameName)
	{
		super(gameName);
		this.addState(new Menu(menu));
		this.addState(new Play(play));
		this.addState(new HiScores(hiscores));
		this.addState(new LoadingScreen(loading));
		this.addState(new GameOver(over));
		this.addState(new Play2(play2));
		this.addState(new Play3(play3));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException 
	{
		this.getState(loading).init(gc,this);
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(play2).init(gc,this);
		this.getState(play3).init(gc,this);
		this.getState(hiscores).init(gc,this);
		this.getState(over).init(gc, this);
		this.enterState(loading); // screen thats displayed first		
	}

	public static void main(String[] args) 
	{
		AppGameContainer appgc;
		try
		{
			appgc = new AppGameContainer(new ScalableGame( new Game(gameName), 1000, 650,false));
			appgc.setDisplayMode(1000,650,false);
			appgc.setVSync(true);
			appgc.setShowFPS(false);
			appgc.start();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
	}
}
