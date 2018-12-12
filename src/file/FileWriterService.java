package file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * A service which can be used to write a content into a file.
 * 
 * @author Pascal Nguyen
 *
 */
public class FileWriterService {

	private static FileWriterService fileWriterService;

	public static FileWriterService getFileWriterService() {
		if (fileWriterService == null) {
			fileWriterService = new FileWriterService();
		}

		return fileWriterService;
	}

	/**
	 * Save the given text in the file at the given path.
	 * 
	 * @param filePath
	 *            The path of the file in which to save the content. This parameter
	 *            can either be a relative path or a full path.
	 * @param text
	 *            The text to save in the file.
	 */
	public void saveTextToFile(String filePath, String text) {
		saveTextToFile(filePath, Arrays.asList(text.split("\\n")));
	}

	/**
	 * Save the given text in the file at the given path.
	 * 
	 * @param filePath
	 *            The path of the file in which to save the content. This parameter
	 *            can either be a relative path or a full path.
	 * @param multiLineText
	 *            The text to save in the file.
	 */
	public void saveTextToFile(String filePath, List<String> multiLineText) {
		try {
			Path file = Paths.get(filePath);
			Files.write(file, multiLineText, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
