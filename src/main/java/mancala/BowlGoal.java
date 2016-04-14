package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JComponent;

public class BowlGoal extends JComponent {

	private ArrayList<Image> pieces;
	private int originalCount;

	public BowlGoal() {
		pieces = new ArrayList<Image>();
		originalCount = 0;
		setPreferredSize(new Dimension(160, 500));
		setBackground(new Color(0,0,0,0));
		
	}

	public void addPiece(Image image/* Piece.getImage(); */) {
		pieces.add(image);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < pieces.size(); i++) {
			g.drawImage(pieces.get(i), i * 10 + 2, i * 10 + 2, null);
		}
	}
}
