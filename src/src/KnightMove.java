package src;

import java.lang.Math;

public class KnightMove implements PieceType {

	
	public boolean moveCheck(int x, int y, int destX, int destY, char side, boolean hasMoved, Piece[] pl) {
		System.out.println("------Knight moveCheck Start------");
		System.out.println("Start: " + x + "," + y);
		System.out.println("dest: " + destX + "," + destY);
		System.out.println("side: " + side);
		System.out.println("------------");
		System.out.println("Math.abs(destX - x):" + Math.abs(destX - x));
		System.out.println("Math.abs(destY - y):" + Math.abs(destY - y));
		System.out.println("-----------------------");
		
		if (destX == x && destY == y) {
			return false;
		}
		
		if ( ((Math.abs(destX - x)==1) && (Math.abs(destY - y)==2)) ||
				((Math.abs(destX - x)== 2) && (Math.abs(destY - y)== 1)) ) {
			System.out.println("Knight Move Returns True");
			return true;
		} else {
			System.out.println("Knight Move Returns False");
			return false;
		}
	}

	@Override
	public boolean captureCheck(int y, int x, int destY, int destX, char side, Square s) {
		// TODO Auto-generated method stub
		return false;
	}

}
