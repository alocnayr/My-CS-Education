package cs2102hw1;

public class LegoRobotTeam implements IContestant {

	String school;
	String specialFeature;
	int previousScore;

	LegoRobotTeam(String school, String specialFeature, int previousScore) {

		this.school = school;
		this.specialFeature = specialFeature;
		this.previousScore = previousScore;
	}

	public boolean expectToBeat(LegoRobotTeam contestant) {
		if (contestant.previousScore >= this.previousScore) {
			return false;
		} else {
			return true;
		}
	}
}
