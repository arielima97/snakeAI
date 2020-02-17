/* 
* Snake AI game
* Developed by Ariel Lima
* Copyright. All rights reserved © 
*/

package snakeAIGame;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class MainScreen extends JFrame
{
	// Screen size
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 835;
	
	// Game icon
	private final URL icon_path = getClass().getResource("/assets/icon.png");
	private final ImageIcon icon = new ImageIcon(icon_path);
	
	// Elements of the screen (background, button, snake arena)
	private static ScreenElements elements = new ScreenElements();
	
	public MainScreen()
	{
		// Setting frame
		setIconImage(icon.getImage());
		setTitle("snakeAI");
		setResizable(false);
		setFocusable(true);
		requestFocusInWindow();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Getting screen size
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		
		// Centralize screen
		setLocation((screenWidth - DEFAULT_WIDTH) / 2, (screenHeight - DEFAULT_HEIGHT) / 2);
		
		// Set screen size
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		// Add background
		add(elements);	
				
		// Shows screen
		setVisible(true);	
	}
	
	// Returns the button element (in order to be used in the main class)
	public JButton getStartButton()
	{
		return elements.getStartButton();
	}
	
	// Returns the score label element (in order to be used in the main class)
	public void setScore(int score_value)
	{
		elements.setScore(score_value);
	}

}
