package cs2102hw1;

public class SoccerTeam implements IContestant {

	String country;
	String jerseyColor;
	boolean ritual;
	int wins;
	int losses;

	SoccerTeam(String country, String jerseyColor, boolean ritual, int wins, int losses) {

		this.country = country;
		this.jerseyColor = jerseyColor;
		this.ritual = ritual;
		this.losses = losses;

	}

	public boolean expectToBeat(SoccerTeam contestant) {
		if (contestant.ritual == true && (this.ritual == false)) {
			return false;
		} else if (contestant.ritual == false && (this.ritual == true)) {
			return true;
		} else {
			if ((contestant.wins - contestant.losses) >= (this.wins - this.losses)) {
				return false;
			} else {
				return true;
			}
		}
	}
}
