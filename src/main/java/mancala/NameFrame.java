package mancala;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class NameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel playersPanel, playerPanel1, playerPanel2;
	private JLabel playerLabel1, playerLabel2, backgroundLabel;
	private JTextField playerField1, playerField2;
	private JButton okayButton;
	private String name1, name2;

	@Inject
	public NameFrame() {
		setTitle("Add Names");
		setSize(900, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(false);

		backgroundLabel = new JLabel(new ImageIcon(new ImageIcon(getClass()
				.getResource("/background4.jpg")).getImage().getScaledInstance(
						getWidth(), getHeight(), 10)));

		playersPanel = new JPanel();
		playerPanel1 = new JPanel();
		playerPanel2 = new JPanel();
		playerLabel1 = new JLabel("Player 1");
		playerLabel2 = new JLabel("Player 2");
		playerField1 = new JTextField();
		playerField2 = new JTextField();
		okayButton = new JButton();

		addListener();
		addFormat();

	}

	private void addFormat() {
		backgroundLabel.setLayout(new BorderLayout());

		playersPanel.setLayout(new FlowLayout());
		playersPanel.setOpaque(true);
		playersPanel.setBackground(Color.black);
		playersPanel.setPreferredSize(new Dimension(40, 100));

		playerPanel1.setLayout(new FlowLayout());
		playerPanel1.setOpaque(true);
		playerPanel1.setBackground(Color.black);

		playerPanel2.setLayout(new FlowLayout());
		playerPanel2.setOpaque(true);
		playerPanel2.setBackground(Color.black);

		Font font1 = new Font("Calibri", Font.BOLD, 40);
		Font font2 = new Font("Calibri", Font.BOLD, 30);

		playerLabel1.setSize(60, 20);
		playerLabel1.setFont(font1);
		playerLabel1.setForeground(Color.yellow);

		playerLabel2.setSize(60, 20);
		playerLabel2.setFont(font1);
		playerLabel2.setForeground(Color.yellow);

		playerField1.setColumns(10);
		playerField1.setFont(font2);
		playerField1.setBackground(Color.darkGray);
		playerField1.setForeground(Color.orange);

		playerField2.setColumns(10);
		playerField2.setFont(font2);
		playerField2.setBackground(Color.darkGray);
		playerField2.setForeground(Color.orange);

		okayButton.setIcon(new ImageIcon(formatIcon(80, 80, getClass()
				.getResource("/yellow.png"))));
		okayButton.setBackground(Color.black);
		okayButton.setBorder(new LineBorder(Color.yellow, 1, true));

		playerPanel1.add(playerLabel1);
		playerPanel1.add(playerField1);
		playerPanel2.add(playerLabel2);
		playerPanel2.add(playerField2);
		playersPanel.add(playerPanel1);
		playersPanel.add(playerPanel2);
		playersPanel.add(okayButton);
		backgroundLabel.add(playersPanel, BorderLayout.PAGE_END);
		add(backgroundLabel);
	}

	public Image formatIcon(int width, Integer height, URL image) {
		ImageIcon icon = new ImageIcon(image);
		Image img = icon.getImage().getScaledInstance(width, height,
				java.awt.Image.SCALE_SMOOTH);
		return img;
	}

	private void addListener() {
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				name1 = playerField1.getText().trim().toUpperCase();
				name2 = playerField2.getText().trim().toUpperCase();
				if (name1.length() == 0 || name2.length() == 0) {
					JOptionPane.showMessageDialog(null,
							"You must enter a name for both players!!",
							"WARNING!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (name1.length() > 10 || name2.length() > 10) {
					JOptionPane.showMessageDialog(null,
							"Name can't be longer than 10 digits", "WARNING!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				dispose();
				//BoardGuiPix gui = new BoardGuiPix(name1, name2);
				//gui.setVisible(true);
			}

		});

	}

	public static void main(String[] args) {
		// new NameFrame().setVisible(true);

		Injector injector = Guice.createInjector(new MancalaModule());
		NameFrame frame = injector.getInstance(NameFrame.class);
		frame.setVisible(true);
		// new NameFrame().setVisible(true);
		// injector.Inject...
		// new BoardGuiNumbers("a", "b").setVisible(true);
	}

}
