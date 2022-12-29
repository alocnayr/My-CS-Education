public class DuplicateVotesException extends Exception {
	private String candidateName;

	public DuplicateVotesException(String name) {
		this.candidateName = name;
	}

	/**
	 * An exception that is called when the cadidate name is repeated
	 * 
	 * @return returns the repeated name of the cadidate
	 */
	public String getDuplicateName() {
		return this.candidateName;
	}
}
