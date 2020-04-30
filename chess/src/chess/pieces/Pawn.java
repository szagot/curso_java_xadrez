package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * PEÃO
 */
public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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

			// En Passant Branco: Meu peão está na linha 3 da matriz?
			if (position.getRow() == 3) {
				// Verifica se tem uma peça adversária em En Passant
				// Esquerda
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				// Tem uma peça do oponente na posião...
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
				// ...e ela está vulnerável para um En Passant?
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Move a peça para a mesma coluna do peão vulnerável, uma linha acima
					mat[left.getRow() - 1][left.getColumn()] = true;
				}

				// Direita
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				// Tem uma peça do oponente na posião...
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
				// ...e ela está vulnerável para um En Passant?
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					// Move a peça para a mesma coluna do peão vulnerável, uma linha acima
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
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

			// En Passant Preto: Meu peão está na linha 4 da matriz?
			if (position.getRow() == 4) {
				// Verifica se tem uma peça adversária em En Passant
				// Esquerda
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				// Tem uma peça do oponente na posião...
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
				// ...e ela está vulnerável para um En Passant?
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Move a peça para a mesma coluna do peão vulnerável, uma linha acima
					mat[left.getRow() + 1][left.getColumn()] = true;
				}

				// Direita
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				// Tem uma peça do oponente na posião...
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
				// ...e ela está vulnerável para um En Passant?
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					// Move a peça para a mesma coluna do peão vulnerável, uma linha acima
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}

		return mat;
	}

}
