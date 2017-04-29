package src;

public interface PieceType {
	
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl); 
	public boolean captureCheck(int x, int y, int destY, int destX, char side, Square s);
		
}
