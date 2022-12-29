package cs2102hw1;

import static org.junit.Assert.*;
import org.junit.Test;
public class Examples {
    public Examples(){}
    
    /*
    // This shows what a test case looks like
    @Test 
    public void simpleCheck() {
assertEquals(4, 4);
    }
    */
    
    
    // SoccerTeam(String country, String jerseyColor, boolean ritual, int wins, int losses)
    // SoccerResult(SoccerTeam team1, SoccerTeam team2, double team1points, double team2points) {
     	
    
    //SoccerResult isValid tests

    
    //both 150
    	
    @Test
    public void bothvalid150() {
    	SoccerTeam both150 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam always150 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerResult same150 = new SoccerResult(both150, always150, 150, 150);
    	assertFalse(same150.isValid());
    }
    
    
    //one 150
    
    @Test
    public void onevalid150() {
    	SoccerTeam one150 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam onenot150 = new SoccerTeam("norway", "red", true, 75, 0);
    	SoccerResult different150 = new SoccerResult(one150, onenot150, 150, 75);
    	assertFalse(different150.isValid());
    }
    
    //both under 150
    
    @Test
    public void good150() {
    	SoccerTeam under150 = new SoccerTeam("norway", "red", true, 50, 0);
    	SoccerTeam alsounder150 = new SoccerTeam("norway", "red", true, 60, 0);
    	SoccerResult bothunder150 = new SoccerResult(under150, alsounder150, 80, 90);
    	assertTrue(bothunder150.isValid());}
    
    //one under 150
    
    @Test
    public void oneun150() {
    	SoccerTeam under150 = new SoccerTeam("norway", "red", true, 50, 0);
    	SoccerTeam alsounder150 = new SoccerTeam("norway", "red", true, 60, 0);
    	SoccerResult oneunder150 = new SoccerResult(under150, alsounder150, 25, 149);
    	assertTrue(oneunder150.isValid());}
    
    //both over 150
    
    @Test
    public void over150() {
    	SoccerTeam under150 = new SoccerTeam("norway", "red", true, 50, 0);
    	SoccerTeam alsounder150 = new SoccerTeam("norway", "red", true, 60, 0);
    	SoccerResult oneunder150 = new SoccerResult(under150, alsounder150, 160, 160);
    	assertFalse(oneunder150.isValid());}
    
    //one over 150
    @Test
    public void oneover150() {
    	SoccerTeam under150 = new SoccerTeam("norway", "red", true, 50, 0);
    	SoccerTeam alsounder150 = new SoccerTeam("norway", "red", true, 60, 0);
    	SoccerResult oneover150 = new SoccerResult(under150, alsounder150, 160, 150);
    	assertFalse(oneover150.isValid());}
    
    //over over 150 one under 150
    
    @Test
    public void overunder() {
    	SoccerTeam under150 = new SoccerTeam("norway", "red", true, 50, 0);
    	SoccerTeam alsounder150 = new SoccerTeam("norway", "red", true, 60, 0);
    	SoccerResult oneoveroneunder = new SoccerResult(under150, alsounder150, 160, 40);
    	assertFalse(oneoveroneunder.isValid());}
    
    
  //LegoRobot isValid tests
    
    //LegoRobotTeam(String school, String specialFeature, int previousScore) {
    //LegoRobotResult(LegoRobotTeam team1, LegoRobotTeam team2, double team1points, int team1tasks, boolean team1fell,
			//double team2points, int team2tasks, boolean team2fell) {
    
    //both fewer than 8 and <=16
    
    @Test
    public void allvalid() {
    	 LegoRobotTeam good = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam alsogood = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult perfect = new LegoRobotResult(good, alsogood, 5, 6, false, 7, 5, false);
    	    assertTrue(perfect.isValid());}
    
  //one = 8 and <=16
    

    @Test
    public void fail() {
    	 LegoRobotTeam bien = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam sogood = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult notgoodenough = new LegoRobotResult(bien, sogood, 8, 8, false, 7, 5, false);
    	    assertFalse(notgoodenough.isValid());}
    
  //both = 8 and <=16
    
    @Test
    public void equal8() {
    	 LegoRobotTeam ok = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam oks = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult both8 = new LegoRobotResult(ok, oks, 8, 8, false, 7, 8, false);
    	    assertFalse(both8.isValid());}
    
  //both >8 and <=16
    
    @Test
    public void bigger8() {
    	 LegoRobotTeam big = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam bigs = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult biggerthan8 = new LegoRobotResult(big, bigs, 8, 9, false, 7, 10, false);
    	    assertFalse(biggerthan8.isValid());}
    
  //one 8 and other >8 and <=16
    
    @Test
    public void one8otherbig() {
    	 LegoRobotTeam other = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam others = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult one8not8 = new LegoRobotResult(other, others, 8, 8, false, 7, 10, false);
    	    assertFalse(one8not8.isValid());}
    
  //both fewer than 8 and both 16
    
    @Test
    public void fewer8() {
    	 LegoRobotTeam big = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam bigs = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult fewerbigger = new LegoRobotResult(big, bigs, 16, 6, false, 16, 7, false);
    	    assertTrue(fewerbigger.isValid());}
    
    
  //both fewer than 8 and both >16
    
