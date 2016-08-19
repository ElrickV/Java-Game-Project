package javagame;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.CombinedTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.RotateTransition;

public class Menu extends BasicGameState
{
	public Image play1;
	public Image play2;
	public Image play3;
	public Image exitButton;
	public Image scoreButton;
	public Image worldMap;
	public String mouse = "no inpput";

	public Menu(int state)
	{
		state = 1;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		worldMap = new Image("res/world.png");
		play1 = new Image("res/playbutton.png");
		play2 = new Image("res/game2.png");
		play3 = new Image ("res/game3.png");
		exitButton = new Image("res/exitbutton.png");
		scoreButton = new Image("res/HiScores.png");
	}
	
	// draws stuff on the screen, picture or text
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		worldMap.draw(0, 0);
		g.drawString("Kill the monsters", 450, 50);
		g.drawImage(play1,100,100);
		g.drawImage(play2,100,200);
		g.drawImage(play3, 100, 300);
		g.drawImage(scoreButton,100,400);
		g.drawImage(exitButton,100,500);
		g.drawString(mouse,450,600);
		g.setBackground(Color.pink);
	}
	// updating images on screen so it can move around
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();

		mouse = "Mouse Position x: " + xpos + "y: " + ypos;
		
		if((xpos > 100 && xpos < 200) && (ypos > 500 && ypos < 550))
		{
			if(input.isMouseButtonDown(0)) //0 means left click
			{
				sbg.enterState(2, new FadeOutTransition(),new FadeInTransition());
			}
		}

		if((xpos > 100 && xpos < 200)&&(ypos > 400 && ypos < 450))
		{
			if(input.isMouseButtonDown(0)) //0 means left click
			{
				sbg.enterState(4, new FadeOutTransition(),new FadeInTransition()); 
			}
		}
		
		if((xpos > 100 && xpos < 200)&&(ypos > 300 && ypos < 350))
		{
			if(input.isMouseButtonDown(0)) //0 means left click
			{
				sbg.enterState(6, new FadeOutTransition(),new FadeInTransition()); 
			}
		}
		
		if((xpos > 100 && xpos < 200)&&(ypos > 200 && ypos < 250))
		{
			if(input.isMouseButtonDown(0)) //0 means left click
			{
				sbg.enterState(3, new FadeOutTransition(),new FadeInTransition()); 
			}
		}
		
		if((xpos > 100 && xpos < 200)&&(ypos > 100 && ypos < 150))
		{
			if(input.isMouseButtonDown(0)) //0 means left click
			{
				System.exit(0);
			}
		}
	}

	// return 1 cause its the id of the state
	public int getID() 
	{
		return 1;
	}
}
