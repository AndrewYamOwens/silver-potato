package src;

public class PawnMove implements PieceType {

//Still needs work.
	
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		System.out.println("------Pawn moveCheck Start------");
		System.out.println("Start: " + x + "," + y);
		System.out.println("dest: " + destX + "," + destY);
		System.out.println("side: " + side);
		System.out.println("------------");
		
		
		if (destX == x && destY == y) {
			return false;
		}
		System.out.println("x:" + x + " y:" + y);
		System.out.println("destX:" + destX + " destY:" + destY);
		
		if (destX != x) {
			return false;
		}
		
		if (hasMoved == false) {
			if (side == 'w') {
				if ((destY == (y + 1) || destY == (y + 2)) && clearPath(x,y,destX,destY,pl,side) == true) {
					return true;
				} else {
					return false;
				}
	 		} else {
	 			if ((destY == (y - 1) || destY == (y - 2)) && clearPath(x,y,destX,destY,pl,side) == true) {
	 				return true;
	 			} else {
	 				return false;
	 			}
	 		}
		}
		if (side == 'w') {
			if ((destY == (y + 1) && clearPath(x,y,destX,destY,pl,side) == true)) {
				return true;
			} else {
				return false;
			}
 		} else {
 			if ((destY == (y - 1)) && clearPath(x,y,destX,destY,pl,side) == true) {
 				return true;
 			} else {
 				return false;
 			}
 		}
		
	}


	public boolean captureCheck(int x, int y, int destX, int destY, char side, Square s) {

		System.out.println("------------");
		System.out.println("Start: " + x + "," + y);
		System.out.println("dest: " + destX + "," + destY);
		System.out.println("side: " + side);
		System.out.println("is occupied: " + s.getOccupied());
		System.out.println("------------");
		
		if (s.getOccupied() == true) {
				if (s.getPieceSide() != side) {
					if (side == 'w') {
						if (destY == (y+1) && ((destX == (x+1)) || (destX == (x-1)))) {
							return true;
						} else { 
							return false;
						}
					} else {
						if (destY == (y-1) && ((destX == (x+1)) || (destX == (x-1)))) {
							return true;
						} else {
							return false;
						}
					}
				} else {
					return false;
				}
			
		
			} else {
				return false;
			}
	}

	private boolean clearPath(int x, int y, int destX, int destY, Piece[] pL, char side) {
		System.out.println("------------");
		System.out.println("Start: " + x + "," + y);
		System.out.println("dest: " + destX + "," + destY);
		System.out.println("side: " + side);
		System.out.println("------------");
		
		if (x == destX && side == 'w') {
			while (y != destY) {
				y++;
				for (int i = 0; i < 32; i++) {
					System.out.println("pl[" + i +"]: (" + pL[i].getX() + "," + pL[i].getY() + ")");
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




