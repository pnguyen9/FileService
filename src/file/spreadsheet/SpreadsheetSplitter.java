package file.spreadsheet;

public enum SpreadsheetSplitter {

	TAB('\t'), //
	COMMA(','), //
	SEMICOLON(';');

	private SpreadsheetSplitter(char character) {
		this.character = character;
	}

	private char character;

	@Override
	public String toString() {
		return "" + character;
	}

}