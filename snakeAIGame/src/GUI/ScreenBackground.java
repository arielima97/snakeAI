package GUI;

import java.awt.*;
import javax.swing.*;

public class ScreenBackground extends JComponent {
	private static final String backgroundPath = System.getProperty("user.dir") + "/src/assets/background.png";
	private static final Image background = new ImageIcon(backgroundPath).getImage();
	public void paintComponent(Graphics g)
	{		
		g.drawImage(background, 0, 0, null);
	}
}
