package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	private Color color;

	/**
	 * Cria uma peça de xadrez de acordo com a cor
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

	/**
	 * Pega a posição de uma peça no tabuleiro
	 * 
	 * @return
	 */
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	/**
	 * Verifica a existência de uma peça adversária em uma posição
	 * 
	 * @param position
	 * @return
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);

		// Tem uma peça na posição informada e a cor dela é diferente desta peça?
		return p != null && p.getColor() != color;
	}
}
