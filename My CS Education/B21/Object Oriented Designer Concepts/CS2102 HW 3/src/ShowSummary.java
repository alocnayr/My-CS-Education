import java.util.LinkedList;

class ShowSummary {

	LinkedList<Show> daytime;
	LinkedList<Show> primetime;
	LinkedList<Show> latenight;

	ShowSummary() {
		this.daytime = new LinkedList<Show>();
		this.primetime = new LinkedList<Show>();
		this.latenight = new LinkedList<Show>();
	}

	ShowSummary(LinkedList<Show> daytime, LinkedList<Show> primetime, LinkedList<Show> latenight) {
		this.daytime = daytime;
		this.primetime = primetime;
		this.latenight = latenight;
	}

	public boolean equals(Object obj) {
		ShowSummary showSummary = (ShowSummary) obj;

		if (this.daytime.size() != showSummary.daytime.size() || this.primetime.size() != showSummary.primetime.size()
				|| (this.latenight.size() != showSummary.latenight.size())) {
			return false;

		}

		for (int i = 0; i < this.daytime.size(); i++) {
			Show aShow = daytime.get(i);
			Show anotherShow = showSummary.daytime.get(i);

			if (!aShow.title.equals(anotherShow.title) || aShow.broadcastTime != anotherShow.broadcastTime) {
				return false;
			}
		}
		for (int i = 0; i < this.primetime.size(); i++) {
			Show aShow = primetime.get(i);
			Show anotherShow = showSummary.primetime.get(i);

			if (!aShow.title.equals(anotherShow.title) || aShow.broadcastTime != anotherShow.broadcastTime) {
				return false;
			}
		}
		for (int i = 0; i < this.latenight.size(); i++) {
			Show aShow = latenight.get(i);
			Show anotherShow = showSummary.latenight.get(i);

			if (!aShow.title.equals(anotherShow.title) || aShow.broadcastTime != anotherShow.broadcastTime) {
				return false;
			}
		}
		return true;
	}
}
