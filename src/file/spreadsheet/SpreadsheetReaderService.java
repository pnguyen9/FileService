package file.spreadsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import file.FileReaderService;

public class SpreadsheetReaderService {

	private static SpreadsheetReaderService spreadsheetReaderService;

	private Map<String, SpreadsheetSplitter> spreadsheetSplitters;

	private List<String> spreadsheetColumnNames;

	private String[][] spreadsheetContent;

	private Map<String, Integer> indexForColumnName;

	private int lines;

	private int columns;

	public static SpreadsheetReaderService getSpreadsheetReaderService() {
		if (spreadsheetReaderService == null) {
			spreadsheetReaderService = new SpreadsheetReaderService();
		}

		return spreadsheetReaderService;
	}

	/**
	 * Get the splitter identified by the given character.
	 * 
	 * @param character
	 *            The character with which to identify the splitter.
	 * @return See description
	 */
	public SpreadsheetSplitter getSpreadsheetSplitterForCharacter(char character) {
		return getSpreadsheetSplitterForCharacter("" + character);
	}

	/**
	 * Get the splitter identified by the given character.
	 * 
	 * @param character
	 *            The character with which to identify the splitter.
	 * @return See description
	 */
	public SpreadsheetSplitter getSpreadsheetSplitterForCharacter(String character) {
		if (spreadsheetSplitters == null) {
			spreadsheetSplitters = new HashMap<String, SpreadsheetSplitter>();

			for (SpreadsheetSplitter spreadsheetSplitter : SpreadsheetSplitter.values()) {
				spreadsheetSplitters.put(spreadsheetSplitter.toString(), spreadsheetSplitter);
			}
		}

		return spreadsheetSplitters.get(character);
	}

	/**
	 * Read the spreadsheet at the given path and load the data.
	 * 
	 * In order to use the data retrieved, please see
	 * {@link #getSpreadsheetContent()}.
	 * 
	 * @param filePath
	 *            The path of the file to read. This parameter can either be a
	 *            relative path or a full path.
	 * @param splitter
	 *            The splitter with which to identify the columns in the file
	 */
	public void loadSpreadsheet(String filePath, SpreadsheetSplitter splitter) {
		spreadsheetContent = null;
		List<String> spreadsheetContentAsText = FileReaderService.getFileReaderService().getTextFromFile(filePath);

		// We retrieve the spreadsheet column names, which are on the first row
		String spreadsheetColumnNames = spreadsheetContentAsText.remove(0);
		this.spreadsheetColumnNames = new ArrayList<String>(spreadsheetColumnNames.split(splitter.toString()).length);

		int columnNameIndex = 0;
		indexForColumnName = new HashMap<String, Integer>();

		for (String columnName : spreadsheetColumnNames.split(splitter.toString())) {
			this.spreadsheetColumnNames.add(columnName);
			indexForColumnName.put(columnName, columnNameIndex++);
		}

		// Since we removed the first row containing column names, only data is
		// remaining to parse
		int line = 0;
		int column = 0;
		for (String spreadsheetLine : spreadsheetContentAsText) {
			if (spreadsheetContent == null) {
				lines = spreadsheetContentAsText.size();
				columns = getMaxAmountOfColumns(spreadsheetContentAsText, splitter);

				spreadsheetContent = new String[lines][columns];
			}

			for (String data : spreadsheetLine.split(splitter.toString())) {
				spreadsheetContent[line][column++] = data;
			}

			++line;
			column = 0;
		}
	}

	/**
	 * Get the name of the columns of the loaded spreadsheet.
	 * 
	 * Please remember to load the spreadsheet with
	 * {@link #loadSpreadsheet(String, SpreadsheetSplitter)} before calling this
	 * method.
	 * 
	 * @return See description
	 */
	public List<String> getSpreadsheetColumnNames() {
		return spreadsheetColumnNames;
	}

	/**
	 * Get the content of the loaded spreadsheet.
	 * 
	 * Please remember to load the spreadsheet with
	 * {@link #loadSpreadsheet(String, SpreadsheetSplitter)} before calling this
	 * method.
	 * 
	 * @return See description
	 */
	public String[][] getSpreadsheetContent() {
		return spreadsheetContent;
	}

	/**
	 * Get the matrix column index for the given column name.
	 * 
	 * Please remember to load the spreadsheet with
	 * {@link #loadSpreadsheet(String, SpreadsheetSplitter)} before calling this
	 * method.
	 * 
	 * @return See description
	 */
	public int getIndexForColumnName(String columnName) {
		return indexForColumnName.get(columnName);
	}

	/**
	 * Get the amount of lines for the loaded spreadsheet.
	 * 
	 * Please remember to load the spreadsheet with
	 * {@link #loadSpreadsheet(String, SpreadsheetSplitter)} before calling this
	 * method.
	 * 
	 * @return See description
	 */
	public int getLines() {
		return lines;
	}

	/**
	 * Get the amount of columns for the loaded spreadsheet.
	 * 
	 * Please remember to load the spreadsheet with
	 * {@link #loadSpreadsheet(String, SpreadsheetSplitter)} before calling this
	 * method.
	 * 
	 * @return See description
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Retrieve the max amount of columns in the file content. This can be useful
	 * when some files have empty columns.
	 * 
	 * @param content
	 *            The content of the file
	 * @param splitter
	 *            The splitter to split the lines into columns
	 * @return See description
	 */
	private int getMaxAmountOfColumns(List<String> content, SpreadsheetSplitter splitter) {
		int maxAmountOfColumns = 0;

		for (String line : content) {
			int lineLength = line.split(splitter.toString()).length;

			if (lineLength > maxAmountOfColumns) {
				maxAmountOfColumns = lineLength;
			}
		}

		return maxAmountOfColumns;
	}

}
