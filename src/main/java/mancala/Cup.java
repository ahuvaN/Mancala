package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Cup extends JComponent {

	private ArrayList<Image> pieces;
	protected int originalCount;

	public Cup(int i, MouseListener listener, Piece[] pieceColors) {
		pieces = new ArrayList<Image>();

		setLayout(new FlowLayout()); // TODO
		setName(String.valueOf(i));
		setBackground(new Color(0, 0, 0, 0));
		super.setBackground(new Color(0, 0, 0, 0));

		setPreferredSize(new Dimension(110, 200));
		addMouseListener(listener);
		originalCount = 4;

		for (int x = 0; x < 4; x++) {
			pieces.add(pieceColors[x].getImage());
		}
	}

	public void addPiece(Image image) {
		pieces.add(image);
	}

	public void removePieces() {
		for (Image p : pieces) {
			pieces.remove(p);
		}
	}

	public int count() {
		return pieces.size();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < pieces.size(); i++) {
			int x = getWidth() / 2;
			int y = getHeight() / 2 - 7;
			if (i > 0) {
				int z = i % 5;
				int n = i / 5;
				n -= i;
				switch (z) {
				case 0:
					x += i - 2 *i;
					y += i;
					break;
				case 1:
					x -= 48 + n;
					y -= 40 - n;
					break;
				case 2:
					x -= 5 - n;
					y -= 55 - n;
					break;
				case 3:
					x -= 40 - n;
					y += 5 + n;
					break;
				case 4:
					x -= 10 - n;
					y -= 30 - n;
					break;
				}
			}
			g.drawImage(pieces.get(i), x, y, null);

		}
	}
}
