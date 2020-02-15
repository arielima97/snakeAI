package snakeAIGame;

import java.awt.*;
import javax.swing.*;

public class ScreenElements extends JComponent {
	private final Image background = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
	private static final int initialX = 50;
	private static final int initialY = 250;
	private static JButton startButton = new JButton();
	private static JLabel score = new JLabel();
	private static String score_string = "0";
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
		
		// Score text field
		score.setText(score_string);
		score.setAlignmentY(CENTER_ALIGNMENT);
		score.setAlignmentX(CENTER_ALIGNMENT);
		score.setFont(new Font("SansSerif", Font.BOLD, 28));
		score.setBounds(500, 125, 50, 30);
		add(score);
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
	
	public void setScore(int score_value)
	{
		score_string = String.valueOf(score_value);
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
