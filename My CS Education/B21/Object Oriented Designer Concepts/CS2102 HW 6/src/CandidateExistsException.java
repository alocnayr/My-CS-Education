public class CandidateExistsException extends Exception {

	private String candidateName;

	public CandidateExistsException(String name) {
		this.candidateName = name;
	}

	/**
	 * An exception that is called when the cadidate name is known
	 * 
	 * @return returns the known name of the cadidate
	 */
	public String CandidateExistsName() {
		return this.candidateName;
	}

}
