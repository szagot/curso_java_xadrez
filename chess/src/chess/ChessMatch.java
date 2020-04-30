package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
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
	private Boolean check = false;
	private Boolean checkMate = false;

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

	public boolean getCheckMate() {
		return checkMate;
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

		// Verifica se colocou o proprio jogador em check
		if (testCheck(currentPlayer)) {
			// Desfaz o movimento
			undoMove(source, target, capturedPiece);
			// E lan�a uma exce��o
			throw new ChessException("Voc� n�o pode se colocar em check!");
		}

		// O oponente ficou em check?
		check = testCheck(opponent(currentPlayer));

		// Testa se foi check mate
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			// Troca o turno
			nextTurn();
		}

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
		// Remove a pe�a da posi��o de origem e incrmenta o numero de movimento
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		// Remove uma pe�a da posi��o de destino, se houver
		Piece capturedPiece = board.removePiece(target);
		// Coloca a pe�a na posi��o de destino
		board.placePiece(p, target);

		// Faz o controle da pe�a capturada
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// Roque pequeno: � um rei e ele andou duas casa � direita?
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			// Determina a posi��o de origem da Torre
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			// Determina a posi��o de destino para a Torre
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			// Rocando
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// Roque grande: � um rei e ele andou duas casa � esquerda?
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			// Determina a posi��o de origem da Torre
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			// Determina a posi��o de destino para a Torre
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			// Rocando
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// Retorna a pe�a capturada
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
		// Remove a pe�a da posi��o de destino e remove o movimento realizado na pe�a
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		// Coloca a pe�a de volta na posi��o de origem
		board.placePiece(p, source);

		// Verifica se teve uma pe�a capturada
		if (capturedPiece != null) {
			// Recoloca no tabuleiro e atualiza as listas
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// Desfazendo Roque pequeno: � um rei e ele andou duas casa � direita?
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			// Determina a posi��o de origem da Torre
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			// Determina a posi��o de destino para a Torre
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			// Rocando
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// Desfazendo Roque grande: � um rei e ele andou duas casa � esquerda?
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			// Determina a posi��o de origem da Torre
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			// Determina a posi��o de destino para a Torre
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			// Rocando
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
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
		// Gera uma nova lista apenas com as pe�as da cor escolhida
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			// � um Rei?
			if (p instanceof King) {
				// Devolve ele!
				return (ChessPiece) p;
			}
		}

		// N�o achei o rei: Uma exce��o que N�O DEVE ocorrer jamais
		throw new IllegalStateException("O Rei da cor " + color.getText() + " n�o existe no tabuleiro");
	}

	/**
	 * Testa se o rei da cor informada est� em check
	 * 
	 * @param color
	 * @return
	 */
	private boolean testCheck(Color color) {
		// Pega a posi��o do Rei
		Position kingPosition = king(color).getChessPosition().toPosition();

		// Faz a lista com todas as pe�as do oponente
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

		// Testa cada pe�a do oponente para ver se alguma est� dando check no rei
		for (Piece p : opponentPieces) {
			// Matriz de movimentos poss�veis
			boolean[][] mat = p.possibleMoves();

			// A posi��o da matriz � a mesma da posi��o do rei?
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				// Est� em check
				return true;
			}
		}

		// N�o est� em check
		return false;
	}

	/**
	 * Testa se o rei da cor informada est� em check mate
	 * 
	 * @param color
	 * @return
	 */
	private boolean testCheckMate(Color color) {
		// Se n�o est� nem em check, n�o vai estar em check mate
		if (!testCheck(color)) {
			return false;
		}

		// Pega todas as pe�as da cor informada
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		// Verifica se algum movimento das pe�as do jogador atual tira ele do check
		for (Piece p : list) {
			// Pega a matriz de movimentos poss�veis da pe�a e analisa cada um
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					// � um movimento poss�vel?
					if (mat[i][j]) {
						// Simula um movimento e verifica se saiu do check
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						// Ainda est� em check?
						boolean testCheck = testCheck(color);
						// Devolve a pe�a ao seu lugar
						undoMove(source, target, capturedPiece);
						// Se realmente n�o est� em check
						if (!testCheck) {
							// � pq ainda n�o � check mate
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	/**
	 * Informa a posi��o de uma pe�a no tabuleiro usando a nomenclatura de xadrez
	 * 
	 * @param Column
	 * @param row
	 * @param piece
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		// Coloca no tabuleiro
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		// Coloca na lista de pe�as no tabuleiro
		piecesOnTheBoard.add(piece);
	}

	/**
	 * Inicia a partida, colocando as pe�as no tabuleiro
	 */
	private void initialSetup() {

		// Brancas
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
//		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
//		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
//		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
//		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
//		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		// Pe�es Brancos
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

		// Pretas
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
//		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
//		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
//		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
//		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
//		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		// Pe�es Pretos
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK));

	}

}
