The project3 of CS3013 focuses on memory management and page structures. In this project, we create
our own memory and disk to achieve paging. 

The structure of the page table entry is VPN, PFN, Protection bit, and valid bit. The comment
in code provides a more specific explanation of what each value of the corresponding bits represents.
Each page table entry is 4 byte, thus together the page table is 16 byte in memory. 

If fatal error occur (ex: putting 5 as pid, putting negative number as value), the program 
will exit with error message (see input3, input5, input6).

For part 1, the mapping, storing and loading does not involve swap space, so the input1 and input3 can 
prove that map, store, and load works properly (as provided in project description).

Writing of the file is protected by the protection bit, and we can see several examples of 
access denial. Error message will also print when try to update the page with the same protection
bit number, and this can be seen with input2.

For part 2, we made sure that all swapping takes places as needed and page eviction does occur with the output.
After swapping occur, since the program can still get the correct value of the corresponding virtual page, we 
confirm that the swapping is correct. The swapping can be seen in input2 and input4.

The mapping and eviction strategy I used may be a little different from the professor's method, (the
update of round robin is a bit different), so the swapping output for the last two lines are different 
from the example output. However, the loading value is still correct from the corresponding virtual address 
and the output is reasonable based on how page 3 got swapped instead of page 0, so the program still produces 
the desired output.