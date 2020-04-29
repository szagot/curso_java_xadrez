package chess;

import boardgame.Position;

/**
 * Posi��o das pe�as no tabuleiro com as nomenclaturas de Xadrez
 */
public class ChessPosition {
	private Character column;
	private Integer row;

	public ChessPosition(Character column, Integer row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Erro na inst�ncia da Posi��o do Xadrez: Valores v�lidos v�o de a1 a h8");
		}

		this.column = column;
		this.row = row;
	}

	public Character getColumn() {
		return column;
	}

	public Integer getRow() {
		return row;
	}

	/**
	 * Converte a posi��o de Xadrez para uma posi��o de Matriz
	 * 
	 * @return
	 */
	protected Position toPosition() {
		/**
		 * Linha: 8 - linha do xadrez
		 * 
		 * (ex: linha 5 do xadrez, � a linha 3 da matriz)
		 *
		 * 
		 * Coluna: C�digo unicode da coluna - c�digo unicode de 'a'
		 * 
		 * (ex: 'a' - 'a' = 0; 'b' - 'a' = 1; 'c' - 'a' = 3...)
		 */
		return new Position(8 - row, column - 'a');
	}

	/**
	 * Converte uma posi��o de matriz em uma posi��o de xadrez
	 * 
	 * @param position
	 * @return
	 */
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		// Esse string vazio serve para for�ar o compilador a entender uma concatena��o
		// de strings
		return "" + column + row;
	}

}
