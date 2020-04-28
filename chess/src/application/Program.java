package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();

		// TODO: tempor�rio
		while (true) {

			// Imprime o tabuleiro
			UI.printBoard(chessMatch.getPieces());

			// L� a pe�a a ser movida
			System.out.println();
			System.out.print("Origem: ");
			ChessPosition source = UI.readChessPosition(sc);

			// Destino
			System.out.println();
			System.out.print("Alvo: ");
			ChessPosition target = UI.readChessPosition(sc);

			// Pega uma poss�vel pe�a capturada
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

		}

	}

}
