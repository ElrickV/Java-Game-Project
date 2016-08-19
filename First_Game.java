package javagame;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Play extends BasicGameState
{
	Random position;
	//private Entity [] monster = new Entity [10];
	private ArrayList <Entity> monsters;
	private Entity player;
	private Entity enemy;
	private Image worldMap;
	private float playerX = 470;
	private float playerY = 500;
	//private float enemyX;
	//private float enemyY;
	boolean death = false;
	boolean quit = false;
	private long timepassed = 0;

	public Play (int state)
	{
		state = 2;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		worldMap = new Image("res/world.png");
		
		//enemy = new Entity(new Image("res/enemy.png"),enemyX,enemyY);
		monsters = new ArrayList<Entity>();
		player = new Entity (new Image ("res/w2.png"), playerX, playerY);
		position = new Random();
		//enemyX = position.nextInt(962);
		//enemyY = position.nextInt(612);

	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.drawImage(worldMap, 0, 0);
		player.draw(g);
		//enemy.draw(g);

		for (Entity m: monsters)
		{
			m.draw(g);
		}
		
		//g.drawString("X position" + player.getX() + " " + player.getY(), 500, 300);

		if(quit == true)
		{
			g.drawString("Resume (R)", 250,100);
			g.drawString("Menu (M)", 250,150);
			g.drawString("View HiScores (H)", 250,200);
			g.drawString("Quit (Q)", 250,250);
			
			if (quit==false)
			{
				g.clear();
			}
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		g.drawString("Time : " + nf.format((double)Game.timer/1000),800,5);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		timepassed += delta;
		Game.timer += delta;
		
		if(timepassed > 5000)
		{
			timepassed = 0;
			monsters.add(new Entity(new Image("res/enemy.png"), position.nextInt(962), position.nextInt(612)));
		}
		
//		if(player.collidesWithEntity(enemy))
//		{
//			death = true;
//			Score();
//		}
		
		move(gc, delta);

//		if (death == false)
//		{
//			for(int i = 0; i < monster.length; i++)
//			{
//				//if(monster[i].getX() - player.getX() >= 100 && monster[i].getY() - player.getY() >= 100)
//				//{
//					monster[i].setX(player.getX() + delta / 1000);
//					monster[i].setY(player.getY() + delta / 1000);
//				//}
//			}
//		}

//		if (death == false) 
//		{
//			
//		}
		
//		if(player.collidesWith(monsters))
//		{
//			death = true;
//			Score();
//		}

		if (death)
		{
			sbg.enterState(5, new FadeOutTransition(),new FadeInTransition());
		}
	}

	public int getID() 
	{
		return 2;
	}
	public void Score()
	{
		try
		{
			File file = new File("score.txt");
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getName(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Score ");
			bw.write(""+ Game.timer);
			bw.write("");
			bw.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void move(GameContainer gc, int delta)
	{
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_UP))
		{
			player.setY(player.getY() - delta*.1f);

			if (player.getY() < 0) 
			{
				player.setY(player.getY()  / delta * .1f);
			}
		}
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			player.setY(player.getY() + delta*.1f); 

			if (player.getY() > 608)   //top most of the map
			{
				player.setY(player.getY() / delta * .1f); // move in opposite direction
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			player.setX(player.getX() - delta*.1f);

			if (player.getX() < -2) 
			{
				player.setX(player.getX() / delta * .1f);
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT))
		{
			player.setX(player.getX() + delta*.1f);

			if (player.getX() > 962) 
			{
				player.setX(player.getX() / delta * .1f);
			}
		}
	}
}
