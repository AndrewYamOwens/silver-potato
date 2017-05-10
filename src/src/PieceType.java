package src;

public interface PieceType {
	/*
	 * Move check is the method that is called to ensure all pieces are moving like they should. 
	 * While each pieces method is unique, they all serve the same purpose and work in about the same way. 
	 * If the move is deemed valid, it returns true else it returns false.
	 */
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl); 
	/*
	 * Capture check is a method that is only used by the pawn given that it needs to be able to capture in ways that it can not move.
	 */
	public boolean captureCheck(int x, int y, int destY, int destX, char side, Square s);
	
	/*
	 * Each piece, except the knight, has a private function that is called clearPath. It takes the current piece array 
	 * and makes sure that all the squares in between the starting location and the destination are clear of other pieces.
	 */
		
}
