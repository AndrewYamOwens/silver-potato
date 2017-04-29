package src;

public class QueenMove implements PieceType {

	
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		System.out.println("x:" + x + " y:" + y);
		System.out.println("destX:" + destX + " destY:" + destY);
		
		if (destX == x && destY == y) {
			return false;
		}
		
		if ((destX == x || destY == y) ||  Math.abs(destX - x) == Math.abs(destY - y)) {
			if (clearPath(x, y, destY, destX, pl) == true) {
			System.out.println("abs x:" + Math.abs(destX - x));
			System.out.println("abs y:" + Math.abs(destY - y));
			return true;
			} else {
				return false;
			}
		} else {
			System.out.println("abs x:" + Math.abs(destX - x));
			System.out.println("abs y:" + Math.abs(destY - y));
			return false;
	
		}
	}

	@Override
	public boolean captureCheck(int x, int y, int destY, int destX, char side, Square s) {
		System.out.println("Side:" + side);
		if (s.getOccupied() == true) {System.out.println("Captured Piece Side:" + s.getPieceSide()); }
		if (s.getOccupied() == false) {
			return true;
		} 
		if (s.getPieceSide() == side) {
			return false;
		}
		return true;
		
		
	}
	
	private boolean clearPath(int x, int y, int destY, int destX, Piece[] pL) {
		//bishop move's clarPath
		if  (Math.abs(destX - x) == Math.abs(destY - y)) {
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
		//rook move's clearPath
		} else {
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
}
