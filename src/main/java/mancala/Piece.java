package mancala;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Piece {

	private String imageSource;

	public Piece(String color) {
		imageSource = "/" + color + ".png";
	}

	public String getImageName() {
		return imageSource;
	}
	
	public Image getImage(){
		 return new ImageIcon(getClass().getResource(imageSource)).getImage();
	}
}
