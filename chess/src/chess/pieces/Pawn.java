package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * PE�O
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
		// Iniciando matriz com todas as posi��es poss�veis iniciadas em false
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// Inicia a pe�a auxiliar
		Position p = new Position(0, 0);

		// � branco?
		if (getColor() == Color.WHITE) {
			// 1 posicao Acima
			p.setValues(position.getRow() - 1, position.getColumn());
			// A posi��o existe e n�o tem uma pe�a l�
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;

				// � primeiro movimento?
				if (getMoveCount() == 0) {
					// Valida 2 posicoes Acima (primeira jogada)
					p.setRow(position.getRow() - 2);
					// A posi��o existe e n�o tem uma pe�a l�
					if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
						mat[p.getRow()][p.getColumn()] = true;
					}
				}
			}

			// 1 posicao Acima � esquerda (comer)
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			// A posi��o existe e tem uma pe�a advers�ria l�
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// 1 posicao Acima � direita (comer)
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			// A posi��o existe e tem uma pe�a advers�ria l�
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// En Passant Branco: Meu pe�o est� na linha 3 da matriz?
			if (position.getRow() == 3) {
				// Verifica se tem uma pe�a advers�ria em En Passant
				// Esquerda
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				// Tem uma pe�a do oponente na posi�o...
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
				// ...e ela est� vulner�vel para um En Passant?
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Move a pe�a para a mesma coluna do pe�o vulner�vel, uma linha acima
					mat[left.getRow() - 1][left.getColumn()] = true;
				}

				// Direita
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				// Tem uma pe�a do oponente na posi�o...
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
				// ...e ela est� vulner�vel para um En Passant?
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					// Move a pe�a para a mesma coluna do pe�o vulner�vel, uma linha acima
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		} else {
			// 1 posicao Acima
			p.setValues(position.getRow() + 1, position.getColumn());
			// A posi��o existe e n�o tem uma pe�a l�
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;

				// � primeiro movimento?
				if (getMoveCount() == 0) {
					// Valida 2 posicoes Acima (primeira jogada)
					p.setRow(position.getRow() + 2);
					// A posi��o existe e n�o tem uma pe�a l�
					if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
						mat[p.getRow()][p.getColumn()] = true;
					}
				}
			}

			// 1 posicao Acima � esquerda (comer)
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			// A posi��o existe e tem uma pe�a advers�ria l�
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// 1 posicao Acima � direita (comer)
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			// A posi��o existe e tem uma pe�a advers�ria l�
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			// En Passant Preto: Meu pe�o est� na linha 4 da matriz?
			if (position.getRow() == 4) {
				// Verifica se tem uma pe�a advers�ria em En Passant
				// Esquerda
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				// Tem uma pe�a do oponente na posi�o...
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
				// ...e ela est� vulner�vel para um En Passant?
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					// Move a pe�a para a mesma coluna do pe�o vulner�vel, uma linha acima
					mat[left.getRow() + 1][left.getColumn()] = true;
				}

				// Direita
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				// Tem uma pe�a do oponente na posi�o...
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
				// ...e ela est� vulner�vel para um En Passant?
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					// Move a pe�a para a mesma coluna do pe�o vulner�vel, uma linha acima
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}

		return mat;
	}

}
