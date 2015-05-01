package roadh.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;


public abstract class TextFileResourceReader {

    /**
     * Constructor method to load and process a text file resource. Lines will be trimmed, blank lines and comments will
     * be ignored. Use the alternative constructor if these settings are not required.
     *
     * @param resourceName Name of resource file (plus partial path)
     */
    public TextFileResourceReader(String resourceName) {
        processTextFile(resourceName, true, true, true);
    }

    /**
     * Constructor method to load and process a text file resource.
     *
     * @param resourceName     Name of resource file (plus partial path)
     * @param trimLines        If true then lines will be trimmed by removing leading and trailing spaces.
     * @param ignoreEmptyLines If true then empty lines will be skipped.
     * @param ignoreComments   If true then comment lines Ie.g. lines starting with #) will be ignored.
     */
    public TextFileResourceReader(String resourceName, boolean trimLines, boolean ignoreEmptyLines, boolean ignoreComments) {
        processTextFile(resourceName, trimLines, ignoreEmptyLines, ignoreComments);


    }

    /**
     * Loads and process a text file resource.
     *
     * @param resourceName     Name of resource file (plus partial path)
     * @param trimLines        If true then lines will be trimmed by removing leading and trailing spaces.
     * @param ignoreEmptyLines If true then empty lines will be skipped.
     * @param ignoreComments   If true then comment lines Ie.g. lines starting with #) will be ignored.
     */
    private void processTextFile(String resourceName, boolean trimLines, boolean ignoreEmptyLines, boolean ignoreComments) {
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new RuntimeException("Resource " + resourceName + " not found. Make sure location, spelling and capitalisation is correct.");
        }
        File file = new File(resource.getFile());
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + resourceName + " not found");
        }

        if (ignoreComments || ignoreEmptyLines) {
            String text = scanner.useDelimiter("\\A").next();
            if (ignoreComments) {
                // Ignore anything between /* and */
                text = text.replaceAll("(?s)/\\*.*?\\*/", "");
                // Ignore anything after a # or a -- on a line
                text = text.replaceAll("([#]|[-][-]).*", "");

            }
            if (ignoreEmptyLines) {
                text = text.replaceAll("(?m)^[ \\t]*\\r?\\n", "");
            }
            scanner = new Scanner(text);
        }


        boolean fullyProcessed = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (trimLines) {
                line = line.trim();
            }
            if (!processLine(line)) {
                fullyProcessed = false;
                break;
            }
        }

        scanner.close();

        endOfFile(fullyProcessed);

    }

    /**
     * Called when the processing of the file is complete. Use this to handle any cleanups that may need to be done.
     *
     * @param fullyProcessed True if the file is fully processed, false otherwise.
     */
    protected void endOfFile(boolean fullyProcessed) {
        //Nothing to do by default
    }

    ;


    /**
     * This method must be overridden and should return true if you want to process subsequent lines.
     *
     * @param line
     */
    abstract public boolean processLine(String line);

}
