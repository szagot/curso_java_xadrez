package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();

		// Roda enquanto não for checkmate
		while (!chessMatch.getCheckMate()) {
			try {

				// Imprime a partida
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);

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
				if (capturedPiece != null) {
					// Se tiver uma peça, adiciona a lista
					captured.add(capturedPiece);
				}

				// Teve uma peça promovida?
				if (chessMatch.getPromoted() != null) {
					String type = "";
					// garante que o usuário vai gigitar uma letra válida
					do {
						if (!type.isEmpty()) {
							System.out.println(UI.ANSI_RED + "Valor inválido! " + UI.ANSI_RESET);
						}
						System.out.print(
								"Digite para qual peça o peão será promovido ([T]orre, [C]avalo, [B]ispo ou r[A]inha): ");
						type = sc.nextLine().toUpperCase();
					} while (!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("A"));
					chessMatch.replacePromotedPiece(type);
				}

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

		// Imprime a visão da partida finalizadas
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);

	}

}
