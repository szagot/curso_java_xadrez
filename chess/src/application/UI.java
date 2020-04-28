package application;

import chess.ChessPiece;

/**
 * Classe com métodos auxiliares para o programa de xadrez
 * @author szago
 *
 */
public class UI {

	/**
	 * Iprime o tabuleiro
	 * 
	 * @param pieces
	 */
	public static void printBoard(ChessPiece[][] pieces) {

		// Lê a matriz do tabuleiro para imprimir a peça
		for (int i = 0; i < pieces.length; i++) {

			// Imprime o número da linha do tabuleiro
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pieces[i].length; j++) {
				// Imprime a peça
				printPiece(pieces[i][j]);
			}
			
			// Terminando a linha, quebra a linha
			System.out.println();
		}
		
		// Imprime as letras das colunas
		System.out.println("  a b c d e f g h");

	}

	/**
	 * Imprime uma única peça
	 * 
	 * @param piece
	 */
	private static void printPiece(ChessPiece piece) {
		// Tina peça nessa posição?
		if (piece == null) {
			System.out.print("-");
		} else {
			System.out.print(piece);
		}

		System.out.print(" ");
	}
}
