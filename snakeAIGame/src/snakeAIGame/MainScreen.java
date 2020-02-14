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
		add(new ScreenElements());
		
		// Shows screen
		setVisible(true);	
	}
	
}
