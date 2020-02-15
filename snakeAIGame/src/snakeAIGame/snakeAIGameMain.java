package snakeAIGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class snakeAIGameMain{
	enum Actions
	{
		UP,
		DOWN,
		LEFT,
		RIGHT,
	};
	private static MainScreen screen;
	private static Actions last_action;
	
	public static void main(String[] args) {
		screen = new MainScreen();	
		
		JButton start_game = screen.getStartButton();
		start_game.addActionListener(new ActionListener()
	    {
		    public void actionPerformed(ActionEvent e)
		    {
		    	run();
		    }
	    });
	}
	
	static private void readKeys()
	{
		new Thread (){
			@Override
			public void run()
			{
				Scanner read = new Scanner(System.in);
				read.nextLine();				
			}
		}.start();
	}
	
	static private void run()
	{
		System.out.println("Click!");
		readKeys();
	}
	
	static private void setScore(int score)
	{
		screen.setScore(score);
	}
	
	static private void setLastAction(Actions _action)
	{
		last_action = _action;
	}
}


