package src;

public class RookMove implements PieceType {


	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		if (destX == x && destY == y) {
			return false;
		}
		
		if ((destX == x || destY == y) && clearPath(x, y, destX, destY, pl)) {
			return true;
		} else {
			return false;
		}
	}
	

	public boolean captureCheck(int y, int x, int destY, int destX, char side, Square s) {
		return false;
	}

	private boolean clearPath(int x, int y, int destY, int destX, Piece[] pL) {
	
		if (x != destX) {	
			if (x < destX) {
				while (x != destX) {
					x++;
					for (int i = 0; i < 32; i++) {
						if (pL[i].checkLocation(x, y) == true) {
							return false;
						}
					}
				}
				return true;
			} else {
				while (x != destX) {
					x--;
					for (int i = 0; i < 32; i++) {
						if (pL[i].checkLocation(x, y) == true) {
							return false;
						}
					}
				}
				return true;
			}
		} else {
			if (y < destY) {
				while (y != destY) {
					y++;
					for (int i = 0; i < 32; i++) {
						if (pL[i].checkLocation(x, y) == true) {
							return false;
						}
					}
				}
				return true;
			} else {
				while (y != destY) {
					y--;
					for (int i = 0; i < 32; i++) {
						if (pL[i].checkLocation(x, y) == true) {
							return false;
						}
					}
				}
				return true;
			}
		}
	}

}
	
