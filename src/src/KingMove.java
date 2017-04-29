package src;

public class KingMove implements PieceType {
	
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		if (destX == x && destY == y) {
			return false;
		}
		
		if (((destX >= x-1) && (destX <= x+1)) && ((destY >= y-1) && (destY <= y+1))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean captureCheck(int y, int x, int destY, int destX, char side, Square s) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
