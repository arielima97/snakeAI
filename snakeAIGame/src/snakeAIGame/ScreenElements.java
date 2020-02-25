package snakeAIGame;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class ScreenElements extends JComponent {
	private final Image background = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
	private static final int initialX = 50;
	private static final int initialY = 250;
	private static JButton startButton = new JButton();
	private static JLabel score = new JLabel();
	private static String score_string = "0";
	private static ArrayList<Coordinate> pixels = new ArrayList<Coordinate>();
	private static boolean game_over_stat = false;
	
	public void paintComponent(Graphics g)
	{	
		// Background
		g.drawImage(background, 0, 0, null);
		
		// Arena
		drawArena(g);
		
		// Game over
		if(game_over_stat)
			drawGameOver(g);
		
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
	
	// Returns the button element (in order to be used in the main class)
	public JButton getStartButton()
	{
		return startButton;
	}
	
	// Returns the score label element (in order to be used in the main class)
	public void setScore(int score_value)
	{
		score_string = String.valueOf(score_value);
		repaint();
	}
	
	// Draw snake and food on arena
	public void drawSnake(ArrayList <Coordinate> points)
	{
		pixels.clear();
		pixels = points;
		repaint();
	}
	
	// Set new game
	public void new_game()
	{
		game_over_stat = false;
		repaint();
	}
	
	// Set game over
	public void game_over()
	{
		game_over_stat = true;
		repaint();
	}
	
	// Draw game over
	public void drawGameOver(Graphics graph)
	{
		pixels.clear();
		graph.setColor(Color.BLACK);
		graph.fillRect(initialX + 50, initialY + 210, 400, 80);		
		graph.setColor(Color.WHITE);
		graph.setFont(new Font("SansSerif", Font.BOLD, 28));
		graph.drawString("GAME OVER", initialX + 160, initialY + 260);
	}
	
	// Drawing 50x50 arena
	private void drawArena(Graphics graph)
	{
		
		for(int i = 1; i <= 50; i++)
		{
			for(int j = 1; j <= 50; j++)
			{
				graph.setColor(Color.LIGHT_GRAY);
				graph.drawRect(initialX + (i-1) * 10, initialY + (j-1) * 10, 10, 10);			
			}
		}
		for(Coordinate p : pixels)
		{
			graph.setColor(Color.BLACK);
			graph.fillRect(initialX + (p.getX() - 1) * 10, initialY + (p.getY() - 1) * 10, 10, 10);
		}
	}
	
}
