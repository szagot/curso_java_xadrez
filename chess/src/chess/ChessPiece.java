package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

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

	/**
	 * Pega a posi��o de uma pe�a no tabuleiro
	 * 
	 * @return
	 */
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	/**
	 * Verifica a exist�ncia de uma pe�a advers�ria em uma posi��o
	 * 
	 * @param position
	 * @return
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);

		// Tem uma pe�a na posi��o informada e a cor dela � diferente desta pe�a?
		return p != null && p.getColor() != color;
	}
}
