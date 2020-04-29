package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 * PEÃO
 */
public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		// Iniciando matriz com todas as posições possíveis iniciadas em false
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// Inicia a peça auxiliar
		Position p = new Position(0, 0);

		// É branco?
		if (getColor() == Color.WHITE) {

			// 1 posicao Acima
			p.setValues(position.getRow() - 1, position.getColumn());
			// A posição existe e não tem uma peça lá
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;

				// É primeiro movimento?
				if (getMoveCount() == 0) {
					// Valida 2 posicoes Acima (primeira jogada)
					p.setRow(position.getRow() - 2);
					// A posição existe e não tem uma peça lá
					if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
						mat[p.getRow()][p.getColumn()] = true;
					}
				}
			}

			// 1 posicao Acima à esquerda (comer)
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			// A posição existe e tem uma peça adversária lá
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// 1 posicao Acima à direita (comer)
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			// A posição existe e tem uma peça adversária lá
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

		} else {

			// 1 posicao Acima
			p.setValues(position.getRow() + 1, position.getColumn());
			// A posição existe e não tem uma peça lá
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;

				// É primeiro movimento?
				if (getMoveCount() == 0) {
					// Valida 2 posicoes Acima (primeira jogada)
					p.setRow(position.getRow() + 2);
					// A posição existe e não tem uma peça lá
					if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
						mat[p.getRow()][p.getColumn()] = true;
					}
				}
			}

			// 1 posicao Acima à esquerda (comer)
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			// A posição existe e tem uma peça adversária lá
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// 1 posicao Acima à direita (comer)
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			// A posição existe e tem uma peça adversária lá
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

		}

		return mat;
	}

}
