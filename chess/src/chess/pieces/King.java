package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 * REI
 */
public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		// TODO: Lógica temporária para testes
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		return mat;
	}

}
