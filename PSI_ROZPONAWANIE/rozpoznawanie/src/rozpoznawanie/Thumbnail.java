package rozpoznawanie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import rozpoznawanie.DigitizeCfg;

public class Thumbnail extends JPanel {
    
    private static final long serialVersionUID = 1L;

	private boolean[][] character;
	private int size;
	private int margin;
	
	public Thumbnail() {
		size = 200 / DigitizeCfg.COLUMN;
		margin = 10;
		character = new boolean[0][0];
		
		setPreferredSize(new Dimension(DigitizeCfg.COLUMN * size + 2 * margin, DigitizeCfg.ROW * size + 2 * margin));
	}
	
	public void setCharacter(boolean[][] c) {
		character = c;
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.WHITE);
		
		g2.fillRect(margin, margin, DigitizeCfg.COLUMN * size, DigitizeCfg.ROW * size);
		
		g2.setColor(Color.BLACK);
		
		for (int i = margin; i <= DigitizeCfg.COLUMN * size + margin; i += size)
			g2.drawLine(i, margin, i, DigitizeCfg.ROW * size + margin);
		
		for (int i = margin; i <= DigitizeCfg.ROW * size + margin; i += size)
			g2.drawLine(margin, i, DigitizeCfg.COLUMN * size + margin, i);
		
		
		for (int i = 0; i < character.length; i++)
			for (int j = 0; j < character[0].length; j++)
				if (character[i][j])
					g2.fillRect(margin + j * size, margin + i * size, size, size);
	}
    
}
