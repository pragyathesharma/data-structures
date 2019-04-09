import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Minesweeper extends JPanel implements ActionListener, MouseListener, MenuListener
{
	JFrame frame;
	JMenuBar menuBar;
	JMenu game, theme, controls;
	JMenuItem beginner, intermediate, expert, theme1, theme2, theme3;
	JPanel gridPanel, topPanel, infoPanel;
	JLabel timer, mineCount;
	JButton smiley;
	ImageIcon appIcon;
	Font f;
	javax.swing.Timer timerThread;

	JToggleButton[][] toggles;
	ImageIcon[] mines, flags, smiles, deads, numbers, flaggedMines, wins, waits;
	ArrayList<Point> points, freePoints, minePoints;
	boolean gameOn=true;
	int dimR = 9, dimC = 9, themeNum = 0, totalMines = 10, totalFlags = 0, seconds = 0, totalFlagged = 0, scale = 45;
	boolean firstClickPerformed;


	public Minesweeper()
	{
		frame = new JFrame("Minesweeper");
		frame.add(this);
		frame.setSize(50*dimC,60*dimR);

		firstClickPerformed = false;
		points = new ArrayList<>();
		freePoints = new ArrayList<>();
		minePoints = new ArrayList<>();

		setMenus();
		setIcons();
		setPanels();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	//clicking grid
	public void mouseReleased(MouseEvent e)
	{
			smiley.setIcon(smiles[themeNum]);
			for(int i = 0; i < toggles.length; i++)
			{
				for(int j = 0; j < toggles[i].length; j++)
				{
					if(e.getSource() == toggles[i][j])
					{

						if(gameOn)
						{
							if(e.getButton() == MouseEvent.BUTTON1 && toggles[i][j].getIcon()==null)
							{
								if(!firstClickPerformed)
								{
									firstClickPerformed = true;
									setMines(new Point(i,j));
									expand(i,j);

									timerThread = new javax.swing.Timer(1000,new ActionListener() {public void actionPerformed(ActionEvent e) {setTime(seconds++);	}});
									timerThread.start();

								}
								else
								{
									if(isMine(i,j))
									{
										toggles[i][j].setIcon(mines[themeNum]);
										gameOver("lose");
									}
									else expand(i,j);
								}
							}
							else if(e.getButton() == MouseEvent.BUTTON3)
							{
								if(toggles[i][j].getIcon() == null)
								{
									toggles[i][j].setIcon(flags[themeNum]);
									totalFlags++;
									if(isMine(i,j))
										totalFlagged++;
									setMineCount();
								}
								else if(toggles[i][j].getIcon().equals(flags[themeNum]))
								{
									toggles[i][j].setIcon(null);
									totalFlags--;
									if(isMine(i,j))
										totalFlagged--;
									setMineCount();
								}
								toggles[i][j].setSelected(false);
							}
							checkWin();

						}
						else
						{
							//toggles[i][j].setEnabled(false);
							toggles[i][j].setSelected(!toggles[i][j].isSelected());
						}
						System.out.println("g: "+gameOn);

					}


				}

			}


		//frame.revalidate();
	}

	//expand
	public void expand(int r, int c)
	{
		if(toggles[r][c].getIcon() == null)
		{
				toggles[r][c].setSelected(true);
				if(checkCount(r,c) != null)
				{
					toggles[r][c].setIcon(checkCount(r,c));
					toggles[r][c].setSelected(true);
				}
				else
				{
					for(int row = r-1; row <= r+1; row++)
					{
						for(int col = c-1; col <= c+1; col++)
						{
							if(row >= 0 && row < dimR && col >=0 && col < dimC)
							{
								if(!toggles[row][col].isSelected() && checkCount(row,col) == null)
									expand(row,col);
								else
								{
									if(checkCount(row,col) != null && !isFlag(row,col))
									{
										toggles[row][col].setIcon(checkCount(row,col));
										toggles[row][col].setSelected(true);
									}
								}
							}
						}
					}
				}

		}
	}


	//returns image of number
	public ImageIcon checkCount(int r, int c)
	{
		int m = 0;

		for(int i = r-1; i <= r+1; i++)
		{
			if(isMine(i,c-1)) m++;
			if(isMine(i,c)) m++;
			if(isMine(i,c+1)) m++;
		}

		if(m==0)
			return null;
		return numbers[m-1];
	}

	//end game
	public void gameOver(String result)
	{
		timerThread.stop();
		for(int i = 0; i < toggles.length; i++)
		{
			for(int j = 0; j < toggles[i].length; j++)
			{
				if(isMine(i,j))
				{
					if(isFlag(i,j))
						toggles[i][j].setIcon(flaggedMines[themeNum]);
					else toggles[i][j].setIcon(mines[themeNum]);
				}
				toggles[i][j].removeActionListener(this);
			}
		}
		if(result == "lose")
			smiley.setIcon(deads[themeNum]);
		else smiley.setIcon(wins[themeNum]);
		gameOn=false;
	}
	//reset
	public void resetGridPanel(String type)
	{

		frame.setSize(50*dimC,60*dimR);
		frame.remove(gridPanel);
		setIcons();
		if(type.equals("clear"))
		{
			if(!gameOn)
				timerThread.stop();
			gameOn = true;
			totalFlagged = 0;
			totalFlags = 0;
			seconds = 0;
			setTime(0);
			points = new ArrayList<>();
			freePoints = new ArrayList<>();
			minePoints = new ArrayList<>();
			firstClickPerformed = false;
			setGridPanel();
		}
		if(type.equals("theme"))
			changeIcons();

		frame.add(gridPanel, BorderLayout.CENTER);
        smiley.setIcon(smiles[themeNum]);
		frame.revalidate();
	}

	//set up playing grid
	public void setGridPanel()
	{
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(dimR, dimC));

		toggles = new JToggleButton[dimR][dimC];
		for(int i = 0; i < dimR; i++)
		{
			for(int j = 0; j < dimC; j++)
			{
				toggles[i][j] = new JToggleButton();
				toggles[i][j].addMouseListener(this);
				toggles[i][j].setBackground(Color.WHITE);
				gridPanel.add(toggles[i][j]);
			}
		}

	}
	public void changeIcons()
	{
		for(int i = 0; i < dimR; i++)
		{
			for(int j = 0; j < dimC; j++)
			{
				if(isFlag(i,j))
					toggles[i][j].setIcon(flags[themeNum]);
				if(checkCount(i,j) != null && toggles[i][j].isSelected())
					toggles[i][j].setIcon(checkCount(i,j));
			}
		}
	}

	//place random mines
		public void setMines(Point exclude)
		{
			int count = 0;
			while(minePoints.size()<totalMines)
			{
				int randRow = (int)(Math.random()*dimR);
				int randCol = (int)(Math.random()*dimC);
				Point p = new Point(randRow, randCol);
				if(!minePoints.contains(p) && !p.equals(exclude));
				{
					minePoints.add(p);
					count++;
					System.out.println("Mine "+(count)+": "+(int)(p.getX()+1)+", "+(int)(p.getY()+1));
					freePoints.remove(p);
				}
			}
			System.out.println("------------------------------------------------");
	}

	//set panels
	public void setPanels()
	{
		try
		{
			f = Font.createFont(Font.TRUETYPE_FONT, new File("alarm clock.ttf")).deriveFont(45f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("alarm clock.ttf")));
		}
		catch(IOException e){}
		catch(FontFormatException e){}

		toggles = new JToggleButton[dimR][dimC];

		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		timer = new JLabel(" 00");
		timer.setFont(f);
		timer.setOpaque(true);
		timer.setBackground(Color.BLACK);
		timer.setForeground(Color.RED);
		timer.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
		timer.setPreferredSize(new Dimension(80,50));
		mineCount = new JLabel(" "+totalMines);
		mineCount.setHorizontalAlignment((int)Component.CENTER_ALIGNMENT);
		mineCount.setFont(f);
		mineCount.setOpaque(true);
		mineCount.setBackground(Color.BLACK);
		mineCount.setForeground(Color.RED);
		mineCount.setPreferredSize(new Dimension(80,50));
		smiley = new JButton(smiles[themeNum]);
		smiley.addActionListener(this);
		smiley.setBackground(Color.WHITE);
		smiley.setPreferredSize(new Dimension(64, 64));
		infoPanel.add(mineCount);
		infoPanel.add(smiley);
		infoPanel.add(timer);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(menuBar,BorderLayout.NORTH);
		topPanel.add(infoPanel,BorderLayout.SOUTH);

		setGridPanel();

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(gridPanel, BorderLayout.CENTER);

	}

	//set up icons
	public void setIcons()
	{
		mines = new ImageIcon[3];
		for(int i = 0; i < mines.length; i++)
		{
			mines[i] = new ImageIcon("images/mine"+i+".png");
			mines[i] = new ImageIcon(mines[i].getImage().getScaledInstance(scale,scale, Image.SCALE_SMOOTH));
		}

		flags = new ImageIcon[3];
		for(int i = 0; i < mines.length; i++)
		{
			flags[i] = new ImageIcon("images/flag"+i+".png");
			flags[i] = new ImageIcon(flags[i].getImage().getScaledInstance(scale,scale, Image.SCALE_SMOOTH));
		}

		smiles = new ImageIcon[3];
		for(int i = 0; i < smiles.length; i++)
		{
			smiles[i] = new ImageIcon("images/smile"+i+".png");
			smiles[i] = new ImageIcon(smiles[i].getImage().getScaledInstance(scale,scale, Image.SCALE_SMOOTH));
		}

		deads = new ImageIcon[3];
		for(int i = 0; i < deads.length; i++)
		{
			deads[i] = new ImageIcon("images/dead"+i+".png");
			deads[i] = new ImageIcon(deads[i].getImage().getScaledInstance(scale,scale,Image.SCALE_SMOOTH));
		}

		numbers = new ImageIcon[8];
		for(int i = 0; i < numbers.length; i++)
		{
			numbers[i] = new ImageIcon("images/"+(i+1)+".png");
			numbers[i] = new ImageIcon(numbers[i].getImage().getScaledInstance(scale,scale,Image.SCALE_SMOOTH));
		}

		flaggedMines = new ImageIcon[3];
		for(int i = 0; i < deads.length; i++)
		{
			flaggedMines[i] = new ImageIcon("images/flaggedMine"+i+".png");
			flaggedMines[i] = new ImageIcon(flaggedMines[i].getImage().getScaledInstance(scale,scale,Image.SCALE_SMOOTH));
		}

		wins = new ImageIcon[3];
		for(int i = 0; i < deads.length; i++)
		{
			wins[i] = new ImageIcon("images/win"+i+".png");
			wins[i] = new ImageIcon(wins[i].getImage().getScaledInstance(scale,scale,Image.SCALE_SMOOTH));
		}

		waits = new ImageIcon[3];
		for(int i = 0; i < deads.length; i++)
		{
			waits[i] = new ImageIcon("images/wait"+i+".png");
			waits[i] = new ImageIcon(waits[i].getImage().getScaledInstance(scale,scale,Image.SCALE_SMOOTH));
		}

        frame.setIconImage(mines[themeNum].getImage());
	}

	//set up menus
	public void setMenus()
	{
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);

		game = new JMenu("Game");
		beginner = new JMenuItem("Beginner");
		intermediate = new JMenuItem("Intermediate");
		expert = new JMenuItem("Expert");
		beginner.addActionListener(this);
		intermediate.addActionListener(this);
		expert.addActionListener(this);
		game.add(beginner);
		game.add(intermediate);
		game.add(expert);

		theme = new JMenu("Theme");
		theme1 = new JMenuItem("Original");
		theme2 = new JMenuItem("Modern");
		theme3 = new JMenuItem("Hearts");
		theme1.addActionListener(this);
		theme2.addActionListener(this);
		theme3.addActionListener(this);
		theme.add(theme1);
		theme.add(theme2);
		theme.add(theme3);

		controls = new JMenu("Controls");
		controls.addMenuListener(this);

		menuBar.add(game);
		menuBar.add(theme);
		menuBar.add(controls);
	}

 	//menu and button clicks
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == smiley)
		{
			resetGridPanel("clear");
			timer.setText(" 00");
			mineCount.setText(" "+totalMines);
		}
		if(e.getSource() == beginner)
		{
			dimR = 9;
			dimC = 9;
			totalMines = 10;
			resetGridPanel("clear");
		}
		if(e.getSource() == intermediate)
		{
			dimR = 16;
			dimC = 16;
			totalMines = 40;
			resetGridPanel("clear");
		}
		if(e.getSource() == expert)
		{
			dimR = 16;
			dimC = 30;
			totalMines = 99;
			resetGridPanel("clear");
		}
		if(e.getSource() == theme1)
		{
			themeNum = 0;
			resetGridPanel("theme");
		}
		if(e.getSource() == theme2)
		{
			themeNum = 1;
			resetGridPanel("theme");
		}
		if(e.getSource() == theme3)
		{
			themeNum = 2;
			resetGridPanel("theme");
		}
	}
	public void menuSelected(MenuEvent e){JOptionPane.showMessageDialog(frame,"How to Play: \nLeft click to uncover. \nRight click to set flag. \nSmiley to reset.\nTo win, flag all mines \nand clear all empty spaces.\n ");}
	public boolean isMine(int r, int c){return minePoints.contains(new Point(r,c));}
	public void setMineCount()
	{
		int i = totalMines - totalFlags;
		if(i < 10)
			mineCount.setText(" 0"+i);
		else mineCount.setText(" "+i);
	}
	public void setTime(int s)
	{
		if(s < 10)
			timer.setText(" 0"+s);
		else timer.setText(" "+s);
	}
	public boolean isFlag(int r, int c)
	{
		for(ImageIcon i : flags)
		{
			if(toggles[r][c].getIcon() == i)
				return true;
		}
		return false;
	}
	public void checkWin()
	{
		int totalSelected = 0;
		for(int i = 0; i < toggles.length; i++)
		{
			for(int j = 0; j < toggles[i].length; j++)
			{
				if(toggles[i][j].isSelected())
					totalSelected++;
			}
		}
	System.out.println("selected: "+totalSelected + " flagged: "+totalFlagged);

		if(totalSelected == dimR*dimC - totalMines)
			gameOver("win");


	}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){smiley.setIcon(waits[themeNum]);}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void menuCanceled(MenuEvent e){}
	public void menuDeselected(MenuEvent e){}

	public static void main(String[] args)
	{
		Minesweeper app = new Minesweeper();
	}

}