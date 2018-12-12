package file.spreadsheet;

/**
 * This enum is used to define the type of splitter used for the Spreadsheet
 * services, especially for parsing the content of the spreadsheet.
 * 
 * @author Pascal Nguyen
 *
 */
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