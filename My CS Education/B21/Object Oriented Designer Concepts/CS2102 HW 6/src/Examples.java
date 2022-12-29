
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

public class Examples {

	public Examples() {
	}

	ElectionData Setup1() {

		ElectionData ED = new ElectionData();

		// put candidates on the ballot
		try {

			ED.addCandidate("gompei");
			ED.addCandidate("husky");
			ED.addCandidate("ziggy");

		} catch (Exception e) {
		}

		// cast votes

		try {

			ED.processVote("gompei", "husky", "ziggy");
			ED.processVote("gompei", "ziggy", "husky");
			ED.processVote("gompei", "husky", "ziggy");

		} catch (Exception e) {
		}

		return (ED);

	}

	ElectionData tieTest() {

		ElectionData ED = new ElectionData();

		// put candidates on the ballot

		try {

			ED.addCandidate("steve");
			ED.addCandidate("jobs");
			ED.addCandidate("tim");

		} catch (Exception e) {
		}
		// cast votes
		try {

			ED.processVote("steve", "jobs", "tim");
			ED.processVote("jobs", "tim", "steve");
			ED.processVote("tim", "steve", "jobs");

		} catch (Exception e) {
		}

		return (ED);
	}

	// finderWinnerMostFirstVotes tests
	@Test
	public void mostFirstWinnerTest() {

		assertEquals("gompei", Setup1().findWinnerMostFirstVotes());

	}

	@Test
	public void mostFirstWinnerNotEquals() {

		assertNotEquals("husky", Setup1().findWinnerMostFirstVotes());

	}

	@Test
	public void mostFirstWinnerRunoffTest() {
		assertEquals("Runoff required", tieTest().findWinnerMostFirstVotes());
	}

	@Test
	public void findWinnerMostFirstVotesSingleWinner() {
		assertEquals("gompei", Setup1().findWinnerMostFirstVotes());

	}

	@Test
	public void findWinnerMostFirstVotesTie() {
		assertEquals("Runoff required", tieTest().findWinnerMostFirstVotes());

	}

	// findWinnerMostPoints tests

	@Test
	public void findWinnerMostPointsSingleWinner() {
		assertEquals("gompei", Setup1().findWinnerMostPoints());
	}

	@Test
	public void findWinnerMostPointsTie() {

		LinkedList<String> tieList = new LinkedList<String>();

		tieList.addFirst("gompei");
		tieList.addFirst("husky");

		assertTrue(tieList.contains(Setup1().findWinnerMostPoints()));

	}

// UnknownCandidateException test

	@Test(expected = UnknownCandidateException.class)

	public void unknownCandidate() throws UnknownCandidateException {
		ElectionData ED = Setup1();
		try {
			ED.processVote("gompei", "steven", "jobs");
		} catch (DuplicateVotesException e) {
		}
	}

// DuplicateVotesException test

	@Test(expected = DuplicateVotesException.class)

	public void sameCandidateRepeated() throws DuplicateVotesException {
		ElectionData ED = Setup1();
		try {
			ED.processVote("gompei", "gompei", "gompei");
		} catch (UnknownCandidateException e) {
		}
	}

// CandidateExistsException test

	@Test(expected = CandidateExistsException.class)

	public void theCandidateExists() throws CandidateExistsException {
		ElectionData ED = Setup1();
		ED.addCandidate("gompei");

	}

}
