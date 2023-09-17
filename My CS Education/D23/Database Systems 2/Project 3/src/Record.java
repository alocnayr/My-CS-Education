// This record is used to define the structure of a record in the dataset
public record Record(String id, String name, String address, int randomV) {

    /**
     * Constructor for Record
     *
     * @param id      Record iD
     * @param name    Record name
     * @param address Record address
     * @param randomV Record randomV
     */
    public Record {
    }

    /**
     * Convert string to Record
     *
     * @param recordString Record in string format
     * @return a new Record
     */
    public static Record stringToRecord(String recordString) {
        String[] parts = recordString.split(", ");
        String id = parts[0];
        String name = parts[1];
        String address = parts[2];
        int randomV = Integer.parseInt(parts[3]);
        return new Record(id, name, address, randomV);
    }
}
