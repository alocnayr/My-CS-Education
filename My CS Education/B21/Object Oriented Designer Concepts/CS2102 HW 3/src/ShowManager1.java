import java.util.LinkedList;

class ShowManager1 {

	ShowManager1() {
	}

	// consumes list of show
	// produces report containing all daytime, primetime, and light night shows
	// (these are all linkedlists)

	/**
	 * consumes a list of Show and produces a report containing all daytime, primetime, and late night shows that are not specials
	 * @param shows A list of Show
	 * @return a ShowSummary of shows which are not specials
	 */
	public ShowSummary organizeShows(LinkedList<Show> shows)
	{
	    ShowSummary holder = new ShowSummary();
		
	    for (Show aShow : shows) {
	    	
		if (aShow.broadcastTime >= 600 && aShow.broadcastTime < 1700 &&
		aShow.isSpecial == false) {
			holder.daytime.add(aShow);
		}
		if (aShow.broadcastTime > 1700 && aShow.broadcastTime < 2200 &&
				aShow.isSpecial == false) {
					holder.primetime.add(aShow);
		}
		
		if (aShow.broadcastTime > 2200 && aShow.broadcastTime < 100 &&
				aShow.isSpecial == false) {
					holder.latenight.add(aShow);
	
	}
	    }
	    return holder;
	}
}