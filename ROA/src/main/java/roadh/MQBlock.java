package roadh;

import java.util.LinkedHashMap;
import java.util.Map;

class MQBlock {

    private final String blockName;

    //A LinkedHashMap preserves the order of the fields.
    private final Map<String, String> fields = new LinkedHashMap<String, String>();

    /**
     * Constructor
     *
     * @param blockName Name of the block.
     */
    public MQBlock(String blockName) {
        this.blockName = blockName;
    }

    /**
     * Defines the set of fields and the order of the fields in the block.
     *
     * @param fieldNames Names of the fields in the block. The order is the order the fields are presented.
     */
    public void defineFields(String[] fieldNames) {
        for (String fieldName : fieldNames) {
            if (fields.containsKey(fieldName)) {
                throw new RuntimeException("Field [" + fieldName + "] is already defined in block [" + blockName + "]");
            }
            fields.put(fieldName, null);
        }
    }


    /**
     * Sets the value of the field.
     *
     * @param fieldName Name of the field.
     * @param value     Value to set.
     */
    public void setValue(String fieldName, String value) {
        if (fields.containsKey(fieldName)) {
            if (fields.get(fieldName) != null) {
                throw new RuntimeException("Field [" + fieldName + "] is already set in block [" + blockName + "]");
            }
            fields.put(fieldName, value);
        } else {
            throw new RuntimeException("Field [" + fieldName + "] is not defined in block [" + blockName + "]");
        }
    }

    @Override
    public String toString() {
        return "MQBlock{" +
                "blockName='" + blockName + '\'' +
                ", fields=" + fields +
                '}';
    }

    /**
     * Returns the block as part of an MQ string
     *
     * @return The block data in MQ format
     */
    public String asMQString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(blockName);
        sb.append(">");
        int lastFieldIndex = fields.size();
        for (String fieldName : fields.keySet()) {
            String value = fields.get(fieldName);
            if (value != null) {
                sb.append(value);
            }
            if (--lastFieldIndex > 0) {
                sb.append("|");
            }

        }
        return sb.toString();
    }
}
