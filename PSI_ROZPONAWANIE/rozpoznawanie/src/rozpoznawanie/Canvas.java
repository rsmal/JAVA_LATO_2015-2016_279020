package rozpoznawanie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import rozpoznawanie.DigitizeCfg;
import rozpoznawanie.Pair;

public class Canvas extends JPanel implements MouseMotionListener, MouseListener {
    
    private static final long serialVersionUID = 1L;
	
	private Point oldPoint;
	private ArrayList<Pair<Point, Point>> pointList;
	private Border characterBorder;
	private DigitizeBorder digitizeBorder;
	private Thumbnail thumb;

	public Canvas() {
		pointList = new ArrayList<Pair<Point, Point>>();
		
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.WHITE);
		
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		if (digitizeBorder != null) {
			g2.setColor(Color.LIGHT_GRAY);
			
			for (Pair<Point, Point> p : digitizeBorder)
				g2.drawLine((int) p.getLeft().getX(), (int) p.getLeft().getY(), (int) p.getRight().getX(), (int) p.getRight().getY());
		}
			
		if (characterBorder != null) {
			g2.setColor(Color.RED);
			
			g2.drawLine(0, characterBorder.getTop(), getWidth(), characterBorder.getTop());
			g2.drawLine(0, characterBorder.getBottom(), getWidth(), characterBorder.getBottom());
			g2.drawLine(characterBorder.getLeft(), 0, characterBorder.getLeft(), getHeight());
			g2.drawLine(characterBorder.getRight(), 0, characterBorder.getRight(), getHeight());
		}

	    //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(Color.BLACK);
	    
