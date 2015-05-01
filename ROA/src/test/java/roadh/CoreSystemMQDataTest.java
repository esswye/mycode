package roadh;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class CoreSystemMQDataTest {


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
    }

    @Test
    public void testGetMQString1(){
        CoreSystemMQData coreSystemMQData = new CoreSystemMQData();
        String mqString = coreSystemMQData.getMQString("1234567A", HSQLDBHelper.getInstance().statement);
        assertEquals("<CL1>1234567A|John Smith|1960-01-01|2000-01-29|1<EOM>", mqString);
    }

    @Test
    public void testGetMQString2(){
        CoreSystemMQData coreSystemMQData = new CoreSystemMQData();
        String mqString = coreSystemMQData.getMQString("2345678B", HSQLDBHelper.getInstance().statement);
        assertEquals("<CL1>2345678B|Pat Murphy|1970-01-01|2001-01-29|2<EOM>", mqString);
    }

    @Test
    public void testGetMQString3(){
        CoreSystemMQData coreSystemMQData = new CoreSystemMQData();
        String mqString = coreSystemMQData.getMQString("3456789C", HSQLDBHelper.getInstance().statement);
        assertEquals("<CL1>3456789C|Larry Duggan|1980-01-01|2002-01-29|3<EOM>", mqString);
    }

    @Test
    public void testNoMatchingPolicy(){
        CoreSystemMQData coreSystemMQData = new CoreSystemMQData();
        String mqString = coreSystemMQData.getMQString("9999999A", HSQLDBHelper.getInstance().statement);
        assertEquals("<EOM>", mqString);
    }

    @Test
    public void testGetCompassMQString2(){
        CoreSystemMQData coreSystemMQData = new CoreSystemMQData();
        String mqString = coreSystemMQData.getMQString("87654321", HSQLDBHelper.getInstance().statement);
        assertEquals("<CL1>87654321|Joan Murphy|1975-02-02|2006-01-29|2<EOM>", mqString);
    }


}