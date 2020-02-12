package GUI;

import java.awt.*;
import javax.swing.*;

public class MainScreen extends JFrame
{
	private static final int DEFAULT_WIDTH = 600;
	private static final int DEFAULT_HEIGHT = 835;
	public MainScreen()
	{
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
		add(new ScreenBackground());
	}
	
}
