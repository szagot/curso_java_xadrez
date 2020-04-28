package boardgame;

public class Piece {
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

}
