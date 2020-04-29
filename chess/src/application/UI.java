package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
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
	 * Limpa o console
	 * https://stackoverflow.com/questions/2979383/java-clear-the-console
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	/**
	 * Lê a posição da peça para mover
	 * 
	 * @param sc
	 * @return
	 */
	public static ChessPosition readChessPosition(Scanner sc) {
		try {

			// Lê a posição digitada pelo usuário
			String s = sc.nextLine();
			// Separa a coluna e a linha
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));

			// Retorna a posição do tabuleiro de Xadrez
			return new ChessPosition(column, row);

		} catch (RuntimeException e) {
			// Se houver qualquer erro inesperado, cria uma exceção padrão para erro de
			// entrada de dados
			throw new InputMismatchException("Erro ao ler posição: Os valores válidos são de a1 a h8");
		}
	}

	/**
	 * Imprime a partida (tabuleiro e turnos)
	 * 
	 * @param chessMatch
	 * @param captured
	 */
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		// Tabuleiro
		printBoard(chessMatch.getPieces());
		System.out.println();
		// Peças capturadas
		printCapturedPieces(captured);
		System.out.println();
		// Turno
		System.out.println("Turno: " + chessMatch.getTurn());
		// Jogador atual
		System.out.println("Jogador Atual: " + chessMatch.getCurrentPlayer().getText());

		// Está em check?
		if (chessMatch.getCheck()) {
			System.out.println(ANSI_RED + "CHECK!" + ANSI_RESET);
		}
	}

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
				printPiece(pieces[i][j], false);
			}

			// Terminando a linha, quebra a linha
			System.out.println();
		}

		// Imprime as letras das colunas
		System.out.println("  a b c d e f g h");

	}

	/**
	 * Imprime o tabuleiro destacando os movimentos possíveis
	 * 
	 * @param pieces
	 * @param possibleMoves
	 */
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {

		// Lê a matriz do tabuleiro para imprimir a peça
		for (int i = 0; i < pieces.length; i++) {

			// Imprime o número da linha do tabuleiro
			System.out.print((8 - i) + " ");

			for (int j = 0; j < pieces[i].length; j++) {
				// Imprime a peça
				printPiece(pieces[i][j], possibleMoves[i][j]);
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
	 * @param background Verifica se é pra colorir o fundo
	 */
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_GREEN_BACKGROUND);
		}

		// Tinha peça nessa posição?
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
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

	/**
	 * Imprime as peças capturadas
	 * 
	 * @param captured
	 */
	private static void printCapturedPieces(List<ChessPiece> captured) {
		// Gera uma lista apenas com peças capturadas brancas
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		// Gera uma lista apenas com peças capturadas pretas
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());

		System.out.println("Peças capturadas");
		System.out.print("Brancas: ");
		// Imprime no formato padrão do array
		System.out.println(ANSI_CYAN + Arrays.toString(white.toArray()) + ANSI_RESET);

		System.out.print("Pretas: ");
		// Imprime no formato padrão do array
		System.out.println(ANSI_PURPLE + Arrays.toString(black.toArray()) + ANSI_RESET);
	}
}
