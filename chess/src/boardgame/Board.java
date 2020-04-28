package boardgame;

public class Board {

	private Integer rows;
	private Integer columns;
	// A pe�a � uma mztriz no tabuleiro
	private Piece[][] pieces;

	/**
	 * O tabuleiro � criado com o n�mero de linhas e colunas informado
	 * 
	 * @param rows
	 * @param columns
	 */
	public Board(Integer rows, Integer columns) {
		// Verifica se os dados est�o corretos
		if (rows < 2 || columns < 2) {
			throw new BoardException("Erro ao criar o tabuleiro: � ness�rio que haja ao menos 2 linhas e 2 colunas.");
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
	 * Retorna a pe�a que est� na linha e coluna informadas
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece piece(Integer row, Integer column) {
		// A posi��o informada existe?
		if (!positionExists(row, column)) {
			throw new BoardException("Posi��o inexistente no tabuleiro");
		}

		return pieces[row][column];
	}

	/**
	 * Sonrecarga: Retorna a pe�a pela posi��o informada
	 * 
	 * @param position
	 * @return
	 */
	public Piece piece(Position position) {
		return piece(position.getRow(), position.getColumn());
	}

	/**
	 * Posiciona a pe�a no tabuleiro
	 * 
	 * @param piece
	 * @param position
	 */
	public void placePiece(Piece piece, Position position) {
		// A posi��o informada est� sem pe�a?
		if (thereIsAPiece(position)) {
			throw new BoardException("J� existe uma pe�a na posi��o " + position);
		}

		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	/**
	 * Verifica se uma posi��o existe na linha e coluna informadas
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	/**
	 * Verifica se a posi��o existe
	 * 
	 * @param position
	 * @return
	 */
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	/**
	 * Verifica se tem uma pe�a na posi��o
	 * 
	 * @param position
	 * @return
	 */
	public boolean thereIsAPiece(Position position) {
		// A posi��o informada existe?
		if (!positionExists(position)) {
			throw new BoardException("Posi��o inexistente no tabuleiro");
		}

		return piece(position) != null;
	}
}
