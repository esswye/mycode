package roadh;

import roadh.helpers.TextFileResourceReader;

import java.util.*;

class MQDefinition {

    private static final String EOM = "<EOM>";

    private final Map<String, Map<Integer, MQBlock>> blocks = new LinkedHashMap<String, Map<Integer, MQBlock>>();


    /**
     * Sets the value of and MQBlock field.
     *
     * @param blockName Name of the block
     * @param fieldName Name of the field in the block
     * @param index     Index of the block (1 is first, 2 is second, etc)
     * @param value     The value to set. Null values are automatically converted to empty strings
     */
    public void setValue(final String blockName, final String fieldName, final int index, final String value) {

        Map<Integer, MQBlock> blocksForName;

        if (blocks.containsKey(blockName)) {
            blocksForName = blocks.get(blockName);
        } else {
            blocksForName = new TreeMap<Integer, MQBlock>();
            blocks.put(blockName, blocksForName);
        }

        // We want to group all the same blocks (i.e. repeating blocks) together.
        if (!blocksForName.containsKey(index)) {
            MQBlock block = defineBlock(blockName);
            blocksForName.put(index, block);
        }

        MQBlock block = blocksForName.get(index);
        if (value == null) {
            block.setValue(fieldName, "");
        } else {
            block.setValue(fieldName, value);
        }
    }

    /**
     * Defines the order in which we want the blocks to appear.
     *
     * @param blockNameOrder The order the blocks should appear in the MQ string.
     */
    public void defineBlockOrder(String[] blockNameOrder) {
        for (String blockName : blockNameOrder) {
            blocks.put(blockName, new TreeMap<Integer, MQBlock>());
        }
    }

    /**
     * Defines an MQ block by loading the associated resource file containing the list of block fields
     *
     * @param blockName Name of block to define.
     * @return An MQBlock
     */
    MQBlock defineBlock(String blockName) {
        List<String> fieldNames = getBlockFields("blocks/" + blockName + ".txt");
        MQBlock block = new MQBlock(blockName);
        String[] fnames = fieldNames.toArray(new String[fieldNames.size()]);
        block.defineFields(fieldNames.toArray(fnames));
        return block;
    }

    /**
     * Gets the list of fields for the specified block name
     *
     * @param blockName Name of the block.
     * @return List of block fields.
     */
    private List<String> getBlockFields(String blockName) {

        final List<String> result = new ArrayList<String>();

        new TextFileResourceReader(blockName) {
            @Override
            public boolean processLine(String line) {
                result.add(line);

                return true;
            }
        };

        return result;

    }

    /**
     * Returns the MQ string
     *
     * @return A string format that we all know and love. Not.
     */
    public String getMQString() {
        StringBuilder sb = new StringBuilder();
        for (String blockName : blocks.keySet()) {
            Map<Integer, MQBlock> blocksForName = blocks.get(blockName);
            for (Integer index : blocksForName.keySet()) {
                MQBlock block = blocksForName.get(index);
                sb.append(block.asMQString());
            }
        }


        sb.append(EOM);

        return sb.toString();
    }

    public boolean isEmpty() {
        return EOM.equals(getMQString());
    }

}