package src;

import java.io.File;
import java.io.IOException;

public class Queen extends Piece {
	public Queen(int x, int y, char side, PieceType type, int i) {
		super(x, y, side, type, i);
		typeC = 'q';
		try {
			setImage(getSide());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String getSide() throws IOException {
		String path = null;
		String finalPath = null;
		File currentDir = new File(".");
		String basePath = currentDir.getCanonicalPath();

		
		if (side == 'w') {
			path = "\\src\\piecePictures\\white_queen.png";
			finalPath = basePath.concat(path);
		}
		if (side == 'b') {
			path = "\\src\\piecePictures\\black_queen.png";
			finalPath = basePath.concat(path);
		}
		return finalPath;
	}
	
	
}
