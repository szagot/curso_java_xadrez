package chess;

import boardgame.Position;

/**
 * Posição das peças no tabuleiro com as nomenclaturas de Xadrez
 */
public class ChessPosition {
	private Character column;
	private Integer row;

	public ChessPosition(Character column, Integer row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("Erro na instância da Posição do Xadrez: Valores válidos vão de a1 a h8");
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
	 * Converte a posição de Xadrez para uma posição de Matriz
	 * 
	 * @return
	 */
	protected Position toPosition() {
		/**
		 * Linha: 8 - linha do xadrez
		 * 
		 * (ex: linha 5 do xadrez, é a linha 3 da matriz)
		 *
		 * 
		 * Coluna: Código unicode da coluna - código unicode de 'a'
		 * 
		 * (ex: 'a' - 'a' = 0; 'b' - 'a' = 1; 'c' - 'a' = 3...)
		 */
		return new Position(8 - row, column - 'a');
	}

	/**
	 * Converte uma posição de matriz em uma posição de xadrez
	 * 
	 * @param position
	 * @return
	 */
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		// Esse string vazio serve para forçar o compilador a entender uma concatenação
		// de strings
		return "" + column + row;
	}

}
