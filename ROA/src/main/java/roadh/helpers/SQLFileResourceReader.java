package roadh.helpers;

/**
 *
 */
public abstract class SQLFileResourceReader extends TextFileResourceReader {

    private StringBuilder sql;
    /**
     * Constructor method to load and process a SQL file resource. Lines will be trimmed, blank lines and comments will
     * be ignored.
     *
     * @param resourceName Name of resource file (plus partial path)
     */
    public SQLFileResourceReader(String resourceName) {
        super(resourceName);
    }

    /**
     * Called when the processing of the file is complete. Use this to handle any cleanups that may need to be done.
     * We'll use this to pass on files with single line SQL statements or any statements at the end of a file that are
     * not terminated with a semicolon.
     *
     * @param fullyProcessed True if the file is fully processed, false otherwise.
     */
    @Override
    public void endOfFile(boolean fullyProcessed) {
        if (fullyProcessed) {
            if (sql != null) {
                String sqlString = sql.toString();
                sql = null;
                processSQL(sqlString);
            }
        }
    }

    /**
     * This method implements the one in the TextFileResourceReader class.
     * It expects all sql statements to be ended using a semicolon.
     *
     * @param line The individual line. Multiple lines can be appended to make the complete statement.
     */
    @Override
    public final boolean processLine(String line) {
        if (sql == null) {
            sql = new StringBuilder();
        }
        sql.append(line);
        if (line.endsWith(";")) {
            String sqlString = sql.toString();
            sql = null;
            return processSQL(sqlString);
        } else {
            sql.append(" ");
        }
        return true;
    }

    /**
     * This method must be overridden and should return true if you want to process subsequent sql statements.
     *
     * @param sqlStatement The sql statement.
     */
    public abstract boolean processSQL(String sqlStatement);


}
