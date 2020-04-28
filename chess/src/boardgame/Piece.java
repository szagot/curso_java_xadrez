package boardgame;

public abstract class Piece {
	// Posi��o da pe�a
	protected Position position;
	// Tabuleiro
	private Board board;

	/**
	 * Construtor: pe�a � criada como nula = fora do tabuleiro
	 * 
	 * @param board
	 */
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	/**
	 * O tabuleiro associado a uma pe�a n�o deve ser acess�vel ao jogo
	 * 
	 * @return
	 */
	protected Board getBoard() {
		return board;
	}

	/**
	 * Quais os movimentos poss�veis? Deve ser implementado em cada pe�a
	 * 
	 * @return
	 */
	public abstract boolean[][] possibleMoves();

	/**
	 * Testa os poss�veis movimentos baseado em uma posi��o
	 * 
	 * @param position
	 * @return
	 */
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	/**
	 * Verifica se a pe�a n�o est� travada
	 * 
	 * @return
	 */
	public boolean isThereAnyPossivelMove() {

		// Pega os movimentos poss�veis da pe�a
		boolean[][] mat = possibleMoves();

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				// O movimento � poss�vel
				if (mat[i][j]) {
					// J� retorna verdadeiro, pois tem pelo menos 1 movimento poss�vel
					return true;
				}
			}
		}

		// Se n�o achou nenhum movimento, retorna false
		return false;

	}

}
