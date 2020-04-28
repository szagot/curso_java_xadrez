package boardgame;

public class Piece {
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

}
