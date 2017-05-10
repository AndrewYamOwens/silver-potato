package src;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;



public abstract class Piece {
		
		protected PieceType type;
		
		protected int x;
		protected int y;
		
		protected int destX;
		protected int destY;

		protected char side;
		protected ImageIcon symbol;
		
		protected boolean hasMoved;
		

		protected int id;
		protected Square destS;
		protected boolean captured;
		protected char typeC;
		
		public Piece() {
			
		}
		
		public Piece(int y, int x, char side, PieceType type, int id) {
			this.x = x;
			this.y = y;
			this.side = side;
			this.type = type;
			this.id = id;
			hasMoved = false;
		}
		
		public Piece(int y, int x, char side, PieceType type, int id, boolean hasMoved) {
			this.x = x;
			this.y = y;
			this.side = side;
			this.type = type;
			this.id = id;
			this.hasMoved = hasMoved;
		}
		
			

		public boolean checkMove(Piece[] pl) {
			boolean check = type.moveCheck(x, y, destX, destY, side, hasMoved, pl);  ;
			System.out.println("Move Check- X:" + x + " y:" + y);
			if (check == true) {
				x = destX;
				y = destY;
				hasMoved = true;
				System.out.println("Piece checkMove = TRUE");
				return true;
			} else {
				System.out.println("Piece checkMove = FALSE");
				return false;
			}
		}
		
		protected void setImage(String path){
			try {
	            symbol = new ImageIcon(ImageIO.read(new File(path)));
	        } catch (IOException ioe) {
	            System.out.println("Unable to load image file.");
	        }
				
		}
		
		public void setdestX(int X) {
			destX = X;
		}
		public void setdestY(int Y) {
			destY = Y;
		}
		public void setdestS(Square s) {
			destS = s;
		}
		public ImageIcon getSymbol() {
			return symbol;
		}
		public int getId() {
			return id;
		}
		public char getColor() {
			return side;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public boolean checkLocation(Piece p) {
			// This breaks things but only sometimes
			// A null pointer exception points to this next line as being the problem
			if (id == p.getId() || side == p.getColor()) {
				return false;
			}
			if (x == p.getX() && y == p.getY()) {
				return true;
			} else {
				return false;
			}
		}
		public boolean checkLocation(int x, int y) {
			if (this.x == x && this.y == y) {
				return true;
			} else {
				return false;
			}
		}
		public void setCaptured(boolean c) {
			captured = c;  
			if (captured == true) {
				x = 9;
				y = 9;
			}
		}
		public boolean getCaptured() {
			return captured;
		}
		public void setPosition(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public boolean getHasMoved() {
			return hasMoved;
		}
		
		public String pL_Entry() {
			String entry = "";
			entry = entry.concat(Integer.toString(id));
			entry = entry + "/";
			entry = entry.concat(Integer.toString(x));
			entry = entry + "/";
			entry = entry.concat(Integer.toString(y));
			entry = entry + "/";
			entry = entry + typeC;
			entry = entry + "/";
			entry = entry + side;
			entry = entry + "/";
			
			if (hasMoved == true) {
				entry = entry + "t";
			} else { 
				entry = entry + "f";
			}
			
			
			return entry;
			
		}
}
