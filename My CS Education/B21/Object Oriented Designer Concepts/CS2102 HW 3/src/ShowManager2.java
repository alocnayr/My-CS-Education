import java.util.LinkedList;

class ShowManager2 {

	ShowManager2() {
	}

	/**
	 * consumes a list of Show and produces a report containing all daytime,
	 * primetime, and late night shows that are not specials
	 * 
	 * @param shows A list of Show
	 * @return a ShowSummary of shows which are not specials
	 */
	public ShowSummary organizeShows(LinkedList<Show> shows) {
		LinkedList<Show> daytime = new LinkedList<>();
		LinkedList<Show> primetime = new LinkedList<>();
		LinkedList<Show> latenight = new LinkedList<>();
		LinkedList<Show> filtered = new LinkedList<>();

		for (Show aShow : shows) {

			if (aShow.isSpecial == false) {
				filtered.add(aShow);
			}

		}

		for (Show aShow : filtered) {

			if (aShow.broadcastTime >= 600 && aShow.broadcastTime < 1700) {
				daytime.add(aShow);
			}
			if (aShow.broadcastTime > 1700 && aShow.broadcastTime < 2200) {
				primetime.add(aShow);
			}

			if (aShow.broadcastTime >= 2200 && aShow.broadcastTime < 100) {
				latenight.add(aShow);

			}
		}

		return new ShowSummary(daytime, primetime, latenight);
	}

}
