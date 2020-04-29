package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * Partida de Xadrez
 * 
 * @author szagot
 *
 */
public class ChessMatch {

	private Integer turn;
	private Color currentPlayer;
	private Board board;

	public ChessMatch() {
		// Cria um tabuleiro no tamanho correto
		board = new Board(8, 8);

		// Inicia o turno e a cor do jogador inicial
		turn = 1;
		currentPlayer = Color.WHITE;

		// Faz o setup do tabuleiro
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Retorna a matriz das pe�as de xadrez correspondete � partida atual
	 * 
	 * @return
	 */
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		// L� a matriz de pe�as do tabuleiro para gerar a matriz da partida
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}

		return mat;
	}

	/**
	 * Retorna uma matriz com os movimentos poss�veis de uma pe�a
	 * 
	 * @param sourcePosition
	 * @return
	 */
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);

		return board.piece(position).possibleMoves();
	}

	/**
	 * Faz o movimento da pe�a
	 * 
	 * @param sourcePosition Origem
	 * @param targetPosition Destino
	 * @return
	 */
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		// Verifica se havia uma pe�a na posi��o de origem
		validateSourcePosition(source);

		// Verifica se o destino da pe�a � v�lido
		validateTargetPosition(source, target);

		// Move a pe�a e salva se houver uma pe�a capturada
		Piece capturedPiece = makeMove(source, target);

		// Troca o turno
		nextTurn();

		return (ChessPiece) capturedPiece;
	}

	/**
	 * Faz a valida��o de pe�a de origem
	 * 
	 * @param position
	 */
	private void validateSourcePosition(Position position) {
		// Tem uma pe�a na origem?
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("N�o existe pe�a na posi��o de origem");
		}

		// O jogador atual � o mesmo da pe�a a ser movida?
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("Essa pe�a � do outro jogador");
		}

		// A pe�a pode mover?
		if (!board.piece(position).isThereAnyPossivelMove()) {
			throw new ChessException("Para essa pe�a n�o h� movimentos poss�veis");
		}
	}

	/**
	 * Faz a valida��o da possibilidade da jogada
	 * 
	 * @param source
	 * @param target
	 */
	private void validateTargetPosition(Position source, Position target) {
		// Para a pe�a de origem, a posi��o de destino � um movimento poss�vel?
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A pe�a escolhida n�o pode se mover para a posi��o de destino informada");
		}
	}

	/**
	 * Troca o turno
	 */
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	/**
	 * Faz uma jogada
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private Piece makeMove(Position source, Position target) {
		// Remove a pe�a da posi��o de origem
		Piece p = board.removePiece(source);
		// Remove uma pe�a da posi��o de destino, se houver
		Piece capturedPiece = board.removePiece(target);
		// Coloca a pe�a na posi��o de destino
		board.placePiece(p, target);

		// Retorna a pe�a capturada
		return capturedPiece;
	}

	/**
	 * Informa a posi��o de uma pe�a no tabuleiro usando a nomenclatura de xadrez
	 * 
	 * @param Column
	 * @param row
	 * @param piece
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	/**
	 * Inicia a partida, colocando as pe�as no tabuleiro
	 */
	private void initialSetup() {

		// TODO: testando
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));

	}

}
