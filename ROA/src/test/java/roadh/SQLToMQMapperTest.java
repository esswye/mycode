package roadh;

import org.junit.*;

import java.sql.*;

import static org.junit.Assert.*;

public class SQLToMQMapperTest {

    MQDefinition mqDefinition;
    SQLToMQMapper sqlToMQMapper;

    @BeforeClass
    public static void beforeOnlyOnce() throws Exception {
        HSQLDBHelper.getInstance().open();
    }

    @AfterClass
    public static void afterOnlyOnce() throws SQLException {
        //HSQLDB needs this command to close properly.
        HSQLDBHelper.getInstance().close();
    }

    @Before
    public void setUp() throws Exception {
        mqDefinition = new MQDefinition();
        sqlToMQMapper = new SQLToMQMapper();
    }



    @Test
    public void testMap1() throws Exception {
        //Actual capitalisation of the column names shouldn't matter.
        String qry = "select YO_NAME as Field1, YO_UDDERNAME as field2, YO_DOB as FIELD3, YO_STATUS as FiElD4 from My_Table where YO_POLICY = '1000'";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZA>John|Murphy|1970-01-29|99<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testMap6() throws Exception {
        String qry = "select YO_NAME as Field1, YO_UDDERNAME as field2, YO_DOB as FIELD3, YO_STATUS as FiElD4 from My_Table where YO_POLICY = '1006'";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZA>Sinead|O'Conner|1989-09-09|42<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testMap2() throws Exception {
        String qry = "select YO_NAME as Field1, YO_UDDERNAME as field2, YO_DOB as FIELD3, YO_STATUS as FiElD4 from My_Table where YO_POLICY in ('1003', '1005')";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZA>Mary|Walsh|1966-11-20|66<ZZA>Tom|Crean|1889-01-01|91<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testAllFirstNName() throws Exception {
        String qry = "select YO_NAME as Field1 from My_Table";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZA>John|||<ZZA>Pat|||<ZZA>Mary|||<ZZA>Tom|||<ZZA>Tom|||<ZZA>Sinead|||<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testTwoQueries() throws Exception {
        String qry = "select YO_NAME as Field1, YO_UDDERNAME as field2, YO_DOB as FIELD3, YO_STATUS as FiElD4 from My_Table where YO_POLICY = '1000'";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        qry = "select PolicyNumber as here, PolicyStatus as be, PolicyEndDate as dragons from POLICY_TABLE where POLICYNUMBER = '1007'";
        resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZA>John|Murphy|1970-01-29|99<ZZB>1007|Live|2027-08-29<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testTwoQueriesTwoClients() throws Exception {
        String qry = "select YO_NAME as Field1, YO_UDDERNAME as field2, YO_DOB as FIELD3, YO_STATUS as FiElD4 from My_Table where YO_POLICY in ('1003', '1005')";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        qry = "select PolicyNumber as here, PolicyStatus as be, PolicyEndDate as dragons from POLICY_TABLE where POLICYNUMBER = '1007'";
        resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZA>Mary|Walsh|1966-11-20|66<ZZA>Tom|Crean|1889-01-01|91<ZZB>1007|Live|2027-08-29<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testOrderOfBlocks() throws Exception {
        //Reverse the order of the blocks.
        String[] blockOrder = {"ZZB", "ZZA"};
        mqDefinition.defineBlockOrder(blockOrder);
        String qry = "select YO_NAME as Field1, YO_UDDERNAME as field2, YO_DOB as FIELD3, YO_STATUS as FiElD4 from My_Table where YO_POLICY in ('1003', '1005')";
        ResultSet resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        qry = "select PolicyNumber as here, PolicyStatus as be, PolicyEndDate as dragons from POLICY_TABLE where POLICYNUMBER = '1007'";
        resultSet =  HSQLDBHelper.getInstance().statement.executeQuery(qry);
        sqlToMQMapper.map(resultSet, mqDefinition);
        assertEquals("<ZZB>1007|Live|2027-08-29<ZZA>Mary|Walsh|1966-11-20|66<ZZA>Tom|Crean|1889-01-01|91<EOM>", mqDefinition.getMQString());
    }

}