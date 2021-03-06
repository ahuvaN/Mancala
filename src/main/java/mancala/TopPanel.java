package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopPanel extends JPanel {

	private JPanel pieceInfo;
	protected JLabel[] labels;
	private JLabel player2;
	private String repeat;
	private Font f;
	private boolean computer;

	public TopPanel(boolean cpu) {
		super(new BorderLayout());
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);

		f = new Font("Arial", Font.BOLD, 20);
		labels = new JLabel[7];
		computer = cpu;

		addPieceInfo();
	}

	private void addPieceInfo() {
		repeat = IntStream.range(0, 56).mapToObj(x -> "< ")
				.collect(Collectors.joining());
		if (computer)
			player2 = new JLabel(repeat + "< < CPU");
		else
			player2 = new JLabel(repeat + "Player 2");
		player2.setPreferredSize(new Dimension(40, 45));
		player2.setForeground(Color.YELLOW);
		player2.setFont(f);
		add(player2, BorderLayout.PAGE_START);

		JPanel numPanel = new Panel(new BorderLayout(), 0, 105);
		pieceInfo = new Panel(new GridLayout(1, 7, 0, 6), 110, 100);

		JLabel space = new JLabel(" ");
		space.setPreferredSize(new Dimension(90, 10));
		JLabel space2 = new JLabel(" ");
		space2.setPreferredSize(new Dimension(125, 4));

		for (int i = 0; i < 7; i++) {
			labels[i] = new JLabel("4");
			labels[i].setForeground(Color.MAGENTA);
			labels[i].setFont(f);
			pieceInfo.add(labels[i]);
		}
		labels[0].setText("0");
		add(numPanel, BorderLayout.CENTER);
		numPanel.add(space, BorderLayout.WEST);
		numPanel.add(pieceInfo, BorderLayout.CENTER);
		numPanel.add(space2, BorderLayout.EAST);
	}

	public void highlight() {
		player2.setForeground(Color.BLUE);
		player2.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
	}

	public void unHighlight() {
		player2.setForeground(Color.YELLOW);
		player2.setBorder(BorderFactory.createEmptyBorder());
	}

	public void addValue(int position) {
		int currVal = Integer.parseInt(labels[position].getText());
		labels[position].setText(String.valueOf(++currVal));
		repaint();
	}

	public int reset(int position) {
		int currVal = Integer.parseInt(labels[position].getText());
		labels[position].setText("0");
		repaint();
		return currVal;
	}

}
