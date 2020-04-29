package chess;

/**
 * ENUM com valores definidos
 */
public enum Color {
	BLACK("Preto"), WHITE("Branco");

	private String value;

	Color(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
