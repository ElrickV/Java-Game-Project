package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class LoadingScreen extends BasicGameState
{
	private Image logo;
	private int time;
	private final int delay = 3000;
	
	//private Music music;
	//private Sound sound;
	
	public LoadingScreen(int state)
	{
		state = 0;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		logo = new Image("res/logo.png");
		//music = new Music ("res/Sound.mp4");
		//music.setVolume(0.5f);
		//sound = new Sound ("res/Sound.mp4");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.drawImage(logo, 0, 0);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		//sound.play();
		time += delta;
		//music.play();
		if(time >= delay)
		{
			sbg.enterState(1);
		}
	}

	public int getID() 
	{
		return 0;
	}
}
