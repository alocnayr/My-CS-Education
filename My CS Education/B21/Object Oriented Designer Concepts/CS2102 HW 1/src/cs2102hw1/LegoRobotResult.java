package cs2102hw1;

public class LegoRobotResult implements IResult {

	LegoRobotTeam team1;
	LegoRobotTeam team2;
	double team1points;
	double team2points;
	int team1tasks;
	int team2tasks;
	boolean team1fell;
	boolean team2fell;

	LegoRobotResult(LegoRobotTeam team1, LegoRobotTeam team2, double team1points, int team1tasks, boolean team1fell,
			double team2points, int team2tasks, boolean team2fell) {

		this.team1 = team1;
		this.team2 = team2;
		this.team1points = team1points;
		this.team2points = team2points;
		this.team1tasks = team1tasks;
		this.team1fell = team1fell;
		this.team2fell = team2fell;

	}

	public boolean isValid() {
		if (this.team1tasks < 8 && (this.team2tasks < 8) && (this.team1points <= 16 && (this.team2points <= 16))) {
			return true;
		}

		else {
			return false;
		}
	}

	
//#3

	public double getScore(double points, int tasks, boolean fellDown) {
		double score = points + tasks;
		if (fellDown == true) {
			return score - 5;
		} else {
			return score;
		}
	}

	public IContestant getWinner() {
		if (getScore(team1points, team1tasks, team1fell) > getScore(team2points, team2tasks, team2fell)) {
			return team1;
		} else {
			return team2;
		}
	}
}
