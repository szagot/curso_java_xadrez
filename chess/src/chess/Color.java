package chess;

import application.UI;

/**
 * ENUM com valores definidos
 */
public enum Color {
	BLACK(UI.ANSI_PURPLE + "Preto" + UI.ANSI_RESET), WHITE(UI.ANSI_CYAN + "Branco" + UI.ANSI_RESET);

	private String value;

	Color(String value) {
		this.value = value;
	}

	/**
	 * Pega o texto do ENUM
	 * 
	 * @return
	 */
	public String getText() {
		return value;
	}
}
