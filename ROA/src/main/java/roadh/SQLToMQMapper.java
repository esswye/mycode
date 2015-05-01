package roadh;

import roadh.helpers.TextFileResourceReader;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * This class provides methods to take the result of a SQL call and map the details to an MQ string
 */
public class SQLToMQMapper {


    private static final String SQL_TO_MQ_MAPPING_RESOURCE = "mappings/SQLToMQMappings.txt";

    /**
     * Local class to hold block name and block field name values
     */
    private class MQMappings {
        String blockName;
        String fieldName;

        public MQMappings(String blockName, String fieldName) {
            if ("".equals(blockName.trim())) {
                throw new RuntimeException("Block name cannot be blank");
            }
            if ("".equals(fieldName.trim())) {
                throw new RuntimeException("Field name cannot be blank");
            }
            this.blockName = blockName;
            this.fieldName = fieldName;
        }
    }

    private Map<String, MQMappings> mappings = new TreeMap<String, MQMappings>();

    /**
     * Class constructor
     */
    public SQLToMQMapper() {
        loadSQLToMQDefinitions();
    }

    /**
     * Loads the SQL to MQ mapping definitions from the resource file.
     * The resource file entries are defined as "SQLField=BlockName.FieldName"
     */
    private void loadSQLToMQDefinitions() {

        new TextFileResourceReader(SQL_TO_MQ_MAPPING_RESOURCE) {
            @Override
            public boolean processLine(String line) {
                String[] nameEquals = line.split("=");
                if ("".equals(nameEquals[0].trim())) {
                    throw new RuntimeException("SQL Field cannot be blank");
                }
                String[] blockField = nameEquals[1].trim().split("\\.");
                MQMappings mqMappings = new MQMappings(blockField[0], blockField[1]);
                mappings.put(nameEquals[0].toUpperCase(), mqMappings);

                return true;
            }
        };
    }


    /**
     * Maps the data in the result set to the MQ definition.
     *
     * @param resultSet    Results set from SQL call
     * @param mqDefinition The MQ definition
     * @throws SQLException
     */
    public void map(ResultSet resultSet, MQDefinition mqDefinition) throws SQLException {
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        int index = 0;
        while (resultSet.next()) {
            index++;
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                MQMappings mqMappings = getMQMappingsFor(rsMetaData.getColumnLabel(i).toUpperCase());
                mqDefinition.setValue(mqMappings.blockName, mqMappings.fieldName, index, resultSet.getString(i));
            }
        }
        resultSet.close();
    }

    /**
     * Gets the MQMappings for the specified column name and throws a runtime exception if not found.
     *
     * @param columnName The column name to get mappings for.
     * @return The MQ block and field to map to.
     */
    private MQMappings getMQMappingsFor(final String columnName) {
        if (!mappings.containsKey(columnName)) {
            throw new RuntimeException("No MQMapping found for [" + columnName + "]");
        }
        return mappings.get(columnName);
    }

}
