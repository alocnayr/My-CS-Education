Name: Ryan Kornitsky
ID: 172752763

Section 1:
Simply copy the files that I submitted into an IDE and you should be able to run Main and begin to input GET, SET, PIN, and UNPIN commands into the terminal.
I used Intellij for this. First you need to configure the runtime configuration to make sure that args[0] is a buffer size.
If you want to run the program on the actual command line through the terminal, then include the txt files within src.

Section 2: I ran all of the test cases in the corresponding order and they all output the same information as the example output with some deviation in the wording (see section 3).
All the test cases seem to work properly.

Section 3:
I didn’t deviate a lot from the design choices from the assignment.
But I did make sure to be thorough with my printouts to make them very specific to be able to tell exactly what the program is doing.
For example, if I wanted to pin block 5 and it wasn’t already in the buffer, my program would output:

Block 5 is not in buffer pool. Looking for empty buffer...
Found empty buffer at buffer frame # 1.
Reading block 5 from the disk...
Block 5 has been successfully read from the disk and has now been added to the buffer pool at frame # 1.

This indicated that it searched through the entire buffer pool, saw that block 5 wasn’t in there, and is performing I/O to read it from the disk upon finding an empty buffer.
The professor said it was fine if my printouts weren’t exactly the same as what was said in the assignment, as long as I didn’t leave out any other information that it wanted printed out.

A description of each class:

Frame)
Like the assignment, this class keeps track of the content of 4KB, the blockId, and whether the frame is dirty or pinned.
Apart from the basic getters and setters of this class, I have two other methods, getRecord() and setRecord().
These are both referenced in the GET and SET commands (getRecord() returns the record content based on an inputted record number, and setRecord() does the same except replaces the 40 bytes of that record with an inputted record content).

BufferPool)
In this class, I keep track of all of the frames in the buffer, the buffer size, and the last frame evicted with its block id, which are used in the eviction method.
My primary methods are get(), set(), pinBlock(), and unpinBlock().
Inside the get(), set() and pinBlock() methods I follow a very similar overall structure
(using a search() method to find whether the blockId is in the buffer, if not then look for an empty buffer through a getEmptyBuffer() method that searches the pool for blockId == -1 and try to evict if there are no empty buffers, or use a readBlockFromFile() method if there was a free buffer space).
I also have another helper method called getRecordNumber() that is used to find the record number of a record based on the record index.
This is used inside of get() and set().

Main)
Contains all of the info for user input and parses that data out to the BufferPool class to call get(), set(), pinBlock(), or unpinBlock() based on what command they specified.

I didn’t write anything too extra that wasn’t already specified in the project and I provided plenty of comments specifying what each method does and any additional comments inside of the method if I felt it needed them.
