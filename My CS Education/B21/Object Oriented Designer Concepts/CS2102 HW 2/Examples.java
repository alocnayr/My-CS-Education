package cs2102hw2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Examples {

	@Test
	public void testisnormal() {
		Dillo mydillo = new Dillo(10,false);
		assertFalse(mydillo.canShelter());
	}
	
	@Test
	public void fishnormalsize() {
		Fish fishy = new Fish(1,2.3);
		assertTrue(fishy.isNormalSize());
		
	}
	
	@Test
	public void sharknormalsize() {
		Shark sharky = new Shark(2, 4);
		assertTrue(sharky.isNormalSize());
	}
	
	
	@Test
	public void dangerousshark() {
		Shark sharky = new Shark(10000,100000);
		assertTrue(sharky.isDangerToPeople());
	}
	
	@Test
	public void nondangerousshark() {
		Shark sharky = new Shark(10000,0);
		assertFalse(sharky.isDangerToPeople());
	}
	
	@Test
	public void dangerousboa() {
		Boa aboa = new Boa("bob", 2, "people");
		assertTrue(aboa.isDangerToPeople());
	}
	
	@Test
	public void nondangerousboa() {
		Boa aboa = new Boa("bob", 2, "apples");
		assertFalse(aboa.isDangerToPeople());
	}
	
}
	
