package chess;

import boardgame.Board;
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
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		
		return mat;
	}
	
	/**
	 * Inicia a partida, colocando as peças no tabuleiro
	 */
	private void initialSetup() {

		// TODO: testando
		board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
		
	}

}
