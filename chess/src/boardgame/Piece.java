package boardgame;

public abstract class Piece {
	// Posição da peça
	protected Position position;
	// Tabuleiro
	private Board board;

	/**
	 * Construtor: peça é criada como nula = fora do tabuleiro
	 * 
	 * @param board
	 */
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	/**
	 * O tabuleiro associado a uma peça não deve ser acessível ao jogo
	 * 
	 * @return
	 */
	protected Board getBoard() {
		return board;
	}

	/**
	 * Quais os movimentos possíveis? Deve ser implementado em cada peça
	 * 
	 * @return
	 */
	public abstract boolean[][] possibleMoves();

	/**
	 * Testa os possíveis movimentos baseado em uma posição
	 * 
	 * @param position
	 * @return
	 */
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	/**
	 * Verifica se a peça não está travada
	 * 
	 * @return
	 */
	public boolean isThereAnyPossivelMove() {

		// Pega os movimentos possíveis da peça
		boolean[][] mat = possibleMoves();

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				// O movimento é possível
				if (mat[i][j]) {
					// Já retorna verdadeiro, pois tem pelo menos 1 movimento possível
					return true;
				}
			}
		}

		// Se não achou nenhum movimento, retorna false
		return false;

	}

}
