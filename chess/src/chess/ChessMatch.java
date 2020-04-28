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

	private Board board;

	public ChessMatch() {
		// Cria um tabuleiro no tamanho correto
		board = new Board(8, 8);

		// Faz o setup do tabuleiro
		initialSetup();
	}

	/**
	 * Retorna a matriz das peças de xadrez correspondete à partida atual
	 * 
	 * @return
	 */
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		// Lê a matriz de peças do tabuleiro para gerar a matriz da partida
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}

		return mat;
	}

	/**
	 * Faz o movimento da peça
	 * 
	 * @param sourcePosition Origem
	 * @param targetPosition Destino
	 * @return
	 */
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		// Verifica se havia uma peça na posição de origem
		validateSourcePosition(source);

		// Verifica se o destino da peça é válido
		validateTargetPosition(source, target);

		// Move a peça e salva se houver uma peça capturada
		Piece capturedPiece = makeMove(source, target);

		return (ChessPiece) capturedPiece;
	}

	/**
	 * Faz a validação de peça de origem
	 * 
	 * @param position
	 */
	private void validateSourcePosition(Position position) {
		// Tem uma peça na origem?
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não existe peça na posição de origem");
		}

		// A peça pode mover?
		if (!board.piece(position).isThereAnyPossivelMove()) {
			throw new ChessException("Para essa peça não há movimentos possíveis");
		}
	}

	/**
	 * Faz a validação da possibilidade da jogada
	 * 
	 * @param source
	 * @param target
	 */
	private void validateTargetPosition(Position source, Position target) {
		// Para a peça de origem, a posição de destino é um movimento possível?
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode se mover para a posição de destino informada");
		}
	}

	private Piece makeMove(Position source, Position target) {
		// Remove a peça da posição de origem
		Piece p = board.removePiece(source);
		// Remove uma peça da posição de destino, se houver
		Piece capturedPiece = board.removePiece(target);
		// Coloca a peça na posição de destino
		board.placePiece(p, target);

		// Retorna a peça capturada
		return capturedPiece;
	}

	/**
	 * Informa a posição de uma peça no tabuleiro usando a nomenclatura de xadrez
	 * 
	 * @param Column
	 * @param row
	 * @param piece
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	/**
	 * Inicia a partida, colocando as peças no tabuleiro
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
