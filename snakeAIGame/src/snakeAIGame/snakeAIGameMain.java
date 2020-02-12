package snakeAIGame;

import java.awt.*;
import javax.swing.*;

import GUI.MainScreen;

public class snakeAIGameMain {
	// Getting the icon from assets
	private static final String iconPath = System.getProperty("user.dir") + "/src/assets/icon.png";
	private static final ImageIcon icon = new ImageIcon(iconPath);
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> 
		{		
			var screen = new MainScreen();
			screen.setIconImage(icon.getImage());
			screen.setTitle("snakeAI");
			screen.setResizable(false);
			screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			screen.setVisible(true);			
		});
	}

}


