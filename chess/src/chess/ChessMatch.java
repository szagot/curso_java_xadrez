package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
	private boolean check;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

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

	public boolean getCheck() {
		return check;
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
	 * Retorna uma matriz com os movimentos possíveis de uma peça
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

		// Verifica se colocou o proprio jogador em check
		if (testCheck(currentPlayer)) {
			// Desfaz o movimento
			undoMove(source, target, capturedPiece);
			// E lança uma exceção
			throw new ChessException("Você não pode se colocar em check!");
		}

		// O oponente ficou em check?
		check = testCheck(opponent(currentPlayer));

		// Troca o turno
		nextTurn();

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

		// O jogador atual é o mesmo da peça a ser movida?
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("Essa peça é do outro jogador");
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

	/**
	 * Troca o turno
	 */
	private void nextTurn() {
		turn++;
		currentPlayer = opponent(currentPlayer);
	}

	/**
	 * Faz uma jogada
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private Piece makeMove(Position source, Position target) {
		// Remove a peça da posição de origem
		Piece p = board.removePiece(source);
		// Remove uma peça da posição de destino, se houver
		Piece capturedPiece = board.removePiece(target);
		// Coloca a peça na posição de destino
		board.placePiece(p, target);

		// Faz o controle da peça capturada
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// Retorna a peça capturada
		return capturedPiece;
	}

	/**
	 * Desfaz um movimento
	 * 
	 * @param source
	 * @param target
	 * @param capturedPiece
	 */
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		// Remove a peça da posição de destino
		Piece p = board.removePiece(target);
		// Coloca a peça de volta na posição de origem
		board.placePiece(p, source);

		// Verifica se teve uma peça capturada
		if (capturedPiece != null) {
			// Recoloca no tabuleiro e atualiza as listas
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

	}

	/**
	 * Retorna a cor do oponente
	 * 
	 * @param color
	 * @return
	 */
	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	/**
	 * Retorna o rei da cor escolhida
	 * 
	 * @param color
	 * @return
	 */
	private ChessPiece king(Color color) {
		// Gera uma nova lista apenas com as peças da cor escolhida
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			// É um Rei?
			if (p instanceof King) {
				// Devolve ele!
				return (ChessPiece) p;
			}
		}

		// Não achei o rei: Uma exceção que NÃO DEVE ocorrer jamais
		throw new IllegalStateException("O Rei da cor " + color.getText() + " não existe no tabuleiro");
	}

	/**
	 * Testa se o rei da cor informada está em check
	 * 
	 * @param color
	 * @return
	 */
	private boolean testCheck(Color color) {
		// Pega a posição do Rei
		Position kingPosition = king(color).getChessPosition().toPosition();

		// Faz a lista com todas as peças do oponente
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

		// Testa cada peça do oponente para ver se alguma está dando check no rei
		for (Piece p : opponentPieces) {
			// Matriz de movimentos possíveis
			boolean[][] mat = p.possibleMoves();

			// A posição da matriz é a mesma da posição do rei?
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				// Está em check
				return true;
			}
		}

		// Não está em check
		return false;
	}

	/**
	 * Informa a posição de uma peça no tabuleiro usando a nomenclatura de xadrez
	 * 
	 * @param Column
	 * @param row
	 * @param piece
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		// Coloca no tabuleiro
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		// Coloca na lista de peças no tabuleiro
		piecesOnTheBoard.add(piece);
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
