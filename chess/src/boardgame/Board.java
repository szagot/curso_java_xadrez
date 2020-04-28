package boardgame;

public class Board {

	private Integer rows;
	private Integer columns;
	// A peça é uma mztriz no tabuleiro
	private Piece[][] pieces;

	/**
	 * O tabuleiro é criado com o número de linhas e colunas informado
	 * 
	 * @param rows
	 * @param columns
	 */
	public Board(Integer rows, Integer columns) {
		// Verifica se os dados estão corretos
		if (rows < 2 || columns < 2) {
			throw new BoardException("Erro ao criar o tabuleiro: É nessário que haja ao menos 2 linhas e 2 colunas.");
		}

		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

	/**
	 * Retorna a peça que está na linha e coluna informadas
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece piece(Integer row, Integer column) {
		// A posição informada existe?
		if (!positionExists(row, column)) {
			throw new BoardException("Posição inexistente no tabuleiro");
		}

		return pieces[row][column];
	}

	/**
	 * Sonrecarga: Retorna a peça pela posição informada
	 * 
	 * @param position
	 * @return
	 */
	public Piece piece(Position position) {
		return piece(position.getRow(), position.getColumn());
	}

	/**
	 * Posiciona a peça no tabuleiro
	 * 
	 * @param piece
	 * @param position
	 */
	public void placePiece(Piece piece, Position position) {
		// A posição informada está sem peça?
		if (thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça na posição " + position);
		}

		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	/**
	 * Verifica se uma posição existe na linha e coluna informadas
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	/**
	 * Verifica se a posição existe
	 * 
	 * @param position
	 * @return
	 */
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	/**
	 * Verifica se tem uma peça na posição
	 * 
	 * @param position
	 * @return
	 */
	public boolean thereIsAPiece(Position position) {
		// A posição informada existe?
		if (!positionExists(position)) {
			throw new BoardException("Posição inexistente no tabuleiro");
		}

		return piece(position) != null;
	}
}
