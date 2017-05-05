package src;

import java.io.File;
import java.io.IOException;

public class Pawn extends Piece {
	
	public Pawn(int x, int y, char side, PieceType type, int offset) {
		super(x, y, side, type, offset);
		typeC = 'p';
		try {
			setImage(getSide());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Pawn(int x, int y, char side, PieceType type, int i, boolean hasMoved)	{
		super(x, y, side, type, i, hasMoved);
		typeC = 'p';
		try {
			setImage(getSide());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public boolean checkMove(Piece[] pL) {
		boolean check = type.moveCheck(x, y, destX, destY, side, hasMoved, pL);
		System.out.println("-------- Pawn moveCheck end -------");
		System.out.println("Move Check- X:" + x + " y:" + y);
		if (check == false) {
			check = type.captureCheck(x, y, destX, destY, side, destS);
		}
		
		if (check == false) {
			return false;
		} else {
			x = destX;
			y = destY;
			hasMoved = true;
			return true;
		}
	}
	
	private String getSide() throws IOException {
		String path = null;
		String finalPath = null;
		File currentDir = new File(".");
		String basePath = currentDir.getCanonicalPath();

		if (side == 'w') {
			path = "\\src\\piecePictures\\white_pawn.png";
			finalPath = basePath.concat(path);
		}
		if (side == 'b') {
			path = "\\src\\piecePictures\\black_pawn.png";
			finalPath = basePath.concat(path);
		}
		return finalPath;
	}

}
