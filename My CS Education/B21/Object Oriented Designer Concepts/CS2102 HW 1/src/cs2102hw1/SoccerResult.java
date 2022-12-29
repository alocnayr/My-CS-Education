package cs2102hw1;

public class SoccerResult implements IResult {

	SoccerTeam team1;
	SoccerTeam team2;
	double team1points;
	double team2points;

	SoccerResult(SoccerTeam team1, SoccerTeam team2, double team1points, double team2points) {

		this.team1 = team1;
		this.team2 = team2;
		this.team1points = team1points;
		this.team2points = team2points;

	}
	

	public boolean isValid() {
		if (this.team1points < 150 && (this.team2points < 150)) {
			return true;
		} else {
			return false;
		}
	}

	public IContestant getWinner() {
		if (team1points > team2points)
		{
		return team1;
		}
		else {
				return team2;}
			}	
	}

// valid if both teams are under 150 points
