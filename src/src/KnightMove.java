package src;

import java.lang.Math;

public class KnightMove implements PieceType {

	
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		if (destX == x && destY == y) {
			return false;
		}
		
		if ( (Math.abs(destX - x)==1) && (Math.abs(destY - y)==2) ||
				((Math.abs(destX - x)== 2) && (Math.abs(destY - y)== 1)) ) {
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
