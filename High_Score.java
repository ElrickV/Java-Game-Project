package javagame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class HiScores extends BasicGameState 
{
	File file;
	FileWriter filewriter;
	BufferedWriter bufferwriter;
	//BufferedReader br=null;
	//String sCurrentLine;
	
	public HiScores(int hiscores) 
	{
		hiscores = 3;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		try
		{
			file =new File("scores.txt");
			if(!file.exists())
			{
				file.createNewFile();
			}
			filewriter = new FileWriter(file.getName(),true);
			bufferwriter = new BufferedWriter(filewriter);
			//br = new BufferedReader(new FileReader(file));
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//g.drawOval(300, 200, 200, 200);
		g.setBackground(Color.darkGray);

		g.drawString("SCORES", 450, 80);
		g.drawString("Back to menu screen(M)", 100,400);
		g.drawString("Back to game screen(G)", 100,500);
		g.drawString("SCORE: " + Game.score, 450, 90);

		//		for (int i=0; i < Game.scores.size(); i++)
		//		{
		//			g.drawString(i + ": " + Game.scores.get(i), 450, 110 + (i*10));	
		//		}
		if(Game.death)		
		{
			g.drawString("GAME OVER", 400, 50);
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		Input input = gc.getInput();

		//Collections.sort(Game.scores);
		//		try {
		//			while((sCurrentLine = br.readLine()) != null)
		//			{
		//				Game.scores.add(Integer.parseInt(sCurrentLine));
		//			}
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		
		if(Game.prevState == 3)
		{
			Game.health = 5;
			Game.win = false;
			Game.death = false;
			//Game.score=0;
		}
		if(Game.prevState == 1)
		{
			Game.health = 5;
			//Game.score=0;
			Game.death = false;
		}
		if(input.isKeyDown(Input.KEY_M))
		{
			sbg.enterState(1);
		}
		
		if(input.isKeyDown(Input.KEY_G))
		{
			sbg.enterState(2);
		}

	}
	
	public void leave(GameContainer container, StateBasedGame game) throws SlickException 
	{
		super.leave(container, game);
		try 
		{
			bufferwriter.write("\n " + Integer.toString(Game.score));
			bufferwriter.close();
			//br.close();
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public int getID() 
	{
		return 3;
	}
}
