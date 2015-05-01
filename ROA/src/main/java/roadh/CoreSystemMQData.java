package roadh;

import roadh.helpers.SQLFileResourceReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CoreSystemMQData {

    /**
     * Gets the MQ String from data in the core system.
     * @param policyNo Policy number - could be Bliss or Compass
     * @return
     */
    public String getMQString(final String policyNo, Statement statement) {

        String coreSystemSQLS = "";
        Connection connection;
        // If the policy number is 7 digits followed by a letter in the range A to J then it's a BLISS policy
        if (policyNo.matches("\\d{7}[A-J]")) {
            coreSystemSQLS = "db/Bliss.sql";
        } else {
            coreSystemSQLS = "db/Compass.sql";
        }

        return getMQStringFromConnection(policyNo, coreSystemSQLS, statement);
    }

    /**
     * Gets the MQ String by building it up based on the results of SQL calls the the relevant core system.
     * @param policyNo The policy number
     * @param coreSystemSQLSResourceName The core system SQL resource name
     * @param statement The statement used to access data from the core system DB
     * @return The MQ string
     */
    private String getMQStringFromConnection(final String policyNo, final String coreSystemSQLSResourceName, final Statement statement) {
        final MQDefinition mqDefinition = new MQDefinition();
        final SQLToMQMapper sqlToMQMapper = new SQLToMQMapper();

        new SQLFileResourceReader(coreSystemSQLSResourceName) {

            @Override
            public boolean processSQL(String sqlQuery) {
                sqlQuery = sqlQuery.replace("[POLICYNO]", policyNo);
                try {
                    ResultSet resultSet = statement.executeQuery(sqlQuery);
                    sqlToMQMapper.map(resultSet, mqDefinition);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                return true;
            }
        };


        return mqDefinition.getMQString();
    }

}
