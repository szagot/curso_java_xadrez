package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {
	private Color color;

	/**
	 * Cria uma pe�a de xadrez de acordo com a cor
	 * 
	 * @param board
	 * @param color
	 */
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
