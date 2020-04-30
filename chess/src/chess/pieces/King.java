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

	/** Necessário para Rocar */
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
	 * Verifica se o Rei pode mover para um determinada posição
	 * 
	 * @param position
	 * @return
	 */
	private boolean canMove(Position position) {
		// Pegando a peça do destino
		ChessPiece p = (ChessPiece) getBoard().piece(position);

		// Retorna sse a peça que está lá é adversária
		return p == null || p.getColor() != getColor();
	}

	/**
	 * A torre está disponível para rocar?
	 * 
	 * @return
	 */
	private boolean testRookCastling(Position position) {
		// Pega a peça da posição informada
		ChessPiece p = (ChessPiece) getBoard().piece(position);

		// Peça existe, é uma torre da mesma cor do rei e ainda não se moveu?
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		// Iniciando matriz com todas as posições possíveis iniciadas em false
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		// Inicia a peça auxiliar
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

		// ROQUE: O Rei ainda não se moveu e a partida não está em cheque?
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// Roque pequeno
			// Pega a peça que está a 3 casas à direita do rei
			Position posT = new Position(position.getRow(), position.getColumn() + 3);
			// Verifica se está é uma torre apta a rocar
			if (testRookCastling(posT)) {
				// As casas entre elas estão vazias?
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					// Coloca a casa de roque como sendo uma alvo válido
					mat[p2.getRow()][p2.getColumn()] = true;
				}
			}

			// Roque grande
			// Pega a peça que está a 4 casas à esquerda do rei
			posT = new Position(position.getRow(), position.getColumn() - 4);
			// Verifica se está é uma torre apta a rocar
			if (testRookCastling(posT)) {
				// As casas entre elas estão vazias?
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					// Coloca a casa de roque como sendo uma alvo válido
					mat[p2.getRow()][p2.getColumn()] = true;
				}
			}
		}

		return mat;
	}

}
