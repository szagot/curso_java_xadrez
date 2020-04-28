package application;

import chess.ChessPiece;
import chess.Color;

/**
 * Classe com métodos auxiliares para o programa de xadrez
 * 
 * Cores no console do JAVA:
 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
 * 
 * @author szago
 *
 */
public class UI {
	// Constantes de cores de letras
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	// Constantes de cores de fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
		// Tinha peça nessa posição?
		if (piece == null) {
			System.out.print("-");
		} else {
			// A peça é branca?
			if (piece.getColor() == Color.WHITE) {
				// Imprime em ciano
				System.out.print(ANSI_CYAN + piece + ANSI_RESET);
			} else {
				// Imprime em roxo
				System.out.print(ANSI_PURPLE + piece + ANSI_RESET);
			}
		}
		
		System.out.print(" ");
	}
}
