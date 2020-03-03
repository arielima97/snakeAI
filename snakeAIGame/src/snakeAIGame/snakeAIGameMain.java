package snakeAIGame;

import java.awt.event.*;
import javax.swing.*;
import snakeAIGame.snakeAIGameMain.Actions;
import snakeAIGame.snakeAIGameMain.Feedback;

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
	
	// Snake feedback
	enum Feedback
	{
		IDLE,
		HIT_WALL,
		HIT_ITSELF,
		HIT_FOOD,
		HIT_NOTHING
	};
	private static Actions last_action = Actions.UP;
	
	// JFrame main screen
	private static MainScreen screen;

	// Game control
	private static boolean isRunning = false;
	
	// Time per action
	private static int time_ms = 100;
	
	// Game execution
	private static Thread game = new Thread() 
	{
		@Override
		public void run()
		{
			snakeAIGameMain.run();
		}
	};
	
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
					if(data.contains("UP"))
					{
						setLastAction(Actions.UP);
					}
					if(data.contains("DOWN"))
					{
						setLastAction(Actions.DOWN);
					}
					if(data.contains("LEFT"))
					{
						setLastAction(Actions.LEFT);
					}
					if(data.contains("RIGHT"))
					{
						setLastAction(Actions.RIGHT);
					}
					if(data.contains("RUN"))
					{
						new_game();
						send(String.valueOf(time_ms));
					}			
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
		start_game.addMouseListener(new MouseListener()
	    {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {	
				new_game();				
			}
		    
	    });
	}
		
	static private void new_game()
	{
		last_action = Actions.UP;
		if(isRunning == true)
			isRunning = false;

		try {
			game = new Thread() 
			{
				@Override
				public void run()
				{
					try 
					{
						Thread.sleep(time_ms + 10);
					} catch (InterruptedException e) {}
					snakeAIGameMain.run();
				}
			};
			game.start();
		} catch (Exception e) 
		{
			isRunning = true;
		}
	}
	
	// Starts/restarts game
	static private void run()
	{
		screen.new_game();	
		isRunning = true;
		Snake mySnake = new Snake();
		Feedback mySnakeFeedback = Feedback.IDLE;
		long counter = 1;
		while(mySnakeFeedback != Feedback.HIT_ITSELF && mySnakeFeedback != Feedback.HIT_WALL && isRunning == true)
		{
			try 
			{
				Thread.sleep(time_ms);
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Game thread interrupted");
			}
			if(isRunning == false)
				return;
			mySnake.setDirection(last_action);
			mySnakeFeedback = mySnake.move();
			setScore(mySnake.getScore());
			screen.drawSnake(mySnake.getDrawPoints());
			send(counter + " - " + mySnake.getHead().toString() + " - Score: " + mySnake.getScore() + " - Food at: " + 
			mySnake.getFood().toString() + " - Front = " + mySnake.getSensorFront() + " - Right = " +
			mySnake.getSensorRight() + " - Left = " + mySnake.getSensorLeft());	
			counter++;
		}
		if(isRunning == false)
			return;
		
		screen.game_over();		
		
		isRunning = false;
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
		//System.out.println(last_action.toString());
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
	
	void setCoordinate(Coordinate otherCoordinate)
	{
		this.x = otherCoordinate.getX();
		this.y = otherCoordinate.getY();
	}
	
	int getX()
	{
		return x;
	}
	
	int getY()
	{
		return y;
	}
	
	public String toString()
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
	private Coordinate food;
	private Random rand = new Random();
	private int sensor_front;
	private int sensor_right;
	private int sensor_left;
	
	Snake()
	{
		body = new Stack<Coordinate>();
		body.ensureCapacity(50*50);
		body.add(0, new Coordinate(25,27));
		body.add(0, new Coordinate(25,26));
		body.add(0, new Coordinate(25,25)); // This is the head	
		direction = Actions.UP;
		size = body.size();
		score = size - 3;
		generateFood();
		updateSensors();
	}
	
	private void updateSensors()
	{
		Coordinate head = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		Coordinate before_head = (Coordinate) body.elementAt(1).clone(); // Clones the snake point before head
		Coordinate front, right, left;
		// Vertical
		if(head.getX() - before_head.getX() == 0)
		{
			if(head.getY() - before_head.getY() == 1)
			{
				front = new Coordinate(head.getX(), head.getY() + 1);
				right = new Coordinate(head.getX() - 1, head.getY());
				left = new Coordinate(head.getX() + 1 , head.getY());
			}
			else
			{
				front = new Coordinate(head.getX(), head.getY() - 1);
				right = new Coordinate(head.getX() + 1, head.getY());
				left = new Coordinate(head.getX() - 1 , head.getY());
			}
		}
		// Horizontal
		else
		{
			if(head.getX() - before_head.getX() == 1)
			{
				front = new Coordinate(head.getX() + 1, head.getY());
				right = new Coordinate(head.getX(), head.getY() + 1);
				left = new Coordinate(head.getX(), head.getY() - 1);
			}
			else
			{
				front = new Coordinate(head.getX() - 1, head.getY());
				right = new Coordinate(head.getX(), head.getY() - 1);
				left = new Coordinate(head.getX() , head.getY() + 1);
			}
		}
		
		// Default: nothing found
		sensor_front = 0;
		sensor_right = 0;
		sensor_left = 0;
		
		// Food detected
		if(front.equals(food))
			sensor_front = 1;
		if(right.equals(food))
			sensor_right = 1;
		if(left.equals(food))
			sensor_left = 1;
		
		// Wall detected
		if(front.getX() < 1 || front.getX() > 50 || front.getY() < 1 || front.getY() > 50)
			sensor_front = 2;
		if(right.getX() < 1 || right.getX() > 50 || right.getY() < 1 || right.getY() > 50)
			sensor_right = 2;
		if(left.getX() < 1 || left.getX() > 50 || left.getY() < 1 || left.getY() > 50)
			sensor_left = 2;
		
		// Self detected
		for(Coordinate p : body)
		{
			if(p.getX() == front.getX() && p.getY() == front.getY())
				sensor_front = 3;
			if(p.getX() == right.getX() && p.getY() == right.getY())
				sensor_right = 3;
			if(p.getX() == left.getX() && p.getY() == left.getY())
				sensor_left = 3;

		}
	}
	
	private void generateFood()
	{
		Coordinate food = new Coordinate(25,25);
		boolean check = false;
		do
		{
			check = false;
			food.setCoordinate(rand.nextInt(50) + 1, rand.nextInt(50) + 1);
			for(Coordinate p : body)
			{
				if(p.getX() == food.getX() && p.getY() == food.getY())
				{
					check = true;
				}
			}			
		}while(check);
		this.food = food;
	}
	
	ArrayList<Coordinate> getDrawPoints()
	{
		ArrayList<Coordinate> points = new ArrayList<Coordinate>();
		points.add(getFood());
		for(Coordinate x : body)
		{
			points.add((Coordinate) x.clone());
		}		
		return points;
	}
	
	int getScore()
	{
		return score;
	}
	
	Coordinate getFood()
	{
		return (Coordinate) food.clone();
	}
	
	Coordinate getHead()
	{
		return (Coordinate) body.elementAt(0).clone();
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
	
	Feedback move()
	{
		if(isFood())
		{
			grow();
			generateFood();
			updateSensors();
			return Feedback.HIT_FOOD;
		}
		else if(isWall())
		{
			return Feedback.HIT_WALL;
		}
		else if(isMe())
		{	
			return Feedback.HIT_ITSELF;
		}
		else
		{
			Coordinate new_head = (Coordinate) body.elementAt(0).clone(); // Clone head coordinate.
			new_head.move(direction); // Move the head to the direction selected by the user.
			body.add(0, new_head); // Add the new head.
			body.removeElementAt(body.size() - 1); // Removes the last coordinate.
			updateSensors();
			return Feedback.HIT_NOTHING;
		}
		
	}
	
	Actions getDirection()
	{
		return direction;
	}
		
	private void grow()
	{
		body.add(0, food);
		updateSize();
		move();
	}
	
	private void updateSize()
	{
		size = body.size();
		score = size - 3;
	}
	
	public int getSensorFront()
	{
		return sensor_front;
	}
	
	public int getSensorRight()
	{
		return sensor_right;
	}
	
	public int getSensorLeft()
	{
		return sensor_left;
	}
	
	private boolean isFood()
	{
		Coordinate predict = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		predict.move(direction); // Moves the snake head to the possible point of food
		return predict.equals(food);
	}
	
	private boolean isWall()
	{
		Coordinate predict = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		predict.move(direction); // Moves the snake head to the possible point of wall
		if(predict.getX() < 1 || predict.getX() > 50 || predict.getY() < 1 || predict.getY() > 50)
			return true;
		else
			return false;	
	}
	
	private boolean isMe()
	{
		Coordinate predict = (Coordinate) body.elementAt(0).clone(); // Clones the snake head
		predict.move(direction); // Moves the snake head to the possible point of wall
		
		for(Coordinate p : body)
		{
			if(p.getX() == predict.getX() && p.getY() == predict.getY())
			{
				return true;
			}
		}
		return false;
	}
	
	
}


