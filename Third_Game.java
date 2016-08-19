package javagame;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Play3 extends BasicGameState 
{
	boolean spaceBarPressed = false;
	private Entity alien;
	private Entity person;
	private Image w1,w2,w3,w4, worldMap;
	float x = 50;
	float y = 100;
	boolean death = false;
	boolean quit = false;
	boolean collide = false;
	Random r;
	int delta = 2;
	private ArrayList<Rectangle> platforms;
	private ArrayList<Circle> points;
	private int timePassed, timePassed1;
	private int currentScore;
	private float shiftY;
	private float randomX;
	private float randomY;
	private Circle goal;
	private ArrayList<Rectangle> monsters;

	public Play3(int state) 
	{
		state = 6;
		//Game.prevState= state;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		worldMap = new Image("res/bground.png");
		//alien = new Entity(new Image("res/monster.png"),400,300);
		monsters = new ArrayList<Rectangle>();
		person = new Entity(new Image("res/w1.png"),x,y);
		w1 = new Image("res/w1.png");
		w2 = new Image("res/w2.png");
		w3= new Image("res/w3.png");
		w4 = new Image("res/w4.png");
		platforms = new ArrayList<Rectangle>();
		timePassed = 0;
		timePassed1=0;
		r = new Random();
		currentScore = 0;
		points = new ArrayList<Circle>();
		shiftY = 0;
		goal = new Circle(500,100,20);
		randomX = 0;
		randomY = 0;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.drawImage(worldMap, 0, 0);
		//alien.draw(g);
		person.draw(g);
		g.setColor(Color.black);
		//g.drawString("death "+death, 300, 400);
		//.drawString("x: " + person.getX() + " y: " + person.getY(), 200, 300);
		g.drawString("COLLIDING: " + collide , 400, 100);
		g.setColor(Color.black);
		g.drawString("HEALTH: " + Game.health, 50, 100);
		g.drawString("SCORE: " + Game.score, 50, 120);
		//g.drawString("ShiftY: " + shiftY, 50, 140);
		g.drawString("Time: " + Game.timer, 50, 140);

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
		g.setColor(Color.green);
		g.fill(goal);
		g.setColor(Color.black);
		for (Rectangle p : platforms)
		{
			g.fill(p);
		}
		g.setColor(Color.yellow);
		for(Circle p : points)
		{
			g.fill(p);
		}
		g.setColor(Color.red);
		for(Rectangle m: monsters)
		{
			g.fill(m);
		}
	}

	public int getID() 
	{
		return 6;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		Input input = gc.getInput();
		shiftY = (shiftY - (shiftY/(delta*.9f)));
		timePassed += delta;
		timePassed1 += delta;
		Game.timer += delta;

		If (timePassed > 1000)
		{
			timePassed = 10;
			platforms.add(new Rectangle(0,200 + r.nextInt (700)-50,100,25));
		}

		If (timePassed1 > 5000)
		{
			timePassed1 = 0;
			monsters.add(new Rectangle((float)r.nextInt(800), (float)r.nextInt(600)-100, 15, 15));
			points.add(new Circle(r.nextInt(500),r.nextInt(700)+200,10));
		}

		//		for(Circle p: points)
		//		{
		//			p.setCenterX(p.getCenterX()+(delta/15));
		//			p.setCenterY(p.getCenterY()+(delta/15));
		//		}
		If (person.collidesWithShape(goal))
		{
			Game.win = true;
		}

		If (Game.win)
		{
			sbg.enterState(2, new FadeOutTransition(),new FadeInTransition());
		}

		For (int i = points.size()-1; i >= 0; i--)
		{
			Circle p = points.get(i);
			if(person.collidesWithShape(p))
			{
				Game.health++;
				points.remove(i);
			}
		}

		For (int i = monsters.size()-1; i >= 0; i--)
		{
			Rectangle m = monsters.get(i);
			if(person.collidesWithShape(m))
			{
				Game.health--;
				monsters.remove(i);
			}
		}

		For (Rectangle p: platforms)
		{
			p.setCenterX(p.getCenterX()+(delta/15));
		}

		For (int i = platforms.size()-1; i >= 0; i--)
		{
			Rectangle p = platforms.get(i);
			if(p.getCenterY() > 610)
			{
				platforms.remove(i);
				//Game.health--;
			}
			if(person.getX() <= p.getMaxX() && (person.getX() >= p.getMinX()) && (person.getMaxY() > p.getMinY()) && !(input.isKeyDown(Input.KEY_UP)))
			{	
				if(person.collidesWithPlatform(p) && !(input.isKeyDown(Input.KEY_UP)))
				{
					person.setY(p.getY()-delta*2.9f);
					person.setX(person.getX()+delta/15);
					collide = true;
				}			
			}
		}

		Game.scores.add(currentScore);

		If (Game.health==0)
		{
			Game.death = true;
		}

		If (input.isKeyPressed(Input.KEY_SPACE))
		{
			spaceBarPressed = true;
		}

		If (input.isKeyDown(Input.KEY_UP))
		{
			shiftY++;
			if((shiftY < 13) && (person.getY() <= 700))
			{
				person.setY(person.getY()-delta*.3f);
			}
			if (person.getY() < -5.9) 
			{
				person.setY(person.getY() +delta * .1f);
			}
			if(shiftY >= 13)
			{
				person.setY(person.getY()+delta*.1f);
			}
		}

		If (input.isKeyDown(Input.KEY_DOWN))
		{
			person.setY(person.getY()+delta*.1f); 
			if ((person.getY()) > 700)   //top most of the map
			{
				person.setY(person.getY()- delta * .1f); // move in opposite direction
			}
		}

		If (input.isKeyDown(Input.KEY_LEFT))
		{
			person.setX(person.getX()-delta*.1f);
			if (person.getX() < -11) 
			{
				person.setX(person.getX()- delta * .1f);
			}
		}

		If (input.isKeyDown(Input.KEY_RIGHT))
		{
			person.setX(person.getX()+delta*.1f);
			if (person.getX() > 960) 
			{
				person.setX(person.getX()- delta * .1f);
			}
		}

		//		if(person.collidesWithEntity(alien))
		//		{
		//		   death = true;
		//		}

		If (input.isKeyDown(Input.KEY_ESCAPE))
		{
			quit = true;
		}

		if ((person.getY() <= 600) && !(input.isKeyDown(Input.KEY_UP))) // check for gravity
		{
			person.setY(person.getY()+delta*1.5f);
			
			if ((person.getY()) > 600)   //top most of the map
			{
				person.setY(person.getY()- delta * .1f); // move in opposite direction
			}
		}

		If (quit == true)
		{
			If (input.isKeyDown(Input.KEY_R))
			{
				quit = false;
			}

			If (input.isKeyDown(Input.KEY_M))
			{
				sbg.enterState(0,new FadeOutTransition(),new FadeInTransition());
			}

			If (input.isKeyDown(Input.KEY_H))
			{
				sbg.enterState(2,new FadeOutTransition(),new FadeInTransition());
			}

			If (input.isKeyDown(Input.KEY_Q))
			{
				System.exit(0);
			}
		}

		if (Game.death)
		{
			sbg.enterState(5, new FadeOutTransition(),new FadeInTransition());
		}
	}
}

