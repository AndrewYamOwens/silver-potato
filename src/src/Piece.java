package src;

import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.imageio.ImageIO;



public class Piece {
		
		protected PieceType type;
		
		protected int x;
		protected int y;
		
		protected int destX;
		protected int destY;
		// false = white true = black
		protected char side;
		protected ImageIcon symbol;
		
		protected boolean hasMoved;
		
		// remove before final turn in 
		protected int id;
		protected Square destS;
		protected boolean captured;
		protected char typeC;
		
		public Piece() {
			x = 0;
			y = 0;
			side = '.';
			type = null;
			id = 0;
			
		}
		
		public Piece(int y, int x, char side, PieceType type, int id) {
			this.x = x;
			this.y = y;
			this.side = side;
			this.type = type;
			this.id = id;
			hasMoved = false;
		}
		
		public Piece(String s) {
			String[] token = s.split("/");
			this.id = Integer.parseInt(token[0]);
			this.x = Integer.parseInt(token[1]);
			this.y = Integer.parseInt(token[2]);
			this.type = setPType(token[3]);
			this.side = token[4].charAt(0);
			
			if (token[5] == "t") {
				this.hasMoved = true;
			} else { 
				this.hasMoved = false;
			}
			
		}

		
		
		private PieceType setPType(String s) {
			if (s == "K") {
				KingMove km = new KingMove();
				return km;
			}
			
			if (s == "q") {
				QueenMove qm = new QueenMove();
				return qm;
			}
			
			if (s == "k") {
				KnightMove nm = new KnightMove();
				return nm;
			}
			
			if (s == "r") {
				RookMove rm = new RookMove();
				return rm;
			}
			
			if (s == "b") {
				BishopMove bm = new BishopMove();
				return bm;
			} else {
				PawnMove pm = new PawnMove();
				return pm;
			}

		}

		public boolean checkMove(Piece[] pl) {
			boolean check = type.moveCheck(x, y, destX, destY, side, hasMoved, pl);  ;
			System.out.println("Move Check- X:" + x + " y:" + y);
			if (check == true && type.captureCheck(x, y, destY, destX, side, destS) == true) {
				x = destX;
				y = destY;
				hasMoved = true;
				return true;
			} else {
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
