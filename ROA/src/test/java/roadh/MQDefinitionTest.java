package roadh;

import org.junit.Test;

import static org.junit.Assert.*;

public class MQDefinitionTest {

    @Test
    public void testSetValue() throws Exception {
        MQDefinition mqDefinition = new MQDefinition();
        mqDefinition.setValue("ZZA", "Surname", 1, "Murphy");
        mqDefinition.setValue("ZZA", "FirstName", 1, "Sean");
        mqDefinition.setValue("ZZA", "DOB", 1, "1/1/1900");

        assertEquals("<ZZA>Sean|Murphy|1/1/1900|<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testDefineBlock() throws Exception {
        MQDefinition mqDefinition = new MQDefinition();
        MQBlock result = mqDefinition.defineBlock("ZZA");

        assertEquals("MQBlock{blockName='ZZA', fields={FirstName=null, Surname=null, DOB=null, Status=null}}", result.toString());
    }

    @Test
    public void testGetMQString() throws Exception {
        MQDefinition mqDefinition = new MQDefinition();
        mqDefinition.setValue("ZZA", "Surname", 1, "Murphy");
        mqDefinition.setValue("ZZA", "FirstName", 1, "Sean");
        mqDefinition.setValue("ZZB", "PolicyNo", 1, "1234567");
        mqDefinition.setValue("ZZB", "EndDate", 1, "2018-04-22");

        mqDefinition.setValue("ZZA", "Surname", 2, "Jones");
        mqDefinition.setValue("ZZA", "FirstName", 2, "Tom");

        assertEquals("<ZZA>Sean|Murphy||<ZZA>Tom|Jones||<ZZB>1234567||2018-04-22<EOM>", mqDefinition.getMQString());
    }

    @Test
    public void testBlockOrder() throws Exception {
        MQDefinition mqDefinition = new MQDefinition();
        String[] blockOrder = {"ZZB", "ZZA"};
        mqDefinition.defineBlockOrder(blockOrder);
        mqDefinition.setValue("ZZA", "Surname", 1, "Murphy");
        mqDefinition.setValue("ZZA", "FirstName", 1, "Sean");
        mqDefinition.setValue("ZZB", "PolicyNo", 1, "1234567");
        mqDefinition.setValue("ZZB", "EndDate", 1, "2018-04-22");

        mqDefinition.setValue("ZZA", "Surname", 2, "Jones");
        mqDefinition.setValue("ZZA", "FirstName", 2, "Tom");

        assertEquals("<ZZB>1234567||2018-04-22<ZZA>Sean|Murphy||<ZZA>Tom|Jones||<EOM>", mqDefinition.getMQString());
    }


    @Test(expected = RuntimeException.class)
    public void testOverwriteFail() throws Exception {
        MQDefinition mqDefinition = new MQDefinition();
        mqDefinition.setValue("ZZA", "Surname", 1, "Murphy");
        mqDefinition.setValue("ZZA", "Surname", 1, "Jones");
    }
}