    @Test
    public void bothbigger8() {
    	 LegoRobotTeam big = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam bigs = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult bigbig = new LegoRobotResult(big, bigs, 96, 6, false, 144, 7, false);
    	    assertFalse(bigbig.isValid());}
    
  //both fewer than 8 and one >16
    
    @Test
    public void onebigger16() {
    	 LegoRobotTeam big = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam bigs = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult biggy = new LegoRobotResult(big, bigs, 16, 6, false, 17, 7, false);
    	    assertFalse(biggy.isValid());}
    
    
  //LegoRobot getScore tests
    
    //LegoRobotTeam(String school, String specialFeature, int previousScore)
    //LegoRobotResult(LegoRobotTeam team1, LegoRobotTeam team2, double team1points, int team1tasks, boolean team1fell,
	//double team2points, int team2tasks, boolean team2fell) {
    
   //no -5
    @Test
    public void standingguy() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 5, 6, true, 7, 5, false);
    	   assertEquals(152, falldown.getScore(150, 2, false),0.001);
    }
    
    //-5
    
    @Test
    public void fellover() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 5, 6, true, 7, 5, false);
    	   assertEquals(147, falldown.getScore(150, 2, true),0.001);
    }
    
  //winner tests
    
    //Match(IContestant contestant1, IContestant contestant2, IResult results) {
	//this.contestant1 = contestant1;
	//this.contestant2 = contestant2;
	//this.results = results;
    
    
    //null
    @Test
    public void notgood() {
        SoccerTeam soccer = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam soccer1 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerResult soccer2 = new SoccerResult(soccer, soccer1, 140, 150);
        Match winning = new Match(soccer, soccer1, soccer2);
    	assertNull(winning.winner());
    }
    
    
    @Test
    public void winnertest() {
        SoccerTeam soccer = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam soccer1 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerResult soccer2 = new SoccerResult(soccer, soccer1, 120, 50);
        Match winning = new Match(soccer, soccer1, soccer2);
    	assertSame(soccer, winning.winner());
    }
    
    
    @Test
    public void losser() {
        SoccerTeam soccer = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam soccer1 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerResult soccer2 = new SoccerResult(soccer, soccer1, 50, 120);
        Match winning = new Match(soccer, soccer1, soccer2);
    	assertSame(soccer1, winning.winner());
    }
    
    @Test
    public void badrobot() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 5, 6, true, 7, 5, false);
    	    Match robot = new Match(fallingdown, standing, falldown);
    	    assertSame(standing, robot.winner());
    }
    
    @Test
    public void goodrobot() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 5, 6, false, 7, 5, false);
    	    Match robot = new Match(fallingdown, standing, falldown);
    	    assertSame(fallingdown, robot.winner());
    }
    
    
  //SoccerResult getWinner tests
    
    @Test
    public void winning() {
        SoccerTeam soccer = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam soccer1 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerResult soccer2 = new SoccerResult(soccer, soccer1, 120, 50);
    	assertSame(soccer, soccer2.getWinner());
    }
    
    @Test
    public void losing() {
        SoccerTeam soccer = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerTeam soccer1 = new SoccerTeam("norway", "red", true, 150, 0);
    	SoccerResult soccer2 = new SoccerResult(soccer, soccer1, 40, 100);
    	assertSame(soccer1, soccer2.getWinner());
    }
    
  //LegoRobot getWinner tests
    
    @Test
    public void fallingdoesntwins() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 5, 6, true, 7, 5, false);
    	    assertSame(standing, falldown.getWinner());
    }
    
    @Test
    public void winwin() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 5, 6, true, 7, 15, false);
    	    assertSame(standing, falldown.getWinner());
    }

    
    @Test
    public void fallingwins() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 10, 6, false, 17, 5, false);
    	    assertSame(standing, falldown.getWinner());
    }
    
    @Test
    public void oof() {
    	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    	    LegoRobotResult falldown = new LegoRobotResult(fallingdown, standing, 10, 60, false, 17, 5, false);
    	    Match robot = new Match(fallingdown, standing, falldown);
    	    assertNull(robot.winner());
    }
    
    
    //LegoRobot expectToBeat
   // @Test
  //  public void expectbeatrobot() {
    //	 LegoRobotTeam fallingdown = new LegoRobotTeam("wpi", "arm", 10);
    //	    LegoRobotTeam standing = new LegoRobotTeam("wpi", "arm", 4);
    //	    assertTrue(fallingdown, LegoRobotTeam.expectToBeat());}
    
    
  //  boolResult = soccerTeamCheck.expectToBeat(soccerTeamCheck);
   // boolResult = robotsTeamCheck.expectToBeat(robotsTeamCheck);
    @Test
    public void ree() {
    	  SoccerTeam soccerTeamCheck = new SoccerTeam("WPI", "maroon", true, 4, 6);
    	  assertFalse(soccerTeamCheck.expectToBeat(soccerTeamCheck));
    }
    
    @Test
    public void expectbeatrobot() {
    LegoRobotTeam robotsTeamCheck = new LegoRobotTeam("WPI","goat cheese", 10);
    assertFalse(robotsTeamCheck.expectToBeat(robotsTeamCheck));
    }

    
    
  //SoccerResult expectToBeat tests
    
    
  
}
