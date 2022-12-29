import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ShowExamples 
{
	ShowManager1 sm1 = new ShowManager1();
	LinkedList<Show> shows = new LinkedList<Show>();
	ShowSummary report1 = new ShowSummary();

	Show startrek = new Show("Star Trek", 1800, 45.0, false);
	Show futurama = new Show("Futurama", 1900, 23.0, false);
	Show animaniacs = new Show("Animaniacs", 1630, 7.0, false);
	Show sesamestreet = new Show("Sesame Street", 900, 60.0, false);

	public ShowExamples()
	{
		shows.add(startrek);
		report1.primetime.add(startrek);

		shows.add(futurama);
		report1.primetime.add(futurama);

		shows.add(animaniacs);
		report1.daytime.add(animaniacs);

		shows.add(sesamestreet);
		report1.daytime.add(sesamestreet);
	}
	
	
	//oranizeShowsTests
	
	@Test
	public void oranizeShowsEqual() {
		ShowSummary aListOfShows = new ShowSummary();
		aListOfShows.daytime.add(animaniacs);
		aListOfShows.primetime.add(startrek);
		aListOfShows.primetime.add(futurama);
		aListOfShows.daytime.add(sesamestreet);
		assertEquals(aListOfShows, organizeShows(shows));
	}
	
	@Test
	public void oranizeShowsNotEqual() {
		ShowSummary aListOfShows = new ShowSummary();
		aListOfShows.daytime.add(animaniacs);
		aListOfShows.primetime.add(startrek);
		aListOfShows.primetime.add(futurama);
		assertNotEquals(aListOfShows, organizeShows(shows));
	}
	
	@Test
	public void oranizeShowsSameShows() {
		ShowSummary aListOfShows = new ShowSummary();
		LinkedList<Show> shows = new LinkedList<Show>();
		aListOfShows.daytime.add(animaniacs);
		aListOfShows.daytime.add(animaniacs);
		aListOfShows.daytime.add(animaniacs);
		shows.add(animaniacs);
		shows.add(animaniacs);
		shows.add(animaniacs);
		assertEquals(aListOfShows, organizeShows(shows));
	}
	
	@Test
	public void oranizeShowsSameShowsUnequalShow() {
		ShowSummary aListOfShows = new ShowSummary();
		LinkedList<Show> shows = new LinkedList<Show>();
		aListOfShows.daytime.add(animaniacs);
		aListOfShows.daytime.add(animaniacs);
		aListOfShows.daytime.add(animaniacs);
		shows.add(animaniacs);
		shows.add(animaniacs);
		assertNotEquals(aListOfShows, organizeShows(shows));
	}
	
	@Test
	public void oranizeShowsEmpty() {
		ShowSummary aListOfShows = new ShowSummary();
		LinkedList<Show> shows = new LinkedList<Show>();
		assertEquals(aListOfShows, organizeShows(shows));
	}
	
	
	
	
	//Equals tests
	
	@Test
	public void instructorTestShowSummary_EmptyReport() {
		ShowSummary report2 = new ShowSummary();
		assertFalse(report1.equals(report2));
	}


	@Test
	public void instructorTestShowSummary_WrongOrder() {
		ShowSummary report2 = new ShowSummary();

		report2.primetime.add(futurama);
		report2.primetime.add(startrek);
		report2.daytime.add(animaniacs);
		report2.daytime.add(sesamestreet);

		assertFalse(report1.equals(report2));
	}

	@Test
	public void instructorTestShowSummary_DifferentInstances() {
		ShowSummary report2 = new ShowSummary();

		Show startrek2 = new Show("Star Trek", 1800, 60.0, true);
		Show futurama2 = new Show("Futurama", 1900, 20.0, false);
		Show animaniacs2 = new Show("Animaniacs", 1630, 9.0, true);
		Show sesamestreet2 = new Show("Sesame Street", 900, 55.0, false);

		report2.primetime.add(startrek2);
		report2.primetime.add(futurama2);
		report2.daytime.add(animaniacs2);
		report2.daytime.add(sesamestreet2);

		assertTrue(report1.equals(report2));
	}

	@Test
	public void instructorTestShowSummary_SameInstances() {
		ShowSummary report2 = report1;
		assertTrue(report1.equals(report2));
	}


	@Test
	public void instructorTestOrganizeShows() 
	{
		ShowSummary report2 = sm1.organizeShows(shows);
		assertEquals(report1, report2);
	}
	
	@Test
	public void differentSizes() {
		ShowSummary num2 = new ShowSummary();
		LinkedList<Show> show0 = new LinkedList<Show>();
		Show show1 = new Show("Star Trek", 1800, 60.0, true);
		Show show2 = new Show("Futurama", 1900, 20.0, false);
		Show show3 = new Show("Animaniacs", 1630, 9.0, true);
		show0.add(show1);
		show0.add(show2);
		show0.add(show3);

		num2.daytime.add(show1);
		num2.daytime.add(show2);
		num2.daytime.add(show3);
		
		assertFalse(num2.equals(report1));
		
	}
	
	@Test
	public void sameSizes() {
		ShowSummary num2 = new ShowSummary();
		Show show1 = new Show("Star Trek", 1800, 60.0, true);
		Show show2 = new Show("Futurama", 1900, 20.0, false);
		Show show3 = new Show("Animaniacs", 1630, 9.0, true);
		Show show4 = new Show("Sesame Street", 900, 55.0, false);
	
		num2.primetime.add(show1);
		num2.primetime.add(show2);
		num2.daytime.add(show3);
		num2.daytime.add(show4);
		
		assertTrue(num2.equals(report1));
		
	}
	
	@Test
	public void sameSizesDifferentTimes() {
		ShowSummary num2 = new ShowSummary();
		Show show1 = new Show("Star Trek", 1800, 60.0, true);
		Show show2 = new Show("Futurama", 1900, 20.0, false);
		Show show3 = new Show("Animaniacs", 1630, 9.0, true);
		Show show4 = new Show("Sesame Street", 900, 55.0, false);
	
		num2.primetime.add(show1);
		num2.latenight.add(show2);
		num2.daytime.add(show3);
		num2.latenight.add(show4);
		
		assertFalse(num2.equals(report1));
		
	}
	
	@Test
	public void differentOrder() {
		ShowSummary num2 = new ShowSummary();
		Show show1 = new Show("Star Trek", 1800, 60.0, true);
		Show show2 = new Show("Futurama", 1900, 20.0, false);
		Show show3 = new Show("Animaniacs", 1630, 9.0, true);
		Show show4 = new Show("Sesame Street", 900, 55.0, false);
	
		num2.daytime.add(show1);
		num2.daytime.add(show2);
		num2.primetime.add(show3);
		num2.primetime.add(show4);
		
		assertFalse(num2.equals(report1));
		
		
		
	}
	
	@Test
	public void differentTitle() {
		ShowSummary num2 = new ShowSummary();
		Show show1 = new Show("aaaa", 1800, 60.0, true);
		Show show2 = new Show("bbbb", 1900, 20.0, false);
		Show show3 = new Show("ccc", 1630, 9.0, true);
		Show show4 = new Show(" Street", 900, 55.0, false);
	
		num2.primetime.add(show1);
		num2.primetime.add(show2);
		num2.daytime.add(show3);
		num2.daytime.add(show4);
		
		assertFalse(num2.equals(report1));
		
	}
	
	@Test
	public void differentBCTime() {
		ShowSummary num2 = new ShowSummary();
		Show show1 = new Show("Star Trek", 20, 60.0, true);
		Show show2 = new Show("Futurama", 100, 20.0, false);
		Show show3 = new Show("Animaniacs", 4630, 9.0, true);
		Show show4 = new Show("Sesame Street", 1900, 55.0, false);
	
		num2.primetime.add(show1);
		num2.primetime.add(show2);
		num2.daytime.add(show3);
		num2.daytime.add(show4);
		
		assertFalse(num2.equals(report1));
		
	}
	
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
	
	//Subtasks
	
	//consumes a list of Show
	//produce/return a report/ShowSummary containing all daytime, primetime, and latenight shows 
	//that are not specials
	//identify type of show
	//add the type of show to a specific show type list (daytime, primetime, latenight)
	//add the type of show list of a ShowSummary list
	
	
	
	//difference between ShowManager1 and ShowManager2
	
	//ShowManager1 iterates through a list of Show via a for loop and determines if the show is not special
	//and what kind of show it is based on its broadcast time.
	//it then adds this show to a specific type of show list (daytime, primetime, latenight) and then
	//adds that list to a list of ShowSummary
	
	//ShowManager2 first throws the list of Show into an inital for loop and looks to filter out (get rid of)
	//all shows that ARE special and if they are not special, the show gets tossed onto the next loop.
	//this creates a filtered list of shows that are not special which gets caught by the next for loop.
	//the second for loop determines what type of show it is based on its broadcast time and then classifies the
	//show and throws in into a new list based on what type of show it is.
	//(if its a primetime show, it gets added to a primetime show list that is then added to a ShowSummary list).

	
}
