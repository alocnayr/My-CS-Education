Ryan Kornitsky rtkornitsky

Section I: section on how to compile and execute your code. Include clear easy-to-follow
step by step that TAs can follow

- Place the code in an IDE and run the main method. The program will prompt you to enter
commands corresponding to the commands listed in the assignment. You will need to include
the folder Project2Dataset outside of src.

Section II: State clearly which parts are working and which parts are not working in your
code. This will help the TAs give you fair points.

- Everything is working to my knowledge.

Section III: section describes any design decisions that you do beyond the design guidelines
given in this document

- I commented my code a lot to make everything a lot easier to understand and
I added class level comments to explain what each class does. Nothing goes beyond the
assignment. For range based query, I did not use inclusive values. If you look at any of the code logic,
in the Main class, I used shortcuts for the commands because it was really annoying to type
them all out everytime, but this is just in addition to the commands listed in the assignment.

- ArrayBasedIndex: I used an array of lists to store multiple values for each randomV value
to account for collisions. This class has two main methods: the rangeSearch() and adding
records to the index (addRecord()).

- HashBasedIndex: I used a HashMap to store the randomV values as the key and a list of RecordLocations
as the value to account for collisions. This class has two main methods: search() and addRecord().

- RecordLocation: This record is used to store the fileNumber and the offset of the record in the file.

- RecordParser: This is a functional interface that is used to parse and reads the records in the files.

- FileParser: This class is used to parse the files and read the records.

- Queries: This class is used to perform the user input queries from the assignment.

- Main: This class is used to run the program and prompt the user for input.