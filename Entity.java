package javagame;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Entity 
{
	private Image image;
	private Rectangle rectangle;
	private float X, Y;
	private int i;
	
	public Entity(Image img, float x,float y)
	{
		this.image = img;
		this.X = x;
		this.Y = y;
		rectangle = new Rectangle(X, Y, img.getWidth(), img.getHeight());
	}
	public Entity(Image img)
	{
		this.image = img;
		rectangle = new Rectangle(X, Y, img.getWidth(), img.getHeight());
	}
	
	public void draw(Graphics g)
	{
		image.draw(X, Y);
		g.draw(rectangle);
	}
	
	public boolean collidesWithEntity(Entity e)
	{
		return this.rectangle.intersects(e.rectangle);
	}
	
//	public boolean collidesWith(Entity e[])
//	{
//		return this.rectangle.intersects(e[i].rectangle);
//	}
	
	public boolean collidesWith(ArrayList<Entity> monsters) 
	{
		return this.rectangle.intersects(monsters.get(i).rectangle);
	}
	
	public Rectangle getRectangle() 
	{
		return rectangle;
	}
	
	public String rectwidthandheight()
	{
		return ("width " + rectangle.getWidth() +" height " + rectangle.getHeight());
	}
	
	public boolean collidesWithPlatform(Rectangle r)
	{
		return this.rectangle.intersects(r);
	}
	
	public float getX() 
	{
		return X;
	}
	
	public void setX(float x) 
	{
		this.X = x;
		rectangle.setX(x);
	}
	
	public float getY() 
	{
		return Y;
	}
	
	public void setY(float y) 
	{
		this.Y = y;
		rectangle.setY(y);
	}
	
	public int getHeight()
	{
		return image.getHeight();
	}
	
	public int getWidth()
	{
		return image.getWidth();
	}
	
	public boolean collidesWithShape(Shape s)
	{
		return this.rectangle.intersects(s);
	}
	
	public float getMaxY()
	{
		return rectangle.getMaxY();
	}
	public float getMaxX()
	{
		return rectangle.getMaxX();
	}
}

