import java.util.Scanner;

public class VotingMachine {
	private ElectionData votesInformation;
	private Scanner keyboard = new Scanner(System.in);

	public VotingMachine(ElectionData votesInformation) {
		this.votesInformation = votesInformation;
	}

	public void addWriteIn(String name) throws CandidateExistsException {
		votesInformation.addCandidate(name);
	}

	/**
	 * 3 votes are inputed or returns an error indicating, duplicate or unknown
	 * candidate
	 */
	public void screen() {

		votesInformation.printBallot();

		System.out.println("Who do you want to vote for first?");
		String firstVote = keyboard.next();
		System.out.println("Who do you want to vote for second?");
		String secondVote = keyboard.next();
		System.out.println("Who do you want to vote for third?");
		String thirdVote = keyboard.next();

		try {
			votesInformation.processVote(firstVote, secondVote, thirdVote);

		} catch (UnknownCandidateException uce) {
			System.out.println("Would you like to add this candidate to the ballot?");
			String response = keyboard.next();
			if (response == "Y" || response == "y") {
				try {
					addWriteIn(uce.getUnknownName());
					System.out.println("This candidate was successfully added to the ballot");
					this.screen();
				} catch (CandidateExistsException cee) {
					System.out.println("This candidate already exists");
					this.screen();
				}
			}

			this.screen();
		} catch (DuplicateVotesException dve) {
			System.out.println("You can't vote for the same candidate twice");
			this.screen();
		}
	}
}
