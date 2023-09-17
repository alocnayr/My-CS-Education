import java.util.Arrays;
import java.util.Scanner;

public class Main {

    /**

     The main method. Takes in a command line argument representing buffer pool size and initializes a BufferPool object with that size.
     It then reads commands entered by the user, and executes the corresponding method of the BufferPool object based on the command entered.

     @param args an array of strings representing the command line arguments, where args[0] is the buffer pool size.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please provide buffer pool size as command line argument.");
            return;
        }

        int bufferSize = Integer.parseInt(args[0]);
        BufferPool bufferPool = new BufferPool();
        bufferPool.initialize(bufferSize);
        Scanner sc = new Scanner(System.in);

        // print ready message
        System.out.println("The program is ready for the next command");

        while (true) {
            // read user command
            String command = sc.nextLine();

            // split command into parts
            String[] parts = command.split(" ", 3);
            String operation = parts[0];

            switch (operation) {
                case "GET" -> {
                    System.out.println("GET command entered");
                    int recordNum = Integer.parseInt(parts[1]);
                    if(recordNum <= 0){
                        System.out.println("Record number must be greater than 0");
                    }
                    else
                        bufferPool.get(recordNum);
                   // printBufferPool(bufferPool);

                }
                case "SET" -> {
                    System.out.println("SET command entered");
                    int recordNum = Integer.parseInt(parts[1]);
                    String recordContent = parts[2].substring(1, parts[2].length() - 1);
                    if(recordContent.length() != 40)
                        System.out.println("Record content must be 40 characters long");
                    else
                        bufferPool.set(recordNum, recordContent);
                   // printBufferPool(bufferPool);
                }
                case "PIN" -> {
                    System.out.println("PIN command entered");
                    int blockId = Integer.parseInt(parts[1]);
                    bufferPool.pinBlock(blockId);
                   // printBufferPool(bufferPool);

                }
                case "UNPIN" -> {
                    System.out.println("UNPIN command entered");
                    int blockId = Integer.parseInt(parts[1]);
                    bufferPool.unpinBlock(blockId);
                   // printBufferPool(bufferPool);

                }
                default ->
                    // invalid command
                        System.out.println("Invalid Command");
            }
        }
    }

    /**
     * Prints the content of the buffer pool and the corresponding block ID of each frame to the console.
     */
    /*
    private static void printBufferPool(BufferPool bufferPool) {
        // Print out each frame in the buffer pool
        System.out.println();
        System.out.println("-----------------------Buffer Pool------------------------");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %n", "Frame", "BlockID", "Pinned", "Dirty", "LastEvicted");
        for(int i = 0; i < bufferPool.getBufferSize(); i++) {
            Frame frame = bufferPool.getBuffers()[i];
            // Print the frame's index, block ID, and status
            System.out.printf("%-10d %-10s ", i+1, frame.getBlockId());
            if(frame.isPinned()) {
                System.out.print("  ✓       ");
            } else {
                System.out.print("           ");
            }
            if(frame.isDirty()) {
                System.out.print("  ✓      ");
            } else {
                System.out.print("         ");
            }
            // Print an arrow to indicate the lastEvictedFrame
            if(bufferPool.getLastEvictedFrame() == i) {
                System.out.print("       ✓");
            }
            else {
                System.out.print("        ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------------------------");
        System.out.println();
    }
     */
}
