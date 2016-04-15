package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JComponent;

public class CupGoal extends JComponent {

	private ArrayList<Image> pieces;
	private int originalCount;

	public CupGoal() {
		pieces = new ArrayList<Image>();
		originalCount = 0;
		setPreferredSize(new Dimension(160, 500));
		setBackground(new Color(0, 0, 0, 0));

	}

	public void addPiece(Image image) {
		pieces.add(image);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < pieces.size(); i++) {
			int x = getWidth() / 2;
			int y = getHeight() / 2;
			if (i > 0) {
				int z = i % 13;
				int n = i / 13;
				n -= i;
				switch (z) {
				case 0:
					x -= 10;
					y += 20;
					break;
				case 1:
					x -= 50 + n;
					y -= 70 - n;
					break;
				case 2:
					x -= 40 - n;
					y -= 55 - n;
					break;
				case 3:
					x -= 40 - n;
					y += 25 + n;
					break;
				case 4:
					x -= 10 - n;
					y -= 120 - n;
					break;
				case 5:
					x -= 50 - n;
					y -= 15 + n;
					break;
				case 6:
					x -= 10 - n;
					y -= 75 - n;
					break;
				case 7:
					x -= 15 - n;
					y += 65 + n;
					break;
				case 8:
					x -= 50 - n;
					y += 70 + n;
					break;
				case 9:
					x -= 20 - n;
					y -= 10 - n;
					break;
				case 10:
					x -= 40 - n;
					y -= 130 - n;
					break;
				case 11:
					x -= 50 - n;
					y -= 100 - n;
					break;
				}
			}
			g.drawImage(pieces.get(i), x, y, null);

		}
	}
	
	public static void main(String[] args){
		new BoardScreen().setVisible(true);;
	}
}
