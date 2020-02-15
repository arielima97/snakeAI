package snakeAIGame;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class MainScreen extends JFrame
{
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 835;
	private final URL icon_path = getClass().getResource("/assets/icon.png");
	private final ImageIcon icon = new ImageIcon(icon_path);
	private static ScreenElements elements = new ScreenElements();
	public MainScreen()
	{
		// Setting frame
		setIconImage(icon.getImage());
		setTitle("snakeAI");
		setResizable(false);
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
	
	public JButton getStartButton()
	{
		return elements.getStartButton();
	}
	
	public void setScore(int score_value)
	{
		elements.setScore(score_value);
	}
	
	/*@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		System.out.println("left");
		
	    /*if (key == KeyEvent.VK_LEFT) {
	    	last_action = Actions.LEFT;
	    }

	    if (key == KeyEvent.VK_RIGHT) {
	    	last_action = Actions.RIGHT;
	    }

	    if (key == KeyEvent.VK_UP) {
	    	last_action = Actions.UP;
	    }

	    if (key == KeyEvent.VK_DOWN) {
	    	last_action = Actions.DOWN;
	    }		
	}*/


}
