package src;

public class King extends Piece{

	public King(int x, int y, char side, PieceType type, int i) {
		super(x, y, side, type, i);
		setImage(getSide());
		
		
	}
	private String getSide() {
		String path = null;
		
		if (side == 'w') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\white_king.png";
		}
		if (side == 'b') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\black_king.png";
		}
		return path;
	}
	
}




