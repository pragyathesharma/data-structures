 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
public class MazeProgram extends JPanel implements KeyListener,MouseListener
{
	//Pragya
	JFrame frame;
	String[][] maze;
	ArrayList<Wall> ceilingAndFloor,walls, wallRects,aheadWalls;
	ArrayList<Point> freeLocations;
	Font f;
	int x=1,y=1,size=15,cRow=1,cCol=1,moves=0, steps=4;
	int xx=0,yy=0;
	int left = KeyEvent.VK_LEFT, right = KeyEvent.VK_RIGHT, up = KeyEvent.VK_UP, down = KeyEvent.VK_DOWN, fKey = KeyEvent.VK_F;
	String dir = "r";
	Point flashlightLocation, trapLocation;
	boolean win = false, flashlight = false, flashlightFound = false;
	Color white, yellow, red;
	AudioInputStream stream;
	Clip clip;
	public MazeProgram()
	{
		setBoard();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			try
				{
					stream = AudioSystem.getAudioInputStream(new File("dream.wav"));
					clip = AudioSystem.getClip();
					clip.open(stream);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);

				}
				catch(Exception e){}

		frame.setSize(1050,800);
		frame.setVisible(true);
		frame.addKeyListener(this);
		this.addMouseListener(this);
		white = new Color(255, 255, 255, 100);
		yellow = new Color(255, 248, 150);
		red = new Color(244, 89, 66);
		try
		{
			f = Font.createFont(Font.TRUETYPE_FONT, new File("rainy.ttf")).deriveFont(24f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("rainy.ttf")));


		}
			catch(IOException e){}
			catch(FontFormatException e) {}




	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(67, 71, 76));
		g.fillRect(0,0,1050,800);
		g.setFont(f);
		g.drawString("huh", 120, 120);
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(new Color(20,20,20));
		g.fillRect(0,0,1000,700);
		for(Wall c:ceilingAndFloor)
		{
			g.setColor(c.getColor());
			g.fillPolygon(c.getPolygon());
		}
		for(Wall r:wallRects)
		{
			g.setColor(r.getColor());
			g.fillPolygon(r.getPolygon());
		}
		for(Wall r:walls)
		{
			g.setColor(r.getColor());
			g.fillPolygon(r.getPolygon());
		}
		for(int i = aheadWalls.size()-1; i>=0; i--)
		{
			g.setColor(aheadWalls.get(i).getColor());
			g.fillPolygon(aheadWalls.get(i).getPolygon());
		}


		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[i].length; j++)
			{
				if(maze[i][j].equals("#"))
				{	g2.setColor(white);
					g2.setStroke(new BasicStroke(2));
					g2.drawRect(size*j, size*i, 15, 15);
					//g2.setColor(new Color(107, 206, 140, 100));
					//g2.fillRect(size*j, size*i, 15, 15);
				}
			}
		}

		Point here = new Point(cRow,cCol);
		g.setColor(new Color(255, 255, 255,150));
		g.drawString((int)(here.getX())+", "+(int)(here.getY()), 700,730);
		g.fillOval(x*size,y*size,15,15);
		String nicedir = "right";
		if(dir.equals("u"))
			nicedir = "up";
		if(dir.equals("d"))
			nicedir = "down";
		if(dir.equals("r"))
			nicedir = "right";
		if(dir.equals("l"))
			nicedir = "left";
		g.drawString("Direction: "+nicedir, 20, 730);
				if(win)
					g.drawString("Wow You Did It", 20, 400);
		g.setColor(yellow);
		g.drawString((int)(flashlightLocation.getX())+", "+(int)(flashlightLocation.getY()), 800,730);
		g.setColor(red);
		g.drawString((int)(trapLocation.getX())+", "+(int)(trapLocation.getY()), 900,730);
		if(here.equals(flashlightLocation) && !flashlightFound)
		{
			g.setColor(yellow);
			flashlightFound = true;
			g.drawString("You found a flashlight! F to toggle", 170,730);
		}
		if(here.equals(trapLocation))
		{
			g.setColor(red);
			g.drawString("You found a portal that leads you...to the entrance of the maze.", 200,500);
			x=1;
			y=1;
			cRow=1;
			cCol=1;

		}
		if(flashlight)
		{
			g.setColor(yellow);
			g.drawString("Flashlight ON", 170, 730);
		}
		//g.drawString(""+yy+","+xx,100,100);

		//g.drawString("Moves: "+moves, 20, 460);

	}
	public void setBoard()
	{
		//choose your maze design

		//pre-fill maze array here
		maze = new String[24][67];
		freeLocations = new ArrayList<>();
		File name = new File("maze.txt");
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			int row = 0;
			while( (text=input.readLine())!= null && row < maze.length)
			{
				for(int col = 0; col < maze[row].length; col++)
				{
					if(text.charAt(col) == '-')
					{
						maze[row][col] = " ";
						if(row!=14 && col!=65)
							freeLocations.add(new Point(row,col));
					}
					else maze[row][col] = text.charAt(col)+"";
					System.out.print(maze[row][col]);
				}
				row++;
				System.out.println();
			}



		}
		catch (IOException io)
		{
			System.err.println("File error");
		}
		flashlightLocation = new Point(freeLocations.remove((int)(Math.random()*freeLocations.size())));
		trapLocation = new Point(freeLocations.remove((int)(Math.random()*freeLocations.size())));
		setCeilingAndFloor();
		setWalls();
	}

	public void setCeilingAndFloor()
	{
		ceilingAndFloor = new ArrayList<Wall>();




		//ceiling
		for(int i = 0; i<steps; i++){
			int[] c1Y={50*i,50*i,50+50*i,50+50*i};
			int[] c1X={100*i,1000-100*i,1000-100*i,100*i};
			if(!flashlight && i>=2)
				ceilingAndFloor.add(new Wall(c1X,c1Y, new Color(30-5*i,30-5*i,30-5*i)));
			else if(!flashlight && i<2)
				ceilingAndFloor.add(new Wall(c1X,c1Y, new Color(70-(20*i), 52-(20*i), 109-(20*i))));
			else ceilingAndFloor.add(new Wall(c1X,c1Y, new Color(80-(20*i), 62-(20*i), 119-(20*i))));

		}
		//floor
		for(int i = 0; i<steps; i++){
			int[] c1Y={650-50*i,650-50*i,700-50*i,700-50*i};
			int[] c1X={100*i,1000-100*i,1000-100*i,100*i};
			if(!flashlight && i>=2)
				ceilingAndFloor.add(new Wall(c1X,c1Y, new Color(40-(5*i),40-(5*i),40-(5*i))));
			else if(!flashlight && i<2)
				ceilingAndFloor.add(new Wall(c1X,c1Y, new Color(70-(20*i), 52-(20*i), 109-(20*i))));
			else ceilingAndFloor.add(new Wall(c1X,c1Y, new Color(80-(20*i), 62-(20*i), 119-(20*i))));
		}

	}

	public void setWalls()
	{
		wallRects = new ArrayList<Wall>();
		//left
		for(int i = 0; i<steps; i++)
		{
			int xA[] = {0+100*i,100+100*i,100+100*i,0+100*i};
			int yA[] = {50+50*i,50+50*i,650-50*i,650-50*i};
			if(!flashlight && i>=2)
				wallRects.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
			else if(!flashlight && i<2)
				wallRects.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
			else wallRects.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
		}
		//right
		for(int i = 0; i<steps; i++)
		{
			int xA[] = {900-100*i,1000-100*i,1000-100*i,900-100*i};
			int yA[] = {50+50*i,50+50*i,650-50*i,650-50*i};
			if(!flashlight && i>=2)
				wallRects.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
			else if(!flashlight && i<2)
				wallRects.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
			else wallRects.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
		}

		walls = new ArrayList<Wall>();
		//left walls
		for(int i = 0; i<steps; i++)
		{
			int xA[] = {0+100*i,100+100*i,100+100*i,0+100*i};
			int yA[] = {0+50*i,50+50*i,650-50*i,700-50*i};
			if(dir.equals("u") && cRow-i>=0 && cCol-1>=0 && maze[cRow-i][cCol-1].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
			if(dir.equals("d") && cRow+i<maze.length && cCol+1<maze[0].length  && maze[cRow+i][cCol+1].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
			if(dir.equals("r") && cRow-1>=0 && cCol+i<maze[0].length  && maze[cRow-1][cCol+i].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
			if(dir.equals("l") && cRow+1<maze.length && cCol-i>=0  && maze[cRow+1][cCol-i].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}

		}
		//rightwalls

	//	y=row
	//	x=col

		for(int i = 0; i<steps; i++)
		{
			int xA[] = {900-100*i,1000-100*i,1000-100*i,900-100*i};
			int yA[] = {50+50*i,0+50*i,700-50*i,650-50*i};
			if(dir.equals("u") && cRow-i>=0 && cCol+1<maze[0].length && maze[cRow-i][cCol+1].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
			if(dir.equals("d") && cRow+i<maze.length && cCol-1>=0  && maze[cRow+i][cCol-1].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
			if(dir.equals("r") && cRow+1<maze.length && cCol+i<maze[0].length  && maze[cRow+1][cCol+i].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
			if(dir.equals("l") && cRow-1<maze.length && cCol-i>=0  && maze[cRow-1][cCol-i].equals("#"))
			{
				if(!flashlight && i>=2)
					walls.add(new Wall(xA, yA, new Color(40-(5*i),40-(5*i),40-(5*i))));
				else if(!flashlight && i<2)
					walls.add(new Wall(xA,yA, new Color(125-(30*i), 125-(30*i), 125-(30*i))));
				else walls.add(new Wall(xA, yA, new Color(216-(30*i), 216-(30*i), 216-(30*i))));
			}
		}

		aheadWalls = new ArrayList<>();
		int j = 0;
		for(int i = steps; i >= 0; i--)
		{
			int xAhead[] = {400-100*i,600+100*i,600+100*i,400-100*i};
			int yAhead[] = {200-50*i,200-50*i,500+50*i,500+50*i};
			if(dir.equals("u") && cRow-j>=0 && maze[cRow-j][cCol].equals("#"))
			{
				if(!flashlight && j>=2)
					aheadWalls.add(new Wall(xAhead, yAhead, new Color(20+(5*i),20+(5*i),20+(5*i))));
				else if(!flashlight && j<2)
					aheadWalls.add(new Wall(xAhead,yAhead, new Color(35+(30*i), 35+(30*i), 35+(30*i))));
				else aheadWalls.add(new Wall(xAhead,yAhead, new Color(126+(30*i),126+(30*i),126+(30*i))));
			}
			if(dir.equals("d") && cRow+j<maze.length && maze[cRow+j][cCol].equals("#"))
			{
				if(!flashlight && j>=2)
					aheadWalls.add(new Wall(xAhead, yAhead, new Color(20+(5*i),20+(5*i),20+(5*i))));
				else if(!flashlight && j<2)
					aheadWalls.add(new Wall(xAhead,yAhead, new Color(35+(30*i), 35+(30*i), 35+(30*i))));
				else aheadWalls.add(new Wall(xAhead,yAhead, new Color(126+(30*i),126+(30*i),126+(30*i))));
			}
			if(dir.equals("r") && cCol+j<maze.length && maze[cRow][cCol+j].equals("#"))
			{
				if(!flashlight && j>=2)
					aheadWalls.add(new Wall(xAhead, yAhead, new Color(20+(5*i),20+(5*i),20+(5*i))));
				else if(!flashlight && j<2)
					aheadWalls.add(new Wall(xAhead,yAhead, new Color(35+(30*i), 35+(30*i), 35+(30*i))));
				else aheadWalls.add(new Wall(xAhead,yAhead, new Color(126+(30*i),126+(30*i),126+(30*i))));
			}
			if(dir.equals("l") && cCol-j>=0 && maze[cRow][cCol-j].equals("#"))
			{
				if(!flashlight && j>=2)
					aheadWalls.add(new Wall(xAhead, yAhead, new Color(20+(5*i),20+(5*i),20+(5*i))));
				else if(!flashlight && j<2)
					aheadWalls.add(new Wall(xAhead,yAhead, new Color(35+(30*i), 35+(30*i), 35+(30*i))));
				else aheadWalls.add(new Wall(xAhead,yAhead, new Color(126+(30*i),126+(30*i),126+(30*i))));
			}
			j++;
		}
	}




	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==fKey && flashlightFound)
		{
			flashlight = !flashlight;
			setWalls();
			setCeilingAndFloor();
			repaint();
		}
		if(cRow==14 && cCol==65)
			win = true;
		else{

		if(e.getKeyCode() == up)
		{
			if(dir.equals("u"))
			{
				if(cRow-1>=0 && maze[cRow-1][cCol].equals(" "))
				{	y--;
					cRow--;
					moves++;
				}
			}
			if(dir.equals("d"))
			{
				if(cRow+1<maze[cRow].length && maze[cRow+1][cCol].equals(" "))
				{
					y++;
					cRow++;
					moves++;
				}
			}
			if(dir.equals("l"))
			{
				if(cCol-1>=0 && maze[cRow][cCol-1].equals(" "))
				{
					x--;
					cCol--;
					moves++;
				}
			}
			if(dir.equals("r"))
			{
				if(cCol+1>=0 && maze[cRow][cCol+1].equals(" "))
				{
					x++;
					cCol++;
					moves++;
				}
			}

		}
		if(e.getKeyCode() == right)
		{
			if(dir.equals("u"))
				dir = "r";
			else if(dir.equals("r"))
				dir = "d";
			else if(dir.equals("d"))
				dir = "l";
			else if(dir.equals("l"))
				dir = "u";
		}
		if(e.getKeyCode() == left
		)
		{
			if(dir.equals("u"))
				dir = "l";
			else if(dir.equals("l"))
				dir = "d";
			else if(dir.equals("d"))
				dir = "r";
			else if(dir.equals("r"))
				dir = "u";

		}
	}
	setWalls();
		repaint();
	}

	public Color randColor()
	{
		return new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	public void keyReleased(KeyEvent e)
	{

	}
	public void keyTyped(KeyEvent e)
	{

	}
	public void mouseClicked(MouseEvent e)
	{
		xx=e.getX();
		yy=e.getY();
		repaint();
	}
	public void mousePressed(MouseEvent e)
	{
	}
	public void mouseReleased(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
	}
	public void mouseExited(MouseEvent e)
	{
	}
	public static void main(String args[])
	{
		MazeProgram app=new MazeProgram();
	}
}