package snakeAIGame;

import java.awt.*;
import javax.swing.*;

public class ScreenElements extends JComponent {
	private final Image background = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
	private static final int initialX = 50;
	private static final int initialY = 250;
	private static JButton startButton = new JButton();
	public void paintComponent(Graphics g)
	{	
		// Background
		g.drawImage(background, 0, 0, null);
		
		// Arena
		drawArena(g,Color.LIGHT_GRAY);
		
		// Score area
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(320, 115, 260, 50);
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.BOLD, 28));
		g.drawString("Score:", 330, 150);
		
		// Divisor
		g.setColor(Color.WHITE);
		g.drawLine(300, 20, 300, 180);
		
		// Start button
		startButton.setText("Start game");
		startButton.setBounds(350, 45, 200, 40);
		startButton.setFocusPainted(false);
		startButton.setForeground(Color.white);
		startButton.setFont(new Font("SansSerif", Font.BOLD, 18));	
		startButton.setBackground(Color.black);
		add(startButton);
	}
	
	public JButton getStartButton()
	{
		return startButton;
	}
	
	public void setStartButton(JButton bt)
	{
		startButton = bt;
		repaint();
	}
	
	private void drawArena(Graphics graph, Color c)
	{
		graph.setColor(c);
		for(int i = 0; i < 50; i++)
		{
			for(int j = 0; j < 50; j++)
			{
				graph.drawRect(initialX + i * 10, initialY + j * 10, 10, 10);
			}
		}
		
	}
}
