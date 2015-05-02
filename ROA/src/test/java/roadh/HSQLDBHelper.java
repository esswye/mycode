package roadh;

import roadh.helpers.SQLFileResourceReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Wrapper class for the HSQLDB test database.
 */
public class HSQLDBHelper {

    private static final HSQLDBHelper ourInstance = new HSQLDBHelper();
    public Statement statement;
    private Connection connection;

    private HSQLDBHelper() {
    }

    public static HSQLDBHelper getInstance() {
        return ourInstance;
    }

    public void open() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "SA", "");
            final Statement st = connection.createStatement();
            this.statement = st;

            new SQLFileResourceReader("db/TestDBScripts.sql") {

                @Override
                public boolean processSQL(String sqlStatement) {
                    try {
                        st.execute(sqlStatement);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    return true;
                }
            };

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Closes the HSQLDB database
     */
    public void close() {
        try {
            statement.execute("SHUTDOWN");
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
