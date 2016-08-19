package javagame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Play2 extends BasicGameState 
{
	public void enter(GameContainer container, StateBasedGame game) 
	throws SlickException 
	{
		Game.health=5;
		super.enter(container, game);
	}

	boolean spaceBarPressed = false;
	private Entity alien;
	private Entity person;
	private Image w1,w2,w3,w4, worldMap;
	//Platform p;
	float x = 50;
	float y = 100;
	boolean death = false;
	boolean quit = false;
	Random r;
	int delta = 2;
	private ArrayList<Circle> balls;
	private Circle mouseBall;
	private int timePassed;
	private int currentScore;
	
	public Play2(int state) 
	{
		state = 4;
		Game.prevState= state;
	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		worldMap = new Image("res/bground.png");
		alien = new Entity(new Image("res/monster.png"),400,300);
		person = new Entity(new Image("res/w1.png"),x,y);
		w1 = new Image("res/w1.png");
		w2 = new Image("res/w2.png");
		w3= new Image("res/w3.png");
		w4 = new Image("res/w4.png");
		//p = new Platform();
		balls = new ArrayList<Circle>();
		mouseBall = new Circle(0,0,10);
		timePassed = 0;
		r = new Random();
		currentScore = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawImage(worldMap, 0, 0);
		//alien.draw(g);
		//person.draw(g);
		//p.draw(g);
		g.setColor(Color.yellow);
		g.fill(mouseBall);
		//g.drawString("death "+death, 300, 400);
		//.drawString("x: " + person.getX() + " y: " + person.getY(), 200, 300);
		g.setColor(Color.black);
		g.drawString("HEALTH: " + Game.health, 50, 100);
		g.drawString("SCORE: " + Game.score, 50, 120);
		
		if(quit == true)
		{
			g.drawString("Resume (R)", 250,100);
			g.drawString("Menu (M)", 250,150);
			g.drawString("View HiScores (H)", 250,200);
			g.drawString("Quit (Q)", 250,250);
			if(quit==false)
			{
				g.clear();
			}
		}
		g.setColor(Color.red);
		for (Circle c : balls)
		{
			g.fill(c);
		}
	}
	
	public int getID() 
	{
		return 4;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		mouseBall.setCenterX(gc.getInput().getMouseX());
		mouseBall.setCenterY(gc.getInput().getMouseY());
		timePassed += delta;
		if(timePassed > 500)
		{
			timePassed = 10;
			balls.add(new Circle(300 + r.nextInt(400),0,10));
		}
		for(Circle c: balls)
		{
			c.setCenterY(c.getCenterY()+(delta/5));
		}
		
		for(int i = balls.size()-1; i >= 0; i--)
		{
			Circle c = balls.get(i);
			if(c.getCenterY() > 610)
			{
				balls.remove(i);
				Game.health--;
			}
			else if(c.intersects(mouseBall))
			{
				balls.remove(i);
				//currentScore++;
				Game.score++;
			}
		}
		
		Game.scores.add(currentScore);
		
		if(Game.health==0)
		{
			Game.death = true;
		}
		
		//p.update(person, gc);
		if(input.isKeyPressed(Input.KEY_SPACE))
		{
			spaceBarPressed = true;
		}
		if(input.isKeyDown(Input.KEY_UP))
		{
			//person.setY (person.getY()-delta*.1.6); 
			person.setY(person.getY()-delta*.6f);
			if (person.getY() < -5.9) 
			{
				person.setY(person.getY() +delta * .1f);
			}
		}
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			person.setY(person.getY()+delta*.1f); 

			if ((person.getY()) > 700)   //top most of the map
			{
				person.setY(person.getY()- delta * .1f); // move in opposite direction
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			person.setX(person.getX()-delta*.1f);
			if (person.getX() < -11) 
			{
				person.setX(person.getX()- delta * .1f);
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT))
		{
			person.setX(person.getX()+delta*.1f);
			if (person.getX() > 960) 
			{
				person.setX(person.getX()- delta * .1f);
			}
		}

		if(person.collidesWithEntity(alien))
		{
		   death = true;
		}
		
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			quit = true;
		}
		if(quit == true)
		{
			if(input.isKeyDown(Input.KEY_R))
			{
				quit = false;
			}
			if(input.isKeyDown(Input.KEY_M))
			{
				sbg.enterState(0);
			}
			if(input.isKeyDown(Input.KEY_H))
			{
				sbg.enterState(2);
			}
			if(input.isKeyDown(Input.KEY_Q))
			{
				System.exit(0);
			}
		}
		if (Game.death)
		{
			sbg.enterState(2, new FadeOutTransition(),new FadeInTransition());	
		}
	}
}
