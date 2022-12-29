import java.util.HashMap;
import java.util.LinkedList;

class ElectionData {
	private LinkedList<String> ballot = new LinkedList<String>();
	private HashMap<String, Integer> firstVotes;
	private HashMap<String, Integer> secondVotes;
	private HashMap<String, Integer> thirdVotes;

	ElectionData() {
		this.firstVotes = new HashMap<String, Integer>();
		this.secondVotes = new HashMap<String, Integer>();
		this.thirdVotes = new HashMap<String, Integer>();
	}

	/**
	 * Prints the candidates in a ballot
	 */
	public void printBallot() {
		System.out.println("The candidates are ");
		for (String s : ballot) {
			System.out.println(s);
		}
	}

	/**
	 * Consumes 3 different strings representing vote choices and adds them to the
	 * hashmap or throws exception if the candidate is unknown or repeated
	 * 
	 * @throws UnknownCandidateException error if a candidate is unknown
	 * @throws DuplicateVotesException   error if a candidate is repeated
	 */
	public void processVote(String voteChoice1, String voteChoice2, String voteChoice3)
			throws DuplicateVotesException, UnknownCandidateException {

		if (ballot.contains(voteChoice1) && ballot.contains(voteChoice2) && ballot.contains(voteChoice3)

				&& (!(voteChoice1.equals(voteChoice2))) && (!(voteChoice2.equals(voteChoice3)))
				&& (!(voteChoice1.equals(voteChoice3)))) {
			for (String stringy : firstVotes.keySet()) {
				if (stringy.equals(voteChoice1)) {
					firstVotes.put(voteChoice1, firstVotes.get(stringy) + 1);
				}
			}
			for (String stringy : secondVotes.keySet()) {
				if (stringy.equals(voteChoice1)) {
					secondVotes.put(voteChoice2, secondVotes.get(stringy) + 1);
				}
			}
			for (String stringy : thirdVotes.keySet()) {
				if (stringy.equals(voteChoice1)) {
					thirdVotes.put(voteChoice3, thirdVotes.get(stringy) + 1);
				}
			}
		} else if (voteChoice1.equals(voteChoice2)) {

			throw new DuplicateVotesException(voteChoice1);

		} else if (voteChoice2.equals(voteChoice3)) {

			throw new DuplicateVotesException(voteChoice2);

		} else if (voteChoice1.equals(voteChoice3)) {

			throw new DuplicateVotesException(voteChoice3);

		} else if (!(ballot.contains(voteChoice1))) {

			throw new UnknownCandidateException(voteChoice1);

		} else if (!(ballot.contains(voteChoice2))) {

			throw new UnknownCandidateException(voteChoice2);

		} else if (!(ballot.contains(voteChoice3))) {

			throw new UnknownCandidateException(voteChoice3);

		}
	}

	/**
	 * Consumes a new candidates name and adds to ballot or throws an error if name
	 * already on ballot
	 * 
	 * @param candidateName The name of the cadidate
	 * @throws CandidateExistsException error if the candidate already exists
	 */
	public void addCandidate(String candidateName) throws CandidateExistsException {
		if (this.ballot.contains(candidateName))

			throw new CandidateExistsException(candidateName);

		else {
			this.ballot.add(candidateName);
			this.firstVotes.put(candidateName, 0);
			this.secondVotes.put(candidateName, 0);
			this.thirdVotes.put(candidateName, 0);
		}
	}

	/**
	 * Produces the name of a candidate who recieved more than 50% of first votes,
	 * or if all candidates received less than or equal to 50%, return a string
	 * indicating that a runoff is required required
	 * 
	 * @return name of winning candidate or runoff required
	 */
	public String findWinnerMostFirstVotes() {

		int allVotes = totalFirstVotes(ballot);
		double percentage = 0;

		for (String candidate : ballot) {
			percentage = (firstVotes.get(candidate) / allVotes) * 100;

			if (percentage > 50) {
				return candidate;
			}
		}
		return "Runoff required";
	}

	/**
	 * Consumes a list of string and produces the total number of first place votes
	 * 
	 * @param ballot a list of string
	 * @return the total number of first place votes
	 */
	int totalFirstVotes(LinkedList<String> ballot) {
		int totalVotes = 0;
		for (String candidate : ballot) {
			totalVotes = totalVotes + firstVotes.get(candidate);
		}
		return totalVotes;
	}

	/**
	 * Return the candidate name with the most vote points
	 * 
	 * @return a string indicating the winning candidate
	 */
	public String findWinnerMostPoints() {

		int totalPointsCandidate = 0;
		int maxPoints = 0;

		HashMap<Integer, String> electionCadidates = new HashMap<Integer, String>();

		for (String candidateName : ballot) {

			totalPointsCandidate = (firstVotes.get(candidateName) * 3) + (secondVotes.get(candidateName) * 2)
					+ (thirdVotes.get(candidateName) * 1);
			electionCadidates.put(totalPointsCandidate, candidateName);

			if (totalPointsCandidate > maxPoints) {
				maxPoints = totalPointsCandidate;
			}
		}
		return electionCadidates.get(maxPoints);
	}
}
