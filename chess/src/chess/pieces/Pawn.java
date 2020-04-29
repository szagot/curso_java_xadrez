package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

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
			
		}

		return mat;
	}

}
