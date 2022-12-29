package cs2102hw1;

//put your name at the beginning of your testing files (Examples.java)
//all test cases must go in Examples.java
//provide examples for all classes defined
//provide test cases for all methods (examples of classes)
//check your work with the compilecheck file.
//if it passes you probably won't break the autograder

public class Match {
	
	IContestant contestant1;
	IContestant contestant2;
	IResult results;
	
	Match(IContestant contestant1, IContestant contestant2, IResult results) {
		this.contestant1 = contestant1;
		this.contestant2 = contestant2;
		this.results = results;
	
	
	}
	
	public IContestant winner() {
		if (results.isValid() == true) {
			return results.getWinner();
		}
		else {
			return null;}
		}
	
	}
