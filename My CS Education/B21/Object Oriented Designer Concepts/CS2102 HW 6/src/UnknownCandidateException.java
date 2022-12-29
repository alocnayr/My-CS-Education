public class UnknownCandidateException extends Exception {

	private String candidateName;

	public UnknownCandidateException(String name) {
		this.candidateName = name;
	}

	/**
	 * An exception that is called when the cadidate name is unknown
	 * 
	 * @return returns the unknown name of the cadidate
	 */
	public String getUnknownName() {
		return this.candidateName;
	}
}
