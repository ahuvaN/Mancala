package mancala;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class StartScreen extends JFrame {

	private JLabel pic;
	private ImageIcon frameIcon;
	private Image img;
	private BoardScreen screen;

	@Inject
	public StartScreen(BoardScreen boardScreen) {
		setTitle("Mancala");
		setSize(800, 615);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen = boardScreen;
		pic = new JLabel();
		pic.setBounds(0, 0, 800, 615);

		setIconImage();

		ImageIcon image = new ImageIcon(img);
		pic.setIcon(image);
		add(pic);
		setVisible(true);
		setResizable(false);
		setLayout(null);

		try {
			Thread.sleep(2000);
			changeIcon("/play.jpg");
			image = new ImageIcon(img);
			pic.setIcon(image);
			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					int x = e.getX();
					int y = e.getY();
					if (x > 325 && x < 475 && y > 255 && y < 320) {
						changeIcon("/players.jpg");
						ImageIcon image = new ImageIcon(img);
						pic.setIcon(image);
						pic.addMouseListener(new MouseListener() {

							@Override
							public void mouseClicked(MouseEvent e) {
								int x = e.getX();
								int y = e.getY();
								if (x > 295 && x < 485 && y > 295 && y < 340) {
									System.out.println(x + ", " + y);
									// BoardScreen p = new BoardScreen(false);
									screen.setComputer(false);
									screen.setVisible(true);
									dispose();
								} else if (x > 321 && y > 219 && x < 479
										&& y < 262) {
									System.out.println(x + ", " + y);
									// BoardScreen p = new BoardScreen(true);
									screen.setComputer(true);
									screen.setVisible(true);
									dispose();
								}
							}

							@Override
							public void mouseEntered(MouseEvent e) {
							}

							@Override
							public void mouseExited(MouseEvent e) {
							}

							@Override
							public void mousePressed(MouseEvent e) {
							}

							@Override
							public void mouseReleased(MouseEvent e) {
							}
						});
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void setIconImage() {
		changeIcon("/mancala.jpg");
		setIconImage(img);
	}

	private void changeIcon(String name) {
		frameIcon = new ImageIcon(getClass().getResource(name));
		img = frameIcon.getImage().getScaledInstance(pic.getWidth(),
				pic.getHeight(), java.awt.Image.SCALE_SMOOTH);
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MancalaModule());
		StartScreen frame = injector.getInstance(StartScreen.class);
	}
}
