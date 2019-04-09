import java.awt.*;
public class Wall
{
	private int[] x,y;
	private Color c;

	public Wall(int[] x,int[] y, Color c)
	{
		this.x=x;
		this.y=y;
		this.c=c;
	}
	public int[] getX()
	{
		return x;
	}
	public int[] getY()
	{
		return y;
	}

	public Polygon getPolygon()
	{
		return new Polygon(x,y,4);
	}

	public Color getColor()
	{
		return c;
	}

}