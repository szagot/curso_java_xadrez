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

		// Roda enquanto n�o for checkmate
		while (!chessMatch.getCheckMate()) {
			try {

				// Imprime a partida
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);

				// L� a pe�a a ser movida
				System.out.println();
				System.out.print("Origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				// Marca os movimentos poss�veis
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);

				// Destino
				System.out.println();
				System.out.print("Alvo: ");
				ChessPosition target = UI.readChessPosition(sc);

				// Pega uma poss�vel pe�a capturada
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if (capturedPiece != null) {
					// Se tiver uma pe�a, adiciona a lista
					captured.add(capturedPiece);
				}
				
				// Teve uma pe�a promovida?
				if(chessMatch.getPromoted() != null) {
					System.out.print("Digite para qual pe�a o pe�o ser� promovido ([T]orre, [C]avalo, [B]ispo ou r[A]inha): ");
					String type = sc.nextLine();
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

		// Imprime a vis�o da partida finalizadas
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);

	}

}
