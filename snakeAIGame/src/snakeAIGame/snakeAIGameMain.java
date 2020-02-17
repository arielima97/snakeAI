package snakeAIGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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


