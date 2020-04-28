package chess.pieces;

import boardgame.Board;
import boardgame.Position;
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
		// Iniciando matriz com todas as posi��es poss�veis iniciadas em false
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Verificando acima da Torre:
		p.setValues(position.getRow() - 1, position.getColumn());
		// Verifica todas as casas acima enquanto exista a posi��o e n�o tem pe�a nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posi��o v�lida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setRow(p.getRow() - 1);
		}
		// Testa se a casa que parou tem uma pe�a advers�ria
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Verificando a esquerda da Torre:
		p.setValues(position.getRow(), position.getColumn() - 1);
		// Verifica todas as casas acima enquanto exista a posi��o e n�o tem pe�a nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posi��o v�lida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setColumn(p.getColumn() - 1);
		}
		// Testa se a casa que parou tem uma pe�a advers�ria
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Verificando a direita da Torre:
		p.setValues(position.getRow(), position.getColumn() + 1);
		// Verifica todas as casas acima enquanto exista a posi��o e n�o tem pe�a nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posi��o v�lida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setColumn(p.getColumn() + 1);
		}
		// Testa se a casa que parou tem uma pe�a advers�ria
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Verificando abaixo da Torre:
		p.setValues(position.getRow() + 1, position.getColumn());
		// Verifica todas as casas acima enquanto exista a posi��o e n�o tem pe�a nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posi��o v�lida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setRow(p.getRow() + 1);
		}
		// Testa se a casa que parou tem uma pe�a advers�ria
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		return mat;
	}

}
