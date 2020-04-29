package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();

		// TODO: temporário
		while (true) {
			try {

				// Imprime o tabuleiro
				UI.clearScreen();
				UI.printMatch(chessMatch);

				// Lê a peça a ser movida
				System.out.println();
				System.out.print("Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				// Marca os movimentos possíveis
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				// Destino
				System.out.println();
				System.out.print("Alvo: ");
				ChessPosition target = UI.readChessPosition(sc);

				// Pega uma possível peça capturada
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

			} catch (ChessException e) {
				System.out.println(UI.ANSI_RED + e.getMessage() + UI.ANSI_RESET);
				System.out.println(UI.ANSI_YELLOW + "(pressione [ENTER] para continuar...)" + UI.ANSI_RESET);
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(UI.ANSI_RED + e.getMessage() + UI.ANSI_RESET);
				System.out.println(UI.ANSI_YELLOW + "(pressione [ENTER] para continuar...)" + UI.ANSI_RESET);
				sc.nextLine();
			}

		}

	}

}
