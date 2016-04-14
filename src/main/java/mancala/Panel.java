package mancala;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class Panel extends JPanel {

	public Panel(LayoutManager layout, int dimenX, int dimenY){
		super(layout);
		setBackground(new Color(0,0,0,0));
		setPreferredSize(new Dimension(dimenX, dimenY));
	}
}
