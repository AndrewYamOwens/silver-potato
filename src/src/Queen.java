package src;

public class Queen extends Piece {
	public Queen(int x, int y, char side, PieceType type, int i) {
		super(x, y, side, type, i);
		setImage(getSide());
		
	}
	private String getSide() {
		String path = null;
		if (side == 'w') { 
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\white_queen.png";
		} 
		if (side == 'b') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\black_queen.png";
		}
		
		return path;
	}
	
	
}
