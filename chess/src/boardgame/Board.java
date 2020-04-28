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
	 * Retorna a pe�a que est� na linha e coluna informadas
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Piece piece(Integer row, Integer column) {
		return pieces[row][column];
	}

	/**
	 * Sonrecarga: Retorna a pe�a pela posi��o informada
	 * 
	 * @param position
	 * @return
	 */
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}

	/**
	 * Posiciona a pe�a no tabuleiro
	 * 
	 * @param piece
	 * @param position
	 */
	public void placePiece(Piece piece, Position position) {
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

}
