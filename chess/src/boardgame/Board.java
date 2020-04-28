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
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	/**
	 * Retorna a peça que está na linha e coluna informadas
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece piece(Integer row, Integer column) {
		return pieces[row][column];
	}

	/**
	 * Sonrecarga: Retorna a peça pela posição informada
	 * 
	 * @param position
	 * @return
	 */
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}

	/**
	 * Posiciona a peça no tabuleiro
	 * 
	 * @param piece
	 * @param position
	 */
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

}
