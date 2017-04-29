package src;

import java.lang.Math;

public class BishopMove implements PieceType {


	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		
		if ( Math.abs(destX - x) == Math.abs(destY - y) && clearPath(x, y, destX, destY, pl) == true) {
			return true;
		} else {
			return false;
		}
	}


	public boolean captureCheck(int y, int x, int destY, int destX, char side, Square s) {
		return false;
	}
	
	private boolean clearPath(int x, int y, int destY, int destX, Piece[] pL) {
		
		if (x < destX) {
			if (y < destY) {
				while (x != destX && y != destY) {
					x++;
					y++;
					for (int i = 0; i < 32; i++) {
						if (pL[i].checkLocation(x, y) == true) {
							return false;
						}
					}
				}
				return true;
			} else {
				while (x != destX && y != destY) {
					x++;
					y--;
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
				while (x != destX && y != destY) {
					x--;
					y++;
					for (int i = 0; i < 32; i++) {
						if (pL[i].checkLocation(x, y) == true) {
							return false;
						}
					}
				}
				return true;
			} else {
				while (x != destX && y != destY) {
					x--;
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

