package src;

public class Pawn extends Piece {
	
	public Pawn(int x, int y, char side, PieceType type, int offset) {
		super(x, y, side, type, offset);
		setImage(getSide());
		
	}
	
	public boolean checkMove(Piece[] pL) {
		boolean check = type.moveCheck(x, y, destX, destY, side, hasMoved, pL);
		System.out.println("-------- Pawn moveCheck end -------");
		System.out.println("Move Check- X:" + x + " y:" + y);
		if (check == false) {
			check = type.captureCheck(x, y, destX, destY, side, destS);
		}
		
		if (check == false) {
			return false;
		} else {
			x = destX;
			y = destY;
			hasMoved = true;
			return true;
		}
	}
	
	private String getSide() {
		String path = null;
		if (side == 'w') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\white_pawn.png";
		} 
		if (side == 'b') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\black_pawn.png";
		}
		return path;
	}

}
