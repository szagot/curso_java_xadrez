package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 * REI
 */
public class King extends ChessPiece {

	/** Necess�rio para Rocar */
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "R";
	}

	/**
	 * Verifica se o Rei pode mover para um determinada posi��o
	 * 
	 * @param position
	 * @return
	 */
	private boolean canMove(Position position) {
		// Pegando a pe�a do destino
		ChessPiece p = (ChessPiece) getBoard().piece(position);

		// Retorna sse a pe�a que est� l� � advers�ria
		return p == null || p.getColor() != getColor();
	}

	/**
	 * A torre est� dispon�vel para rocar?
	 * 
	 * @return
	 */
	private boolean testRookCastling(Position position) {
		// Pega a pe�a da posi��o informada
		ChessPiece p = (ChessPiece) getBoard().piece(position);

		// Pe�a existe, � uma torre da mesma cor do rei e ainda n�o se moveu?
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		// Iniciando matriz com todas as posi��es poss�veis iniciadas em false
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// Inicia a pe�a auxiliar
		Position p = new Position(0, 0);

		// Acima
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Abaixo
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Esquerda
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Direita
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Acima e a esquerda (Noroeste)
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Acima e a direita (Nordeste)
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Abaixo e a esquerda (Sudoeste)
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Abaixo e a direita (Sudeste)
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// ROQUE: O Rei ainda n�o se moveu e a partida n�o est� em cheque?
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// Roque pequeno
			// Pega a pe�a que est� a 3 casas � direita do rei
			Position posT = new Position(position.getRow(), position.getColumn() + 3);
			// Verifica se est� � uma torre apta a rocar
			if (testRookCastling(posT)) {
				// As casas entre elas est�o vazias?
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					// Coloca a casa de roque como sendo uma alvo v�lido
					mat[p2.getRow()][p2.getColumn()] = true;
				}
			}

			// Roque grande
			// Pega a pe�a que est� a 4 casas � esquerda do rei
			posT = new Position(position.getRow(), position.getColumn() - 4);
			// Verifica se est� � uma torre apta a rocar
			if (testRookCastling(posT)) {
				// As casas entre elas est�o vazias?
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					// Coloca a casa de roque como sendo uma alvo v�lido
					mat[p2.getRow()][p2.getColumn()] = true;
				}
			}
		}

		return mat;
	}

}
