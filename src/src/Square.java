package src;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Square {

		protected boolean occupied;
		protected Piece piece;
		public JButton button;
		public ImageIcon currentIcon;
		protected int x;
		protected int y;
		private boolean selected = false;
		private char color;

		public Square(JButton button, int x, int y, char color) {
			this.button = button;
			this.x = x;
			this.y = y;
			this.color = color;
			currentIcon = null;
			button.setIcon(currentIcon);
			
			
			
		}
		
		public void setOccupied(boolean Occupied) {
			occupied = Occupied;
		}
		public boolean getOccupied() {
			return occupied;
		}
		
		public ImageIcon getIcon() {
			return currentIcon;
		}
		public void updateIcon() {
			if (piece != null) {
				setIcon(piece.getSymbol());
			}
			if (piece == null) {
				button.setIcon(null);
			}
		}
		
		public void setIcon(ImageIcon icon) {
			this.currentIcon = icon;
			this.button.setIcon(currentIcon);
		}
		public JButton getButton() {
			return button;
		}
		
		public Piece getPiece() {
			return piece;
		}
		public void setPiece(Piece p) {
			if (p != null) {
				occupied = true;
			}
			piece = p;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getPieceId() {
			return piece.getId();
		}
		public char getPieceSide() {
			return piece.getColor();
		}
		public boolean hasMoved() {
			return piece.getHasMoved();
		}
		public void iconSize() {
			int x = currentIcon.getIconWidth(); 
			int y = currentIcon.getIconHeight();
		}
		
		public void toggleSelected() {
			if (selected == false) {
				button.setBackground(Color.YELLOW);
				selected = true;
			} else {
				if (color == 'w') {
					button.setBackground(Color.WHITE);
					selected = false;
				} else {
					button.setBackground(Color.GRAY);
					selected = false;
				}
			}
		}
}
