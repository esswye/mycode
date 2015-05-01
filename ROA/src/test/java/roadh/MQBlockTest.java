package roadh;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MQBlockTest {

    @Test
    public void testDefineFields() throws Exception {
        MQBlock mqBlock = new MQBlock("CL1");
        String[] fields = {"Field1", "Field2", "Field3"};
        mqBlock.defineFields(fields);

        assertEquals("MQBlock{blockName='CL1', fields={Field1=null, Field2=null, Field3=null}}", mqBlock.toString());
        assertEquals("<CL1>||", mqBlock.asMQString());
    }

    @Test
    public void testSetValue() throws Exception {
        MQBlock mqBlock = new MQBlock("ABC");
        String[] fields = {"Field1", "Field2", "Field3"};
        mqBlock.defineFields(fields);
        mqBlock.setValue("Field3", "999");

        assertEquals("MQBlock{blockName='ABC', fields={Field1=null, Field2=null, Field3=999}}", mqBlock.toString());
        assertEquals("<ABC>||999", mqBlock.asMQString());
    }

    @Test
    public void testToString() throws Exception {
        MQBlock mqBlock = new MQBlock("XYZ");
        String[] fields = {"FirstName", "Surname", "Address"};
        mqBlock.defineFields(fields);
        mqBlock.setValue("Address", "Cork");
        mqBlock.setValue("FirstName", "John");
        mqBlock.setValue("Surname", "Jones");

        assertEquals("MQBlock{blockName='XYZ', fields={FirstName=John, Surname=Jones, Address=Cork}}", mqBlock.toString());
        assertEquals("<XYZ>John|Jones|Cork", mqBlock.asMQString());
    }

    @Test(expected = RuntimeException.class)
    public void testFieldAlreadyExists() throws Exception {
        MQBlock mqBlock = new MQBlock("123");
        String[] fields = {"FirstName", "Surname", "FirstName"};
        mqBlock.defineFields(fields);
    }

    @Test(expected = RuntimeException.class)
    public void testFieldDoesNotExist() throws Exception {
        MQBlock mqBlock = new MQBlock("123");
        String[] fields = {"FirstName", "Surname"};
        mqBlock.defineFields(fields);
        mqBlock.setValue("Address", "Cork");
    }

    @Test(expected = RuntimeException.class)
    public void testFieldAlreadySet() throws Exception {
        MQBlock mqBlock = new MQBlock("123");
        String[] fields = {"FirstName", "Surname"};
        mqBlock.defineFields(fields);
        mqBlock.setValue("FirstName", "John");
        mqBlock.setValue("FirstName", "Mary");
    }
}