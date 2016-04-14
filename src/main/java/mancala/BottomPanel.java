package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BottomPanel extends JPanel {

	private JPanel pieceInfo;
	private JLabel[] labels;
	private JLabel player1;
	private Font f;

	public BottomPanel() {
		super(new BorderLayout());
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		
		f = new Font("Arial", Font.BOLD, 20);
		labels = new JLabel[7];

		addPieceInfo();
	}

	private void addPieceInfo() {
		player1 = new JLabel("Player 1 > > >", SwingConstants.LEFT);
		player1.setPreferredSize(new Dimension(40, 65));
		player1.setForeground(Color.YELLOW);
		player1.setFont(f);
		add(player1, BorderLayout.PAGE_END);
		
		JPanel numPanel = new Panel(new BorderLayout(), 90, 100);
		pieceInfo = new Panel(new GridLayout(1, 7), 90, 100);
		
		JLabel space = new JLabel(" ");
		space.setPreferredSize(new Dimension(205, 10));
		
		for(int i = 0; i < 7; i++){
			labels[i] = new JLabel("4");
			labels[i].setForeground(Color.MAGENTA);
			labels[i].setFont(new Font("Arial", Font.BOLD, 20));
			pieceInfo.add(labels[i]);
		}
		labels[6].setText("0");
		add(numPanel, BorderLayout.CENTER);
		numPanel.add(space, BorderLayout.WEST);
		numPanel.add(pieceInfo, BorderLayout.CENTER);
	}
	
	public void addValue(int position) {
		int currVal = Integer.parseInt(labels[position].getText());
		labels[position].setText(String.valueOf(++currVal));
		repaint();
	}
	
	public int reset(int position){
		int currVal = Integer.parseInt(labels[position].getText());
		labels[position].setText("0");
		repaint();
		return currVal;
	}
	
	public static void main(String[] args){
		new BoardScreen().setVisible(true);
	}
}
