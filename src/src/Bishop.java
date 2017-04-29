package src;

public class Bishop extends Piece {
	
	public Bishop(int x, int y, char side, PieceType type, int i) {
		super(x, y, side, type, i);
		setImage(getSide());
		
	}
	private String getSide() {
		String path = null;
		if (side == 'w') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\white_bishop.png";
		} 
		if (side == 'b') {
			path = "C:\\Users\\Yam\\workspace\\Chess\\src\\piecePictures\\black_bishop.png";
		}
		
		return path;
	}
	
}
