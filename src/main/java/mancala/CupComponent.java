package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class CupComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Image piece;
	protected int count;
	protected int originalCount;

	public CupComponent() {
		setLayout(new GridLayout(4, 4));
		setPreferredSize(new Dimension(110, 280));
		piece = new ImageIcon(getClass().getResource("/red.png")).getImage();
		originalCount = count = 4;
	}

	public void setCount(int count) {
		this.count = count;
		repaint();
	}

	public void emptyCup() {
		count = originalCount;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0, j = -14; i < count; i++, j++) {
			if (i < 14) {
				g.drawImage(piece, 10, i * 15, this);
			} else {
				g.drawImage(piece, 30, j * 15, this);

			}
		}
		super.repaint();
	}

}
