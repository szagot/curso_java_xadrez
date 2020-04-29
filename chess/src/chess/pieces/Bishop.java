package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		// Iniciando matriz com todas as posições possíveis iniciadas em false
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Verificando Noroeste do Bispo:
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		// Verifica todas as casas acima enquanto exista a posição e não tem peça nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posição válida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}
		// Testa se a casa que parou tem uma peça adversária
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Verificando a nordeste do Bispo:
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		// Verifica todas as casas acima enquanto exista a posição e não tem peça nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posição válida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}
		// Testa se a casa que parou tem uma peça adversária
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Verificando a Sudeste do Bispo:
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		// Verifica todas as casas acima enquanto exista a posição e não tem peça nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posição válida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}
		// Testa se a casa que parou tem uma peça adversária
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Verificando Sudoeste do Bispo:
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		// Verifica todas as casas acima enquanto exista a posição e não tem peça nela
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			// Posição válida para se mover
			mat[p.getRow()][p.getColumn()] = true;

			// Move uma casa acima
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}
		// Testa se a casa que parou tem uma peça adversária
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}

}