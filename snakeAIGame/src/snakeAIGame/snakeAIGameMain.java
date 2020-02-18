package snakeAIGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import snakeAIGame.snakeAIGameMain.Actions;

import java.util.*;


public class snakeAIGameMain{
	// Snake actions
	enum Actions
	{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	};
	private static Actions last_action;
	
	// JFrame main screen
	private static MainScreen screen;
	
	// Score
	private static int score_value = 0;
	
	// Communication thread
	private static Thread communicationThread = new Thread() 
	{
		@Override
		public void run()
		{
			while(true)
			{		
				Scanner read = new Scanner(System.in);
				String data = "";
				if(read.hasNext())
				{
					data = read.nextLine();
					if(data.equals("UP"))
					{
						setLastAction(Actions.UP);
					}
					if(data.equals("DOWN"))
					{
						setLastAction(Actions.DOWN);
					}
					if(data.equals("LEFT"))
					{
						setLastAction(Actions.LEFT);
					}
					if(data.equals("RIGHT"))
					{
						setLastAction(Actions.RIGHT);
					}
					if(data.equals("RUN"))
					{
						snakeAIGameMain.run();
					}					
					send("COMMAND_OK");
				}
			}
		}
	}; 
	
	public static void main(String[] args) {
		// Initialize the main screen
		screen = new MainScreen();	
		
		// Initialize communication thread
		communicationThread.start();
		
		// Adds a key listener to get the snake actions from the keyboard arrows or WASD keys
		screen.addKeyListener(new KeyListener(){
			@Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
                	setLastAction(Actions.UP);
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
                	setLastAction(Actions.DOWN);
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
                	setLastAction(Actions.LEFT);
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
                	setLastAction(Actions.RIGHT);
                }                
            }
            @Override
            public void keyTyped(KeyEvent e) {
                return;
            }
            @Override
            public void keyReleased(KeyEvent e) {
                return;
            }
		});
		
		// Gets the button element from the screen and adds a listener to check the click
		JButton start_game = screen.getStartButton();	
		start_game.addActionListener(new ActionListener()
	    {
		    public void actionPerformed(ActionEvent e)
		    {
		    	// When the Start game button is clicked the game starts/restarts
		    	run();
		    }
	    });
	}
		
	// Starts/restarts game
	static private void run()
	{
		Snake mySnake = new Snake();
	}
	
	// Sets game score
	static private void setScore(int score)
	{
		screen.setScore(score);
	}
	
	// Sets the last snake action received
	static private void setLastAction(Actions _action)
	{
		last_action = _action;
		System.out.println(last_action.toString());
	}
	
	// Send data to the console (ensures there won't be conflicts printing on terminal)
	static private void send(String data)
	{
		System.out.println(data);
	}
}

final class Coordinate implements Cloneable
{
	private int x;
	private int y;
	
	Coordinate(int x, int y)
	{
		setCoordinate(x, y);
	}
	
	void setCoordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	int getX()
	{
		return x;
	}
	
	int getY()
	{
		return y;
	}
	
	String getCoordinate()
	{
		return "(" + x + "," + y + ")";
	}
	
	double getDistance(Coordinate otherCoordinate)
	{
		double distance = 0;
		
		distance += Math.pow((double)(this.x - otherCoordinate.x), 2.0);
		distance += Math.pow((double)(this.y - otherCoordinate.y), 2.0);
		distance = Math.sqrt(distance);
		
		return distance;
	}
	
	void move(Actions action)
	{
		switch(action)
		{
			case UP:
				y--;
				break;
			case DOWN:
				y++;
				break;
			case RIGHT:
				x++;
				break;
			case LEFT:
				x--;
				break;
		}
	}
	
	boolean equals(Coordinate otherCoordinate)
	{
		if(this.x == otherCoordinate.x && this.y == otherCoordinate.y)
			return true;
		else
			return false;
	}
	
	public Object clone()
	{ 
		try 
		{
			return super.clone();
		} catch (CloneNotSupportedException e) 
		{
			return null;
		} 
	}
}

final class Snake
{
	private int size;
	private int score;
	private Stack<Coordinate> body;
	private Actions direction;
	
	Snake()
	{
		body.ensureCapacity(50*50);
		body.add(new Coordinate(25,26));
		body.add(new Coordinate(25,27));
		body.add(new Coordinate(25,25)); // This is the head	
		direction = Actions.UP;
		size = body.size();
		score = size - 3;
	}
		
	void setDirection(Actions new_direction)
	{
		// Block reverse direction
		if(direction == Actions.UP && new_direction == Actions.DOWN)
			direction = Actions.UP;
		else if(direction == Actions.RIGHT && new_direction == Actions.LEFT)
			direction = Actions.RIGHT;
		else if(direction == Actions.DOWN && new_direction == Actions.UP)
			direction = Actions.DOWN;
		else if(direction == Actions.LEFT && new_direction == Actions.RIGHT)
			direction = Actions.LEFT;
		else
			direction = new_direction;
	}
	
	Actions getDirection()
	{
		return direction;
	}
		
	void grow(Coordinate food)
	{
		body.add(food);
		updateSize();
	}
	
	void updateSize()
	{
		size = body.size();
		score = size - 3;
	}
	
	boolean isFood(Coordinate suspect)
	{
		Coordinate predict = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		predict.move(direction); // Moves the snake head to the possible point of food
		return predict.equals(suspect);
	}
	
	boolean isWall()
	{
		Coordinate predict = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		predict.move(direction); // Moves the snake head to the possible point of wall
		if(predict.getX() < 1 || predict.getX() > 50 || predict.getY() < 1 || predict.getY() > 50)
			return true;
		else
			return false;	
	}
	
	boolean isMe()
	{
		Coordinate predict = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		predict.move(direction); // Moves the snake head to the possible point of wall
		if(body.contains(predict))
			return true;
		else
			return false;
	}
	
	
}