	    for (Pair<Point, Point> p : pointList)
	    	g2.drawLine((int) p.getLeft().getX(), (int) p.getLeft().getY(), (int) p.getRight().getX(), (int) p.getRight().getY());
	}
	
	public void clear() {
		pointList.clear();
		
		characterBorder = null;
		digitizeBorder = null;
		
		thumb.setCharacter(new boolean[0][0]);
		
		repaint();
	}
	
	public BufferedImage getImage() {
		BufferedImage img = new BufferedImage(getWidth() + DigitizeCfg.COLUMN, getHeight() + DigitizeCfg.ROW, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = img.createGraphics();
	    paint(g);
	    
	    return img;
	}
	
	
	private Border findBorder(BufferedImage img) {
		int t = 0, b = 0, l = 0, r = 0;

		top:
		for (int i = 0; i < getHeight(); i++)
			for (int j = 0; j < getWidth(); j++)
				if (img.getRGB(j, i) == -16777216) {
					t = i;
					break top;
				}
	    
	    bottom:
	    for (int i = getHeight() - 1; i >= 0; i--)
			for (int j = getWidth() - 1; j >= 0; j--)
				if (img.getRGB(j, i) == -16777216) {
					b = i;
					break bottom;
				}
				
		left:
		for (int i = 0; i < getWidth(); i++)
			for (int j = 0; j < getHeight(); j++)
				if (img.getRGB(i, j) == -16777216) {
					l = i;
					break left;
				}
		
		right:
		for (int i = getWidth() - 1; i >= 0 ; i--)
			for (int j = getHeight() - 1; j >= 0 ; j--)
				if (img.getRGB(i, j) == -16777216) {
					r = i;
					break right;
				}
				
		return new Border(t, b, l, r);
	}
	
	private DigitizeBorder digitize(BufferedImage img) {
		int width = characterBorder.getWidth() / DigitizeCfg.COLUMN;
		int height = characterBorder.getHeight() / DigitizeCfg.ROW;
		
		DigitizeBorder digitizeBorder = new DigitizeBorder();
		
		for (int i = 0; i < DigitizeCfg.ROW; i++) {
			Point l = new Point(characterBorder.getLeft(), characterBorder.getTop() + height * i);
			Point r = new Point(characterBorder.getRight(), characterBorder.getTop() + height * i);
			
			Pair<Point, Point> p = new Pair<Point, Point>(l, r);
			
			digitizeBorder.add(p);
		}
		
		for (int i = 0; i < DigitizeCfg.COLUMN; i++) {
			Point l = new Point(characterBorder.getLeft() + width * i, characterBorder.getTop());
			Point r = new Point(characterBorder.getLeft() + width * i, characterBorder.getBottom());
			
			Pair<Point, Point> p = new Pair<Point, Point>(l, r);
			
			digitizeBorder.add(p);
		}
		
		return digitizeBorder;
	}
	
	public ArrayList<Integer> getInput() {
		boolean[][] character = getDigitizedCharacter();
		ArrayList<Integer> input = new ArrayList<Integer>();
		
		for (int i = 0; i < character.length; i++)
			for (int j = 0; j < character[0].length; j++)
				if (character[i][j])
					input.add(1);
				else
					input.add(0);
		
		return input;
	}
		
	public boolean[][] getDigitizedCharacter() {
		BufferedImage img = getImage();
		
		characterBorder = findBorder(img);
		
		boolean[][] r = getDigitizedCharacter(characterBorder, img);
		
		characterBorder = null;
		
		return r;
	}
	
	public boolean[][] getDigitizedCharacter(Border characterBorder, BufferedImage img) {
		int width = characterBorder.getWidth() / DigitizeCfg.COLUMN + 1;
		int height = characterBorder.getHeight() / DigitizeCfg.ROW + 1;
		
		boolean[][] digit = new boolean[DigitizeCfg.ROW][DigitizeCfg.COLUMN];
		
		for (int i = 0; i < DigitizeCfg.ROW; i++)
			for (int j = 0; j < DigitizeCfg.COLUMN; j++)
				for (int k = characterBorder.getTop() + i * height; k < characterBorder.getTop() + i * height + height; k++)
					for (int l = characterBorder.getLeft() + j * width; l < characterBorder.getLeft() + j * width + width; l++) {
						if (img.getRGB(l, k) == -16777216) {
							digit[i][j] = true;
						}
						
						//digitizeBorder.add(new Pair<Point, Point>(new Point(l, k), new Point(l, k)));
					}
			
		return digit;
	}
	
	public void findCharacter() {
		BufferedImage img = getImage();
		
		findCharacter(img);
	}
	
	public void findCharacter(BufferedImage img) {
		characterBorder = findBorder(img);
		digitizeBorder = digitize(img);
		
		repaint();
	}
	
	public void setThumbnail(Thumbnail t) {
		thumb = t;
	}
	
	
	private class Border {
		
		private int top;
		private int bottom;
		private int left;
		private int right;
		
		public Border(int t, int b, int l, int r) {
			top = t;
			bottom = b;
			left = l;
			right = r;
		}
		
		public int getTop() {
			return top;
		}
		public int getBottom() {
			return bottom;
		}
		public int getLeft() {
			return left;
		}
		public int getRight() {
			return right;
		}
		
		public int getWidth() {
			return right - left;
		}
		
		public int getHeight() {
			return bottom - top;
		}
		
	}
	
	private class DigitizeBorder implements Iterable<Pair<Point, Point>> {
		
		ArrayList<Pair<Point, Point>> list;
		
		public DigitizeBorder() {
			list = new ArrayList<Pair<Point,Point>>();
		}
		
		public void add(Pair<Point, Point> l) {
			list.add(l);
		}

		@Override
		public Iterator<Pair<Point, Point>> iterator() {
			return list.iterator();
		}
		
	}
	
	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		Pair<Point, Point> p = new Pair<Point, Point>(oldPoint, e.getPoint());
		
		pointList.add(p);
		
		oldPoint = e.getPoint();
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		oldPoint = e.getPoint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (thumb != null)
			thumb.setCharacter(getDigitizedCharacter());
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
    
}
