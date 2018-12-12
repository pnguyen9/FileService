package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A service which can be used to read the content of a file.
 * 
 * @author Pascal Nguyen
 *
 */
public class FileReaderService {

	private static FileReaderService fileReaderService = null;

	public static FileReaderService getFileReaderService() {
		if (fileReaderService == null) {
			fileReaderService = new FileReaderService();
		}

		return fileReaderService;
	}

	/**
	 * Read the file at the given path and return a list with the content of the
	 * file, each index containing a line.
	 * 
	 * @param filePath
	 *            The path of the file to read. This parameter can either be a
	 *            relative path or a full path.
	 * @return See description
	 */
	public List<String> getTextFromFile(String filePath) {
		ArrayList<String> text = new ArrayList<String>();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
			String nextLine;

			while ((nextLine = bufferedReader.readLine()) != null) {
				text.add(nextLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return text;
	}

}
