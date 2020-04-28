package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 * TORRE
 */
public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		// TODO: L�gica tempor�ria para testes
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		return mat;
	}

}
