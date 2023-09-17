package escape.tests;

import escape.EscapeGameManager;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.impl.gamemanager.GameObserverImpl;
import escape.impl.gamemanager.GameStatusImpl;
import escape.builder.EscapeGameBuilder;
import escape.impl.movement.movevalidation.rules.PointConflict;
import escape.impl.movement.movevalidation.rules.RuleImpl;
import escape.required.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;

public class QATests {


    private EscapeGameManager<? extends Coordinate> masterSquareFinite = null;
    private EscapeGameManagerImpl squareFinite;
    private EscapeGameManager<? extends Coordinate> masterSquareInfX = null;
    private EscapeGameManagerImpl squareInfX;
    private EscapeGameManager<? extends Coordinate> masterSquareInfY = null;
    private EscapeGameManagerImpl squareInfY;
    private EscapeGameManager<? extends Coordinate> masterSquareInfinite = null;
    private EscapeGameManagerImpl squareInfinite;
    private EscapeGameManager<? extends Coordinate> masterHexFinite = null;
    private EscapeGameManagerImpl hexFinite;
    private EscapeGameManager<? extends Coordinate> masterHexInfX = null;
    private EscapeGameManagerImpl hexInfX;
    private EscapeGameManager<? extends Coordinate> masterHexInfY = null;
    private EscapeGameManagerImpl hexInfY;
    private EscapeGameManager<? extends Coordinate> masterHexInfinite = null;
    private EscapeGameManagerImpl hexInfinite;
    private EscapeGameManager<? extends Coordinate> masterTurnLimit = null;
    private EscapeGameManagerImpl turnLimit;
    private EscapeGameManager<? extends Coordinate> masterTurnLimitMorePieces = null;
    private EscapeGameManagerImpl turnLimitMorePieces;
    private EscapeGameManager<? extends Coordinate> masterPlayerCantMove = null;
    private EscapeGameManagerImpl cantMove;
    private EscapeGameManager<? extends Coordinate> masterSquareConflict = null;
    private EscapeGameManagerImpl conflict;
    private EscapeGameManager<? extends Coordinate> manager = null;
    private EscapeGameManager<? extends Coordinate> test7 = null;
    private EscapeGameManager<? extends Coordinate> test8 = null;
    private EscapeGameManager<? extends Coordinate> infX = null;
    private EscapeGameManager<? extends Coordinate> infY = null;
    private EscapeGameManager<? extends Coordinate> hex = null;
    private EscapeGameManager<? extends Coordinate> turnLimit2 = null;
    private EscapeGameManager<? extends Coordinate> score = null;
    private EscapeGameManager<? extends Coordinate> test9 = null;
    private EscapeGameManager<? extends Coordinate> hex1 = null;
    private EscapeGameManager<? extends Coordinate> pointConflict = null;
    private EscapeGameManager<? extends Coordinate> test10 = null;
    private EscapeGameManager<? extends Coordinate> checkmateManager = null;
    private EscapeGameManagerImpl checkmate;
    private EscapeGameManager<? extends Coordinate> checkmateManager2 = null;
    private EscapeGameManagerImpl checkmate2;
    private EscapeGameManager<? extends Coordinate> checkmateManager3 = null;
    private EscapeGameManagerImpl checkmate3;
    private EscapeGameManager<Coordinate> runthroughManager = null;
    private EscapeGameManagerImpl runthrough;
    private EscapeGameManager<Coordinate> runthroughManager2 = null;
    private EscapeGameManagerImpl runthrough2;
    private EscapeGameManager<Coordinate> negativeSquareManager = null;
    private EscapeGameManagerImpl negativeSquare;




    @BeforeEach
    void setup() throws Exception {
        masterSquareFinite = new EscapeGameBuilder("configurations/mSquareFinite.egc").makeGameManager();
        squareFinite = (EscapeGameManagerImpl) masterSquareFinite;
        masterSquareInfX = new EscapeGameBuilder("configurations/mSquareInfX.egc").makeGameManager();
        squareInfX = (EscapeGameManagerImpl) masterSquareInfX;
        masterSquareInfY = new EscapeGameBuilder("configurations/mSquareInfY.egc").makeGameManager();
        squareInfY = (EscapeGameManagerImpl) masterSquareInfY;
        masterSquareInfinite = new EscapeGameBuilder("configurations/mSquareInfinite.egc").makeGameManager();
        squareInfinite = (EscapeGameManagerImpl) masterSquareInfinite;
        masterHexFinite = new EscapeGameBuilder("configurations/mHexFinite.egc").makeGameManager();
        hexFinite = (EscapeGameManagerImpl) masterHexFinite;
        masterHexInfX = new EscapeGameBuilder("configurations/mHexInfX.egc").makeGameManager();
        hexInfX = (EscapeGameManagerImpl) masterHexInfX;
        masterHexInfY = new EscapeGameBuilder("configurations/mHexInfY.egc").makeGameManager();
        hexInfY = (EscapeGameManagerImpl) masterHexInfY;
        masterHexInfinite = new EscapeGameBuilder("configurations/mHexInfinite.egc").makeGameManager();
        hexInfinite = (EscapeGameManagerImpl) masterHexInfinite;
        masterSquareConflict = new EscapeGameBuilder("configurations/mSquareConflict.egc").makeGameManager();
        conflict = (EscapeGameManagerImpl) masterSquareConflict;
        masterTurnLimit = new EscapeGameBuilder("configurations/mTurnLimit.egc").makeGameManager();
        turnLimit = (EscapeGameManagerImpl) masterTurnLimit;
        masterTurnLimitMorePieces = new EscapeGameBuilder("configurations/mTurnLimitMorePieces.egc").makeGameManager();
        turnLimitMorePieces = (EscapeGameManagerImpl) masterTurnLimitMorePieces;
        masterPlayerCantMove = new EscapeGameBuilder("configurations/mPlayerCantMove.egc").makeGameManager();
        cantMove = (EscapeGameManagerImpl) masterPlayerCantMove;
        manager = new EscapeGameBuilder("configurations/test6.egc").makeGameManager();
        test7 = new EscapeGameBuilder("configurations/test7.egc").makeGameManager();
        test8 = new EscapeGameBuilder("configurations/test8.egc").makeGameManager();
        test9 = new EscapeGameBuilder("configurations/test9.egc").makeGameManager();
        infX =  new EscapeGameBuilder("configurations/infiniteX.egc").makeGameManager();
        infY =  new EscapeGameBuilder("configurations/infiniteY.egc").makeGameManager();
        hex = new EscapeGameBuilder("configurations/hex.egc").makeGameManager();
        turnLimit2 = new EscapeGameBuilder("configurations/turn_limit.egc").makeGameManager();
        score = new EscapeGameBuilder("configurations/score.egc").makeGameManager();
        hex1 = new EscapeGameBuilder("configurations/hex1.egc").makeGameManager();
        pointConflict = new EscapeGameBuilder("configurations/point_conflict.egc").makeGameManager();
        test10 = new EscapeGameBuilder("configurations/test10.egc").makeGameManager();
        checkmateManager = new EscapeGameBuilder("configurations/checkmate.egc").makeGameManager();
        checkmate = (EscapeGameManagerImpl) checkmateManager;
        checkmateManager2 = new EscapeGameBuilder("configurations/playerCantMove2.egc").makeGameManager();
        checkmate2 = (EscapeGameManagerImpl) checkmateManager2;
        checkmateManager3 = new EscapeGameBuilder("configurations/playerCantMove3.egc").makeGameManager();
        checkmate3 = (EscapeGameManagerImpl) checkmateManager3;
        runthroughManager = new EscapeGameBuilder("configurations/runThrough.egc").makeGameManager();
        runthrough = (EscapeGameManagerImpl) runthroughManager;
        runthroughManager2 = new EscapeGameBuilder("configurations/runThrough2.egc").makeGameManager();
        runthrough2 = (EscapeGameManagerImpl) runthroughManager2;
        negativeSquareManager = new EscapeGameBuilder("configurations/mNegativeSquare.egc").makeGameManager();
        negativeSquare = (EscapeGameManagerImpl) negativeSquareManager;


    }


    /*********************************** Final tests ***********************************/




    @Test
    void negativeSquare(){
        Coordinate from = new CoordinateImpl(-100, -100);
        Coordinate to = new CoordinateImpl(-101, -100);
        GameStatusImpl status = (GameStatusImpl) negativeSquare.move(from, to);
        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.WIN);
    }





    @Test
    void finalTest() {
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertEquals(runthrough.getPieceAt(new CoordinateImpl(0, 0)).getPlayer(), "Chris");
        assertEquals(runthrough.getTurnNumber(), 1);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 0);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getBounds().getyMax(), 0);
        assertEquals(runthrough.getBounds().getxMax(), 14);
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.POINT_CONFLICT));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.SCORE));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.TURN_LIMIT));
        assertEquals(runthrough.getRules().get(Rule.RuleID.SCORE), 20);
        assertEquals(runthrough.getRules().get(Rule.RuleID.TURN_LIMIT), 5);
        assertEquals(runthrough.getRules().get(Rule.RuleID.POINT_CONFLICT), 0);
        assertEquals(runthrough.getCoordinateType(), Coordinate.CoordinateType.HEX);
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertTrue(runthrough.getBoard().getPointConflict());
        assertEquals(runthrough.getPlayers()[1], "Pat");
        assertEquals(runthrough.getPieceAt( new CoordinateImpl(0, 0)).getName(), EscapePiece.PieceName.SNAIL);
        GameStatus statusR1P1 = runthrough.move(new CoordinateImpl(0, 0), new CoordinateImpl(1, 1));
        assertTrue(statusR1P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P1).getPath());
        assertEquals(((GameStatusImpl) statusR1P1).getPathSize(), 4);
        assertEquals(statusR1P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getTurnNumber(), 1);
        GameStatus statusR1P2 = runthrough.move(new CoordinateImpl(7, 3), new CoordinateImpl(10, 1));
        assertTrue(statusR1P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P2).getPath());
        assertEquals(((GameStatusImpl) statusR1P2).getPathSize(), 7);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(7, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(10, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(10, 1)), LocationType.EXIT);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P1 = runthrough.move(new CoordinateImpl(5, 2), new CoordinateImpl(1, 2));
        assertTrue(statusR2P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P1).getPath());
        assertEquals(((GameStatusImpl) statusR2P1).getPathSize(), 5);
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 2)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(1, 2)));
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P2 = runthrough.move(new CoordinateImpl(1, -4), new CoordinateImpl(1, 1));
        assertTrue(statusR2P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P2).getPath());
        assertEquals(((GameStatusImpl) statusR2P2).getPathSize(), 5);
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P1 = runthrough.move(new CoordinateImpl(6, -3), new CoordinateImpl(8, -5));
        assertTrue(statusR3P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P1).getPath());
        assertEquals(((GameStatusImpl) statusR3P1).getPathSize(), 2);
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, -5)));
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P2 = runthrough.move(new CoordinateImpl(4, -4), new CoordinateImpl(9, -3));
        assertTrue(statusR3P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P2).getPath());
        assertEquals(((GameStatusImpl) statusR3P2).getPathSize(), 6);
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(4, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, -3)));
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        assertEquals(runthrough.getTurnNumber(), 4);

        GameStatus statusR4P1 = runthrough.move(new CoordinateImpl(8, 3), new CoordinateImpl(9, 1));
        assertTrue(statusR4P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P1).getPath());
        assertEquals(((GameStatusImpl) statusR4P1).getPathSize(), 3);
        assertEquals(statusR4P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P1.getCombatResult(), GameStatus.CombatResult.DEFENDER);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, 3)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        EscapePiece piece = runthrough.getPieceAt(new CoordinateImpl(9, 1));
        assertEquals(((EscapePieceImpl)piece).getValue(), 2);
        assertEquals(runthrough.getTurnNumber(), 4);


        GameStatus statusR4P2 = runthrough.move(new CoordinateImpl(9, 1), new CoordinateImpl(9, 0));
        assertTrue(statusR4P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P2).getPath());
        assertEquals(((GameStatusImpl) statusR4P2).getPathSize(), 1);
        assertEquals(statusR4P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 0)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);

        GameStatus statusR5P1 = runthrough.move(new CoordinateImpl(5, 3), new CoordinateImpl(2, 3));
        assertTrue(statusR5P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P1).getPath());
        assertEquals(((GameStatusImpl) statusR5P1).getPathSize(), 3);
        assertEquals(statusR5P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(2, 3)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);

        GameStatus statusR5P2 = runthrough.move(new CoordinateImpl(6, -5), new CoordinateImpl(0, -5));
        assertTrue(statusR5P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P2).getPath());
        assertEquals(((GameStatusImpl) statusR5P2).getPathSize(), 6);
        assertEquals(statusR5P2.getMoveResult(), GameStatus.MoveResult.WIN);
        assertEquals(statusR5P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -5)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, -5)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 6);

        GameStatus statusR6P1 = runthrough.move(new CoordinateImpl(1, 3), new CoordinateImpl(0, 3));
        Assertions.assertFalse(statusR6P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR6P1).getPath());
        assertEquals(((GameStatusImpl) statusR6P1).getPathSize(), 0);
        assertEquals(statusR6P1.getMoveResult(), GameStatus.MoveResult.LOSE);
        assertEquals(statusR6P1.getCombatResult(), GameStatus.CombatResult.NONE);
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(1, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, 3)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 6);

    }

    @Test
    void finalTest20() {
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertEquals(runthrough.getPieceAt(new CoordinateImpl(0, 0)).getPlayer(), "Chris");
        assertEquals(runthrough.getTurnNumber(), 1);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 0);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getBounds().getyMax(), 0);
        assertEquals(runthrough.getBounds().getxMax(), 14);
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.POINT_CONFLICT));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.SCORE));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.TURN_LIMIT));
        assertEquals(runthrough.getRules().get(Rule.RuleID.SCORE), 20);
        assertEquals(runthrough.getRules().get(Rule.RuleID.TURN_LIMIT), 5);
        assertEquals(runthrough.getRules().get(Rule.RuleID.POINT_CONFLICT), 0);
        assertEquals(runthrough.getCoordinateType(), Coordinate.CoordinateType.HEX);
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertTrue(runthrough.getBoard().getPointConflict());
        assertEquals(runthrough.getPlayers()[1], "Pat");
        assertEquals(runthrough.getPieceAt( new CoordinateImpl(0, 0)).getName(), EscapePiece.PieceName.SNAIL);
        GameStatus statusR1P1 = runthrough.move(new CoordinateImpl(0, 0), new CoordinateImpl(1, 1));
        assertTrue(statusR1P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P1).getPath());
        assertEquals(((GameStatusImpl) statusR1P1).getPathSize(), 4);
        assertEquals(statusR1P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getTurnNumber(), 1);
        GameStatus statusR1P2 = runthrough.move(new CoordinateImpl(7, 3), new CoordinateImpl(10, 1));
        assertTrue(statusR1P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P2).getPath());
        assertEquals(((GameStatusImpl) statusR1P2).getPathSize(), 7);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(7, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(10, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(10, 1)), LocationType.EXIT);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P1 = runthrough.move(new CoordinateImpl(5, 2), new CoordinateImpl(1, 2));
        assertTrue(statusR2P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P1).getPath());
        assertEquals(((GameStatusImpl) statusR2P1).getPathSize(), 5);
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 2)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(1, 2)));
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P2 = runthrough.move(new CoordinateImpl(1, -4), new CoordinateImpl(1, 1));
        assertTrue(statusR2P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P2).getPath());
        assertEquals(((GameStatusImpl) statusR2P2).getPathSize(), 5);
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P1 = runthrough.move(new CoordinateImpl(6, -3), new CoordinateImpl(8, -5));
        assertTrue(statusR3P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P1).getPath());
        assertEquals(((GameStatusImpl) statusR3P1).getPathSize(), 2);
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, -5)));
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P2 = runthrough.move(new CoordinateImpl(4, -4), new CoordinateImpl(9, -3));
        assertTrue(statusR3P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P2).getPath());
        assertEquals(((GameStatusImpl) statusR3P2).getPathSize(), 6);
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(4, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, -3)));
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        assertEquals(runthrough.getTurnNumber(), 4);

        GameStatus statusR4P1 = runthrough.move(new CoordinateImpl(8, 3), new CoordinateImpl(9, 1));
        assertTrue(statusR4P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P1).getPath());
        assertEquals(((GameStatusImpl) statusR4P1).getPathSize(), 3);
        assertEquals(statusR4P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P1.getCombatResult(), GameStatus.CombatResult.DEFENDER);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, 3)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        EscapePiece piece = runthrough.getPieceAt(new CoordinateImpl(9, 1));
        assertEquals(((EscapePieceImpl)piece).getValue(), 2);
        assertEquals(runthrough.getTurnNumber(), 4);


        GameStatus statusR4P2 = runthrough.move(new CoordinateImpl(9, 1), new CoordinateImpl(9, 0));
        assertTrue(statusR4P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P2).getPath());
        assertEquals(((GameStatusImpl) statusR4P2).getPathSize(), 1);
        assertEquals(statusR4P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 0)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);

        GameStatus statusR5P1 = runthrough.move(new CoordinateImpl(5, 3), new CoordinateImpl(2, 3));
        assertTrue(statusR5P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P1).getPath());
        assertEquals(((GameStatusImpl) statusR5P1).getPathSize(), 3);
        assertEquals(statusR5P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(2, 3)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);
        runthrough.setTurnNumber(3);
        GameStatus statusR5P2 = runthrough.move(new CoordinateImpl(6, -5), new CoordinateImpl(0, -5));
        assertTrue(statusR5P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P2).getPath());
        assertEquals(((GameStatusImpl) statusR5P2).getPathSize(), 6);
        assertEquals(statusR5P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -5)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, -5)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 4);

        GameStatus statusR6P1 = runthrough.move(new CoordinateImpl(3, 3), new CoordinateImpl(2, 4));
        Assertions.assertFalse(statusR6P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR6P1).getPath());
        assertEquals(((GameStatusImpl) statusR6P1).getPathSize(), 0);
        assertEquals(statusR6P1.getMoveResult(), GameStatus.MoveResult.LOSE);
        assertEquals(statusR6P1.getCombatResult(), GameStatus.CombatResult.NONE);
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(3, 3)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(2, 4)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 4);

        GameStatus statusR6P2 = runthrough.move(new CoordinateImpl(2, 4), new CoordinateImpl(2, 5));
        Assertions.assertFalse(statusR6P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR6P2).getPath());
        assertEquals(((GameStatusImpl) statusR6P2).getPathSize(), 1);
        assertEquals(statusR6P2.getMoveResult(), GameStatus.MoveResult.LOSE);
        assertEquals(statusR6P2.getCombatResult(), GameStatus.CombatResult.NONE);
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(3, 3)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(2, 4)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 4);



    }

    @Test
    void finalTest21() {
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertEquals(runthrough.getPieceAt(new CoordinateImpl(0, 0)).getPlayer(), "Chris");
        assertEquals(runthrough.getTurnNumber(), 1);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 0);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getBounds().getyMax(), 0);
        assertEquals(runthrough.getBounds().getxMax(), 14);
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.POINT_CONFLICT));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.SCORE));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.TURN_LIMIT));
        assertEquals(runthrough.getRules().get(Rule.RuleID.SCORE), 20);
        assertEquals(runthrough.getRules().get(Rule.RuleID.TURN_LIMIT), 5);
        assertEquals(runthrough.getRules().get(Rule.RuleID.POINT_CONFLICT), 0);
        assertEquals(runthrough.getCoordinateType(), Coordinate.CoordinateType.HEX);
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertTrue(runthrough.getBoard().getPointConflict());
        assertEquals(runthrough.getPlayers()[1], "Pat");
        assertEquals(runthrough.getPieceAt( new CoordinateImpl(0, 0)).getName(), EscapePiece.PieceName.SNAIL);
        GameStatus statusR1P1 = runthrough.move(new CoordinateImpl(0, 0), new CoordinateImpl(1, 1));
        assertTrue(statusR1P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P1).getPath());
        assertEquals(((GameStatusImpl) statusR1P1).getPathSize(), 4);
        assertEquals(statusR1P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getTurnNumber(), 1);
        GameStatus statusR1P2 = runthrough.move(new CoordinateImpl(7, 3), new CoordinateImpl(10, 1));
        assertTrue(statusR1P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P2).getPath());
        assertEquals(((GameStatusImpl) statusR1P2).getPathSize(), 7);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(7, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(10, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(10, 1)), LocationType.EXIT);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P1 = runthrough.move(new CoordinateImpl(5, 2), new CoordinateImpl(1, 2));
        assertTrue(statusR2P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P1).getPath());
        assertEquals(((GameStatusImpl) statusR2P1).getPathSize(), 5);
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 2)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(1, 2)));
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P2 = runthrough.move(new CoordinateImpl(1, -4), new CoordinateImpl(1, 1));
        assertTrue(statusR2P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P2).getPath());
        assertEquals(((GameStatusImpl) statusR2P2).getPathSize(), 5);
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P1 = runthrough.move(new CoordinateImpl(6, -3), new CoordinateImpl(8, -5));
        assertTrue(statusR3P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P1).getPath());
        assertEquals(((GameStatusImpl) statusR3P1).getPathSize(), 2);
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, -5)));
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P2 = runthrough.move(new CoordinateImpl(4, -4), new CoordinateImpl(9, -3));
        assertTrue(statusR3P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P2).getPath());
        assertEquals(((GameStatusImpl) statusR3P2).getPathSize(), 6);
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(4, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, -3)));
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        assertEquals(runthrough.getTurnNumber(), 4);

        GameStatus statusR4P1 = runthrough.move(new CoordinateImpl(8, 3), new CoordinateImpl(9, 1));
        assertTrue(statusR4P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P1).getPath());
        assertEquals(((GameStatusImpl) statusR4P1).getPathSize(), 3);
        assertEquals(statusR4P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P1.getCombatResult(), GameStatus.CombatResult.DEFENDER);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, 3)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        EscapePiece piece = runthrough.getPieceAt(new CoordinateImpl(9, 1));
        assertEquals(((EscapePieceImpl)piece).getValue(), 2);
        assertEquals(runthrough.getTurnNumber(), 4);


        GameStatus statusR4P2 = runthrough.move(new CoordinateImpl(9, 1), new CoordinateImpl(9, 0));
        assertTrue(statusR4P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P2).getPath());
        assertEquals(((GameStatusImpl) statusR4P2).getPathSize(), 1);
        assertEquals(statusR4P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 0)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);

        GameStatus statusR5P1 = runthrough.move(new CoordinateImpl(5, 3), new CoordinateImpl(2, 3));
        assertTrue(statusR5P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P1).getPath());
        assertEquals(((GameStatusImpl) statusR5P1).getPathSize(), 3);
        assertEquals(statusR5P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(2, 3)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);
        runthrough.setTurnNumber(3);
        GameStatus statusR5P2 = runthrough.move(new CoordinateImpl(6, -5), new CoordinateImpl(0, -5));
        assertTrue(statusR5P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P2).getPath());
        assertEquals(((GameStatusImpl) statusR5P2).getPathSize(), 6);
        assertEquals(statusR5P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -5)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, -5)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 4);

        /*
        GameStatus statusR6P1 = runthrough.move(new CoordinateImpl(3, 3), new CoordinateImpl(3, 4));
        assertTrue(statusR6P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR6P1).getPath());
        assertEquals(((GameStatusImpl) statusR6P1).getPathSize(), 1);
        assertEquals(statusR6P1.getMoveResult(), GameStatus.MoveResult.WIN);
        assertEquals(statusR6P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(3, 3)));
        assertNotNull(runthrough.getPieceAt(new CoordinateImpl(3, 4)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 4);



         */

    }


    @Test
    void finalTest22(){

        runthrough2.removePiece(new CoordinateImpl(2, 3));
        System.out.println(runthrough2.getPieceAt(new CoordinateImpl(2, 2)).getPlayer());
        GameStatus statusR5P2 = runthrough2.move(new CoordinateImpl(6, -5), new CoordinateImpl(0, -5));
        assertTrue(statusR5P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P2).getPath());
        assertEquals(((GameStatusImpl) statusR5P2).getPathSize(), 6);
        assertEquals(statusR5P2.getMoveResult(), GameStatus.MoveResult.WIN);
        assertEquals(statusR5P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough2.getPieceAt(new CoordinateImpl(6, -5)));
        assertNull(runthrough2.getPieceAt(new CoordinateImpl(0, -5)));
        assertEquals(runthrough2.getCurrentPlayer(), "Pat");
        assertEquals(runthrough2.getPlayer1Score(), 1);
        assertEquals(runthrough2.getPlayer2Score(), 0);
        assertEquals(runthrough2.getTurnNumber(), 1);
    }

    @Test
    void finalTest23(){
        runthrough2.removePiece(new CoordinateImpl(2, -3));
        System.out.println(runthrough2.getPieceAt(new CoordinateImpl(3, 2)).getPlayer());
        GameStatus statusR5P2 = runthrough2.move(new CoordinateImpl(6, -5), new CoordinateImpl(0, -5));
        assertTrue(statusR5P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P2).getPath());
        assertEquals(((GameStatusImpl) statusR5P2).getPathSize(), 6);
        assertEquals(statusR5P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough2.getPieceAt(new CoordinateImpl(6, -5)));
        assertNull(runthrough2.getPieceAt(new CoordinateImpl(0, -5)));
        assertEquals(runthrough2.getCurrentPlayer(), "Pat");
        assertEquals(runthrough2.getPlayer1Score(), 1);
        assertEquals(runthrough2.getPlayer2Score(), 0);
        assertEquals(runthrough2.getTurnNumber(), 1);
    }

    @Test
    void finalTest221() {
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertEquals(runthrough.getPieceAt(new CoordinateImpl(0, 0)).getPlayer(), "Chris");
        assertEquals(runthrough.getTurnNumber(), 1);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 0);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getBounds().getyMax(), 0);
        assertEquals(runthrough.getBounds().getxMax(), 14);
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.POINT_CONFLICT));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.SCORE));
        assertTrue(runthrough.getRules().containsKey(Rule.RuleID.TURN_LIMIT));
        assertEquals(runthrough.getRules().get(Rule.RuleID.SCORE), 20);
        assertEquals(runthrough.getRules().get(Rule.RuleID.TURN_LIMIT), 5);
        assertEquals(runthrough.getRules().get(Rule.RuleID.POINT_CONFLICT), 0);
        assertEquals(runthrough.getCoordinateType(), Coordinate.CoordinateType.HEX);
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertTrue(runthrough.getBoard().getPointConflict());
        assertEquals(runthrough.getPlayers()[1], "Pat");
        assertEquals(runthrough.getPieceAt(new CoordinateImpl(0, 0)).getName(), EscapePiece.PieceName.SNAIL);
        GameStatus statusR1P1 = runthrough.move(new CoordinateImpl(0, 0), new CoordinateImpl(1, 1));
        assertTrue(statusR1P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P1).getPath());
        assertEquals(((GameStatusImpl) statusR1P1).getPathSize(), 4);
        assertEquals(statusR1P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, 0)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(1, 1)), LocationType.EXIT);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 0);
        assertEquals(runthrough.getTurnNumber(), 1);
        GameStatus statusR1P2 = runthrough.move(new CoordinateImpl(7, 3), new CoordinateImpl(10, 1));
        assertTrue(statusR1P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR1P2).getPath());
        assertEquals(((GameStatusImpl) statusR1P2).getPathSize(), 7);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(7, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(10, 1)));
        assertEquals(runthrough.getBoard().getBlockExitLocations().get(new CoordinateImpl(10, 1)), LocationType.EXIT);
        assertEquals(statusR1P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR1P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P1 = runthrough.move(new CoordinateImpl(5, 2), new CoordinateImpl(1, 2));
        assertTrue(statusR2P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P1).getPath());
        assertEquals(((GameStatusImpl) statusR2P1).getPathSize(), 5);
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 2)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(1, 2)));
        assertEquals(statusR2P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 1);
        assertEquals(runthrough.getTurnNumber(), 2);

        GameStatus statusR2P2 = runthrough.move(new CoordinateImpl(1, -4), new CoordinateImpl(1, 1));
        assertTrue(statusR2P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR2P2).getPath());
        assertEquals(((GameStatusImpl) statusR2P2).getPathSize(), 5);
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(1, 1)));
        assertEquals(statusR2P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR2P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 5);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P1 = runthrough.move(new CoordinateImpl(6, -3), new CoordinateImpl(8, -5));
        assertTrue(statusR3P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P1).getPath());
        assertEquals(((GameStatusImpl) statusR3P1).getPathSize(), 2);
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, -5)));
        assertEquals(statusR3P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 4);
        assertEquals(runthrough.getTurnNumber(), 3);

        GameStatus statusR3P2 = runthrough.move(new CoordinateImpl(4, -4), new CoordinateImpl(9, -3));
        assertTrue(statusR3P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR3P2).getPath());
        assertEquals(((GameStatusImpl) statusR3P2).getPathSize(), 6);
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(4, -4)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, -3)));
        assertEquals(statusR3P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR3P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        assertEquals(runthrough.getTurnNumber(), 4);

        GameStatus statusR4P1 = runthrough.move(new CoordinateImpl(8, 3), new CoordinateImpl(9, 1));
        assertTrue(statusR4P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P1).getPath());
        assertEquals(((GameStatusImpl) statusR4P1).getPathSize(), 3);
        assertEquals(statusR4P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P1.getCombatResult(), GameStatus.CombatResult.DEFENDER);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(8, 3)));
        Assertions.assertNotNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 7);
        EscapePiece piece = runthrough.getPieceAt(new CoordinateImpl(9, 1));
        assertEquals(((EscapePieceImpl) piece).getValue(), 2);
        assertEquals(runthrough.getTurnNumber(), 4);


        GameStatus statusR4P2 = runthrough.move(new CoordinateImpl(9, 1), new CoordinateImpl(9, 0));
        assertTrue(statusR4P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR4P2).getPath());
        assertEquals(((GameStatusImpl) statusR4P2).getPathSize(), 1);
        assertEquals(statusR4P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR4P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 1)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(9, 0)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 7);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);

        GameStatus statusR5P1 = runthrough.move(new CoordinateImpl(5, 3), new CoordinateImpl(2, 3));
        assertTrue(statusR5P1.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P1).getPath());
        assertEquals(((GameStatusImpl) statusR5P1).getPathSize(), 3);
        assertEquals(statusR5P1.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P1.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(5, 3)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(2, 3)));
        assertEquals(runthrough.getCurrentPlayer(), "Pat");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 9);
        assertEquals(runthrough.getTurnNumber(), 5);
        runthrough.setTurnNumber(3);
        GameStatus statusR5P2 = runthrough.move(new CoordinateImpl(6, -5), new CoordinateImpl(0, -5));
        assertTrue(statusR5P2.isValidMove());
        System.out.println(((GameStatusImpl) statusR5P2).getPath());
        assertEquals(((GameStatusImpl) statusR5P2).getPathSize(), 6);
        assertEquals(statusR5P2.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(statusR5P2.getCombatResult(), GameStatus.CombatResult.NONE);
        assertNull(runthrough.getPieceAt(new CoordinateImpl(6, -5)));
        assertNull(runthrough.getPieceAt(new CoordinateImpl(0, -5)));
        assertEquals(runthrough.getCurrentPlayer(), "Chris");
        assertEquals(runthrough.getPlayer1Score(), 9);
        assertEquals(runthrough.getPlayer2Score(), 10);
        assertEquals(runthrough.getTurnNumber(), 4);

    }

    @Test
    void finalTest2(){
        int row = 0;
        int column = 0;
        Coordinate c = runthroughManager.makeCoordinate(row, column);
        assertEquals(EscapePiece.PieceName.SNAIL, runthroughManager.getPieceAt(c).getName());
    }

    @Test
    void otherPlayerCantMove2(){
        Coordinate from = new CoordinateImpl(10, 10);
        Coordinate to = new CoordinateImpl(11, 10);
        GameStatusImpl status = (GameStatusImpl) checkmate2.move(from, to);
        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        checkmate2.removePiece(new CoordinateImpl(11, 10));
        Coordinate from2 = new CoordinateImpl(8, 8);
        Coordinate to2 = new CoordinateImpl(9, 8);
        assertNull(checkmate2.getPieceAt(new CoordinateImpl(2, 1)));
        GameStatusImpl status2 = (GameStatusImpl) checkmate2.move(from2, to2);
        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    @Test
    void otherPlayerCantMove3(){
        Coordinate from = new CoordinateImpl(10, 10);
        Coordinate to = new CoordinateImpl(10, 11);
        GameStatusImpl status = (GameStatusImpl) checkmate2.move(from, to);
        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        Coordinate from2 = new CoordinateImpl(8, 8);
        Coordinate to2 = new CoordinateImpl(9, 8);
        assertNull(checkmate2.getPieceAt(new CoordinateImpl(2, 1)));
        GameStatusImpl status2 = (GameStatusImpl) checkmate2.move(from2, to2);
        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    @Test
    void otherPlayerCantMove4(){
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(1, 1);
        GameStatusImpl status = (GameStatusImpl) checkmate3.move(from, to);
        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.NONE);
        Coordinate from2 = new CoordinateImpl(3, 3);
        Coordinate to2 = new CoordinateImpl(2, 2);
        GameStatusImpl status2 = (GameStatusImpl) checkmate3.move(from2, to2);
        assertTrue(status2.isValidMove());
        assertEquals(status2.getMoveResult(), GameStatus.MoveResult.WIN);
    }


    @Test
    void otherPlayerCantMove(){
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5, 6);
        GameStatusImpl status = (GameStatusImpl) checkmate.move(from, to);
        assertTrue(status.isValidMove());
        assertEquals(status.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    @Test
    void testRuleToString() {
        System.out.println(turnLimit.getRules().get(Rule.RuleID.TURN_LIMIT).toString());
        assertEquals(turnLimit.getRules().get(Rule.RuleID.TURN_LIMIT), 2);
    }
    @Test
    void movingToSameLocation() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3, 3);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void notYourTurn() {
        Coordinate from = new CoordinateImpl(30, 30);
        Coordinate to = new CoordinateImpl(31, 30);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    @Test
    void testGetPieceName() {
        assertEquals(squareFinite.getPieceAt(new CoordinateImpl(3, 3)).getName(), EscapePiece.PieceName.SNAIL);
    }


    @Test
    void testGetPlayerName() {
        assertEquals(squareFinite.getPieceAt(new CoordinateImpl(3, 3)).getPlayer(), "Chris");
    }




    @Test
    void pieceDefaultValue() {
        EscapePieceImpl currentPiece = (EscapePieceImpl) squareFinite.getPieceAt(new CoordinateImpl(2, 2));
        assertEquals(currentPiece.getValue(), 1);
    }


    @Test
    void pieceCorrectInputValue() {
        EscapePieceImpl currentPiece = (EscapePieceImpl) squareFinite.getPieceAt(new CoordinateImpl(3, 3));
        assertEquals(currentPiece.getValue(), 5);
    }


    @Test
    void hexLinearNorth() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate north = new CoordinateImpl(2, 3);
        assertTrue(hexFinite.move(from, north).isValidMove());
    }
    @Test
    void hexLinearSouth() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate south = new CoordinateImpl(2,1);
        assertTrue(hexFinite.move(from, south).isValidMove());
    }
    @Test
    void hexLinearEast() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate east = new CoordinateImpl(3,2);
        assertTrue(hexFinite.move(from, east).isValidMove());
    }
    @Test
    void hexLinearWest() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate west = new CoordinateImpl(1,2);
        assertTrue(hexFinite.move(from, west).isValidMove());
    }
    @Test
    void hexLinearNorthwest() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate northWest = new CoordinateImpl(1,3);
        assertTrue(hexFinite.move(from, northWest).isValidMove());
    }
    @Test
    void hexLinearSoutheast() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate southEast = new CoordinateImpl(3,1);
        assertTrue(hexFinite.move(from, southEast).isValidMove());
    }


    @Test
    void hexLinearCantMoveSouthwest() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate southWest = new CoordinateImpl(1,1);
        //Can't move down to (1, 1) without changing direction -> southwest is not a direct linear move
        assertFalse(hexFinite.move(from, southWest).isValidMove());
    }


    @Test
    void hexLinearCantMoveNortheast() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate southWest = new CoordinateImpl(3,3);
        //Can't move up to (3, 3) without changing direction -> northeast is not a direct linear move
        assertFalse(hexFinite.move(from, southWest).isValidMove());
    }

    @Test
    void hexLinearFiniteBoardCantMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearFiniteBoardCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-1000000000,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearFiniteBoardCantMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearFiniteBoardCantMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-1000000000);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteXCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteXCanMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-2,2);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteXCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteXCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-20000);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteYCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteYCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-2);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteYCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteYCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-200000,2);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexLinearInfiniteCanMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-2,2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }





    @Test
    void hexOmniNorth() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate north = new CoordinateImpl(3, 4);
        assertTrue(hexFinite.move(from, north).isValidMove());
    }
    @Test
    void hexOmniSouth() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate south = new CoordinateImpl(3,2);
        assertTrue(hexFinite.move(from, south).isValidMove());
    }
    @Test
    void hexOmniEast() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate east = new CoordinateImpl(4,3);
        assertTrue(hexFinite.move(from, east).isValidMove());
    }
    @Test
    void hexOmniWest() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate west = new CoordinateImpl(2,3);
        assertTrue(hexFinite.move(from, west).isValidMove());
    }
    @Test
    void hexOmniNorthwest() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate northWest = new CoordinateImpl(2,4);
        assertTrue(hexFinite.move(from, northWest).isValidMove());
    }
    @Test
    void hexOmniSoutheast() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate southEast = new CoordinateImpl(4,2);
        assertTrue(hexFinite.move(from, southEast).isValidMove());
    }


    @Test
    void hexOmniChangeDirection() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,1);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniFiniteBoardCantMoveToRow0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(0,3);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniFiniteBoardCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(-1000000000,3);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniFiniteBoardCantMoveToColumn0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,0);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniFiniteBoardCantMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,-1000000000);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    @Test
    void hexOmniInfiniteXCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(0,3);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteXCanMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(-3,3);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteXCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,0);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteXCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,-30000);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteYCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteYCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,-3);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteYCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(0,3);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteYCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(-300000,3);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,0);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,-3);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(0,3);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexOmniInfiniteCanMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(-3,3);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }





    @Test
    void squareUnblockMoveThroughBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,8);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareUnblockCanMovePastMultipleBlocks() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 5) and (6, 4)
        Coordinate to = new CoordinateImpl(6,3);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareUnblockCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,7);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareUnblockCanMoveThroughOtherPlayer() {
        Coordinate from = new CoordinateImpl(6, 6);
        //player at (5, 6)
        Coordinate to = new CoordinateImpl(4,6);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertFalse(gsi.getPath().contains(new CoordinateImpl(5, 6)));
    }


    @Test
    void hexUnblockCantMoveThroughExit() {
        Coordinate from = new CoordinateImpl(6, 6);
        //exit at (7, 6)
        Coordinate to = new CoordinateImpl(8,6);
        GameStatusImpl gsi = (GameStatusImpl) hexFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertFalse(gsi.getPath().contains(new CoordinateImpl(7, 6)));
    }


    @Test
    void hexUnblockMoveThroughBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,8);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexUnblockCanMovePastMultipleBlocks() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 5) and (6, 4)
        Coordinate to = new CoordinateImpl(6,3);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexUnblockCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,7);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexUnblockCanMoveThroughOtherPlayer() {
        Coordinate from = new CoordinateImpl(6, 6);
        //player at (5, 6)
        Coordinate to = new CoordinateImpl(4,6);
        GameStatusImpl gsi = (GameStatusImpl) hexFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertFalse(gsi.getPath().contains(new CoordinateImpl(5, 6)));
    }


    @Test
    void squareFlyCanMoveOverBlock() {
        Coordinate from = new CoordinateImpl(10, 10);
        //block at (10, 9)
        Coordinate to = new CoordinateImpl(10,8);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(10, 9)));
    }
    @Test
    void squareFlyCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(10, 10);
        //block at (10, 9)
        Coordinate to = new CoordinateImpl(10,9);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareFlyCanMoveOverPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,12);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(10, 11)));
    }
    @Test
    void squareFlyCantLandOnPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,11);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void squareFlyCanMoveOverExit() {
        Coordinate from = new CoordinateImpl(10, 10);
        //exit at (9, 10)
        Coordinate to = new CoordinateImpl(8,10);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(9, 10)));
    }


    @Test
    void squareFiniteCantFlyOffBoardRow() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void squareFiniteCantFlyOffBoardColumn() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareFiniteCantFlyOffBoardRowNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-10,2);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void squareFiniteCantFlyOffBoardColumnNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    @Test
    void squareInfiniteXFlyColumn0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertTrue(squareInfX.move(from, to).isValidMove());
    }
    @Test
    void squareInfiniteXFlyRow0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteXFlyRowNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-1,2);
        assertTrue(squareInfX.move(from, to).isValidMove());
    }
    @Test
    void squareInfiniteXFlyColumnNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteYFlyColumn0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertTrue(squareInfY.move(from, to).isValidMove());
    }
    @Test
    void squareInfiniteYFlyRow0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteYFlyRowNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-1,2);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }
    @Test
    void squareInfiniteYFlyColumnNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertTrue(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteFlyColumn0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertTrue(squareInfinite.move(from, to).isValidMove());
    }
    @Test
    void squareInfiniteFlyRow0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteFlyRowNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-1,2);
        assertTrue(squareInfinite.move(from, to).isValidMove());
    }
    @Test
    void squareInfiniteFlyColumnNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertTrue(squareInfinite.move(from, to).isValidMove());
    }



    @Test
    void hexFlyCanMoveOverBlock() {
        Coordinate from = new CoordinateImpl(10, 10);
        //block at (10, 9)
        Coordinate to = new CoordinateImpl(10,8);
        GameStatusImpl gsi = (GameStatusImpl) hexFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(10, 9)));
    }
    @Test
    void hexFlyCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(10, 10);
        //block at (10, 9)
        Coordinate to = new CoordinateImpl(10,9);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFlyCanMoveOverPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,12);
        GameStatusImpl gsi = (GameStatusImpl) hexFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(10, 11)));
    }
    @Test
    void hexFlyCantLandOnPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,11);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }
    @Test
    void hexFlyCanMoveOverExit() {
        Coordinate from = new CoordinateImpl(10, 10);
        //exit at (9, 10)
        Coordinate to = new CoordinateImpl(8,10);
        GameStatusImpl gsi = (GameStatusImpl) hexFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(9, 10)));
    }


    @Test
    void hexFiniteCantFlyOffBoardRow() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }
    @Test
    void hexFiniteCantFlyOffBoardColumn() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteCantFlyOffBoardRowNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-10,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }
    @Test
    void hexFiniteCantFlyOffBoardColumnNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    @Test
    void hexInfiniteXFlyRow0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }
    @Test
    void hexInfiniteXFlyColumn0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteXFlyNegativeRow() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-10,2);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }
    @Test
    void hexInfiniteXCantFlyOffBoardColumnNegative() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteYFlyRow0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }
    @Test
    void hexInfiniteYFlyColumn0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteYFlyNegativeRow() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-10,2);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }
    @Test
    void hexInfiniteYFlyNegativeColumn() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteFlyRow0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,0);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }
    @Test
    void hexInfiniteFlyColumn0() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteFlyNegativeRow() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(-10,2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }
    @Test
    void hexInfiniteFlyNegativeColumn() {
        Coordinate from = new CoordinateImpl(10, 2);
        Coordinate to = new CoordinateImpl(10,-10);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }





    @Test
    void squareOmniJumpMoveNormallyNorth() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate north = new CoordinateImpl(14,4);
        assertTrue(squareFinite.move(from, north).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallySouth() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate south = new CoordinateImpl(14,2);
        assertTrue(squareFinite.move(from, south).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallyEast() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate east = new CoordinateImpl(15,3);
        assertTrue(squareFinite.move(from, east).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallyWest() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate west = new CoordinateImpl(13,3);
        assertTrue(squareFinite.move(from, west).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallyNorthWest() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate northWest = new CoordinateImpl(13,4);
        assertTrue(squareFinite.move(from, northWest).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallyNorthEast() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate northWest = new CoordinateImpl(15,4);
        assertTrue(squareFinite.move(from, northWest).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallySouthWest() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate southWest = new CoordinateImpl(13,2);
        assertTrue(squareFinite.move(from, southWest).isValidMove());
    }
    @Test
    void squareOmniJumpMoveNormallySouthEast() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate southWest = new CoordinateImpl(15,2);
        assertTrue(squareFinite.move(from, southWest).isValidMove());
    }


    @Test
    void hexOmniJumpMoveNormallyNorth() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate north = new CoordinateImpl(14,4);
        assertTrue(hexFinite.move(from, north).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallySouth() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate south = new CoordinateImpl(14,2);
        assertTrue(hexFinite.move(from, south).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallyEast() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate east = new CoordinateImpl(15,3);
        assertTrue(hexFinite.move(from, east).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallyWest() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate west = new CoordinateImpl(13,3);
        assertTrue(hexFinite.move(from, west).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallyNorthWest() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate northWest = new CoordinateImpl(13,4);
        assertTrue(hexFinite.move(from, northWest).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallyNorthEast() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate northWest = new CoordinateImpl(15,4);
        assertTrue(hexFinite.move(from, northWest).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallySouthWest() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate southWest = new CoordinateImpl(13,2);
        assertTrue(hexFinite.move(from, southWest).isValidMove());
    }
    @Test
    void hexOmniJumpMoveNormallySouthEast() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate southWest = new CoordinateImpl(15,2);
        assertTrue(hexFinite.move(from, southWest).isValidMove());
    }
    @Test
    void squareJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void squareJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    @Test
    void squareInfXJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfXJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(squareInfX.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfYJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(squareInfY.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }


    @Test
    void squareInfiniteJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(squareInfinite.move(from, to).isValidMove());
    }





    @Test
    void hexFiniteJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    @Test
    void hexInfXJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }


    @Test
    void hexInfXJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }




    @Test
    void hexInfYJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }


    @Test
    void hexInfYJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }








    @Test
    void hexInfiniteJumpOverBlockNorth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockSouth() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,1);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockEast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,3);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockWest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,3);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockNortheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,5);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockNorthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,5);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockSoutheast() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(20,1);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }


    @Test
    void hexInfiniteJumpOverBlockSouthwest() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(16,1);
        assertFalse(hexInfinite.move(from, to).isValidMove());
    }



    @Test
    void squareJumpOverPiece() {
        Coordinate from = new CoordinateImpl(20, 2);
        Coordinate to = new CoordinateImpl(20,4);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void hexJumpOverPiece() {
        Coordinate from = new CoordinateImpl(20, 2);
        Coordinate to = new CoordinateImpl(20,4);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    //this is already tested when you validate the "to" locations, so this really isn't necessary
    @Test
    void squareFiniteJumpOverPieceLandOffBoard() {
        Coordinate from = new CoordinateImpl(22, 2);
        Coordinate to = new CoordinateImpl(22,0);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void hexFiniteJumpOverPieceLandOffBoard() {
        Coordinate from = new CoordinateImpl(22, 2);
        Coordinate to = new CoordinateImpl(22,0);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void squareFiniteJumpOverMultiplePiecesInARow() {
        Coordinate from = new CoordinateImpl(22, 2);
        Coordinate to = new CoordinateImpl(25,2);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void hexFiniteJumpOverMultiplePiecesInARow() {
        Coordinate from = new CoordinateImpl(22, 2);
        Coordinate to = new CoordinateImpl(25,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpCantChangeDirection() {
        Coordinate from = new CoordinateImpl(23, 5);
        Coordinate to = new CoordinateImpl(25,4);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }




    //don't think this is necessary either
    @Test
    void squareFiniteJumpOverMultiplePiecesWithSpacesInbetween() {
        Coordinate from = new CoordinateImpl(20, 2);
        Coordinate to = new CoordinateImpl(20,6);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void hexFiniteJumpOverMultiplePiecesWithSpacesInbetween() {
        Coordinate from = new CoordinateImpl(20, 2);
        Coordinate to = new CoordinateImpl(20,6);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverPieceLandOnBlock() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(31,3);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }


    @Test
    void hexJumpOverPieceLandOnBlock() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(31,3);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverExitLandOnClear() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(28,5);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void hexJumpOverExitLandOnClear() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(28,5);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void squareJumpOverExitLandOnExit() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(28,1);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }
    @Test
    void hexJumpOverExitLandOnExit() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(28,1);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }


    @Test
    void correctTurnLimit() {
        assertEquals(turnLimit.getRules().get(Rule.RuleID.TURN_LIMIT), 2);
    }


    @Test
    void correctSetTurnNumber() {
        assertEquals(turnLimit.getTurnNumber(), 1);
        turnLimit.setTurnNumber(3);
        assertEquals(turnLimit.getTurnNumber(), 3);
    }


    @Test
    void cantMovePastTurnLimit() {
        turnLimit.setTurnNumber(3);
        assertEquals(turnLimit.getTurnNumber(), 3);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        assertFalse(turnLimit.move(from, to).isValidMove());
    }


    @Test
    void removeAllPiecesBeforeTurnLimit() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameStatusImpl gsi = (GameStatusImpl) turnLimit.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
    }


    @Test
    void cantMoveAfterPlayerRemovedAllPieces() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameStatusImpl gsi = (GameStatusImpl) turnLimit.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
        Coordinate from2 = new CoordinateImpl(3, 3);
        Coordinate to2 = new CoordinateImpl(2,1);
        GameStatusImpl gsi2 = (GameStatusImpl) turnLimit.move(from2, to2);
        assertFalse(gsi2.isValidMove());
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.LOSE);
    }


    @Test
    void turnLimitDraw() {
        turnLimit.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        GameStatusImpl gsi = (GameStatusImpl) turnLimit.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.NONE);
        assertEquals(turnLimit.getTurnNumber(), 2);
        Coordinate from2 = new CoordinateImpl(3, 3);
        Coordinate to2 = new CoordinateImpl(3,4);
        GameStatusImpl gsi2 = (GameStatusImpl) turnLimit.move(from2, to2);
        assertTrue(gsi2.isValidMove());
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.DRAW);
    }


    @Test
    void player2ScoreGreaterThanPlayer1AndTurnLimitUp() {
        turnLimitMorePieces.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        turnLimitMorePieces.move(from, to);
        Coordinate from2 = new CoordinateImpl(1, 1);
        Coordinate to2 = new CoordinateImpl(2,1);
        GameStatusImpl gsi2 = (GameStatusImpl) turnLimitMorePieces.move(from2, to2);
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.WIN);
    }


    @Test
    void player1ScoreGreaterThanPlayer2AndTurnLimitUp() {
        turnLimitMorePieces.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        turnLimitMorePieces.move(from, to);
        Coordinate from2 = new CoordinateImpl(1, 1);
        Coordinate to2 = new CoordinateImpl(1,2);
        GameStatusImpl gsi2 = (GameStatusImpl) turnLimitMorePieces.move(from2, to2);
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.LOSE);
    }




    @Test
    void otherPlayer2CantMove() {
        cantMove.removePiece(new CoordinateImpl(8, 8));
        Coordinate from = new CoordinateImpl(1, 1);
        Coordinate to = new CoordinateImpl(1,2);
        GameStatusImpl gsi = (GameStatusImpl) cantMove.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
    }


    @Test
    void otherPlayer1CantMove() {
        //make player 1 move so that it can be player 2 turn
        Coordinate p1From = new CoordinateImpl(1, 1);
        Coordinate p1To = new CoordinateImpl(1,2);
        GameStatus status = cantMove.move(p1From, p1To);
        cantMove.removePiece(new CoordinateImpl(1, 2));
      //  assertEquals(status.getMoveResult(), GameStatus.MoveResult.LOSE);


        Coordinate p2From = new CoordinateImpl(8, 8);
        Coordinate p2To = new CoordinateImpl(8,9);
        GameStatusImpl gsiP2 = (GameStatusImpl) cantMove.move(p2From, p2To);
        assertTrue(gsiP2.isValidMove());
        assertEquals(gsiP2.getMoveResult(), GameStatus.MoveResult.WIN);
    }




    @Test
    void pointConflictMoveToOtherPieceLocation() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        assertTrue(conflict.move(from, to).isValidMove());
    }


    @Test
    void pointConflictRemovePieceAtStartingLocation() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertTrue(gsi.isValidMove());
        assertNull(conflict.getPieceAt(from));
    }


    //point conflict same piece
    @Test
    void pointConflictRemovesPieceWithLowerValue() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        conflict.move(from, to);
        assertEquals(conflict.getPieceAt(to).getPlayer(), "Chris");
    }


    @Test
    void pointConflictSetCorrectWinningPieceValue() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        conflict.move(from, to);
        EscapePieceImpl epi = (EscapePieceImpl) conflict.getPieceAt(to);
        assertEquals(epi.getValue(), 1);
    }
    @Test
    void pointConflictWithPieceOfEqualValue() {
        Coordinate from = new CoordinateImpl(4, 4);
        Coordinate to = new CoordinateImpl(4,5);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(from));
        assertNull(conflict.getPieceAt(to));
    }


    @Test
    void pointConflictWithMovingPieceLosing() {
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5,6);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(from));
        assertEquals(conflict.getPieceAt(to).getPlayer(), "Pat");
    }


    @Test
    void pointConflictAttackerWins() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertEquals(gsi.getCombatResult(), GameStatus.CombatResult.ATTACKER);
    }
    @Test
    void pointConflictDraw() {
        Coordinate from = new CoordinateImpl(4, 4);
        Coordinate to = new CoordinateImpl(4,5);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertEquals(gsi.getCombatResult(), GameStatus.CombatResult.DRAW);
    }


    @Test
    void pointConflictDefenderWins() {
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5,6);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertEquals(gsi.getCombatResult(), GameStatus.CombatResult.DEFENDER);
    }


    @Test
    void testValidMoveToExitLocation() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        assertTrue(conflict.move(from, to).isValidMove());
    }


    @Test
    void pieceRemovalWhenMoveToExitLocationAtFromLocation() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(from));
    }


    @Test
    void noPieceAtExitLocationWhenMovingThere() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(to));
    }


    @Test
    void movingToExitAddsPlayerScore() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        conflict.move(from, to);
        assertEquals(conflict.getPlayer1Score(), 3);
        Coordinate from2 = new CoordinateImpl(5, 6);
        Coordinate to2 = new CoordinateImpl(6,7);
        conflict.move(from2, to2);
        assertEquals(conflict.getPlayer2Score(), 5);
        Coordinate from3 = new CoordinateImpl(8, 8);
        Coordinate to3 = new CoordinateImpl(8,9);
        conflict.move(from3, to3);
        assertEquals(conflict.getPlayer1Score(), 9);
    }


    @Test
    void playerWinsWhenScoreSatisfied() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
    }


    @Test
    void cantMoveWhenScoreConditionMet() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        assertTrue(conflict.move(from, to).isValidMove());
        Coordinate from2 = new CoordinateImpl(5, 6);
        Coordinate to2 = new CoordinateImpl(5,7);
        assertFalse(conflict.move(from2, to2).isValidMove());
    }


    //observer tests
    @Test
    void testIsMoreInformation() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3, 3);
        conflict.addObserver( new GameObserverImpl());
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertFalse(gsi.isValidMove());
        assertTrue(gsi.isMoreInformation());
    }


    @Test
    public void testAddObserver() {
        GameObserverImpl observer = new GameObserverImpl();
        assertNotNull(squareFinite.addObserver(observer));
    }


    @Test
    public void testRemoveObserver() {
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        assertNotNull(squareFinite.removeObserver(observer));
    }


    @Test
    public void testAddObserverNull() {
        GameObserverImpl observer = null;
        assertNull(squareFinite.addObserver(observer));
    }


    @Test
    public void testRemoveObserverNull() {
        GameObserverImpl observer = new GameObserverImpl();
        assertNull(squareFinite.removeObserver(observer));
    }


    @Test
    public void notifyObserver() {
        GameObserverImpl observer = new GameObserverImpl();
        observer.notify("Test");
    }


    @Test
    void observerInfoSentWhenMoveToSameLocation() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3, 3);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because they tried to move to the same location");
    }


    @Test
    void observerInfoSentWhenInfiniteYCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-200000,2);
        GameObserverImpl observer = new GameObserverImpl();
        hexInfY.addObserver(observer);
        hexInfY.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because they tried to move to a location off the board");


    }


    @Test
    void observerSentInfoWhenCantMovePastTurnLimit() {
        turnLimit.setTurnNumber(3);
        assertEquals(turnLimit.getTurnNumber(), 3);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        GameObserverImpl observer = new GameObserverImpl();
        turnLimit.addObserver(observer);
        turnLimit.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move from Chris, the game is already over from the turn limit being reached.");
    }


    @Test
    void observerSentInfoWhenRemoveAllPiecesBeforeTurnLimit() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameObserverImpl observer = new GameObserverImpl();
        turnLimit.addObserver(observer);
        turnLimit.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris has no pieces left. They win!");
    }


    @Test
    void observerSentInfoWhenTurnLimitDraw() {
        turnLimit.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        GameObserverImpl observer = new GameObserverImpl();
        turnLimit.addObserver(observer);
        turnLimit.move(from, to);
        Coordinate from2 = new CoordinateImpl(3, 3);
        Coordinate to2 = new CoordinateImpl(3,4);
        turnLimit.move(from2, to2);
        assertEquals(observer.getInbox().get(0), "The game was a draw because each player had the same score by time the turn limit was up.");


    }


    @Test
    void observerSentInfoWhenOtherPlayer2CantMove() {
        cantMove.removePiece(new CoordinateImpl(8, 8));
        Coordinate from = new CoordinateImpl(1, 1);
        Coordinate to = new CoordinateImpl(1,2);
        GameObserverImpl observer = new GameObserverImpl();
        cantMove.addObserver(observer);
        cantMove.move(from, to);
        assertEquals(observer.getInbox().get(0), "Pat can't move, Chris wins!");


    }


    @Test
    void observerSentInfoWhenOtherPlayer1CantMove() {
        //make player 1 move so that it can be player 2 turn
        Coordinate p1From = new CoordinateImpl(1, 1);
        Coordinate p1To = new CoordinateImpl(1,2);
        GameObserverImpl observer = new GameObserverImpl();
        cantMove.addObserver(observer);
        cantMove.move(p1From, p1To);
        cantMove.removePiece(new CoordinateImpl(1, 2));
        Coordinate p2From = new CoordinateImpl(8, 8);
        Coordinate p2To = new CoordinateImpl(8,9);
        cantMove.move(p2From, p2To);
        assertEquals(observer.getInbox().get(0), "Chris can't move, Pat wins!");


    }




    @Test
    void observerSentInfoWhenPointConflictAttackerWins() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris's HORSE attacked Pat's DOG at (1, 1) and won.");
    }


    @Test
    void observerSentInfoWhenPointConflictDefenderWins() {
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5,6);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris's HORSE attacked Pat's SNAIL at (5, 6) and lost.");
    }
    @Test
    void observerSentInfoWhenPointConflictDraw() {
        Coordinate from = new CoordinateImpl(4, 4);
        Coordinate to = new CoordinateImpl(4,5);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris's SNAIL attacked Pat's SNAIL at (4, 5) and the conflict was a draw.");
    }


    @Test
    void observerSentInfoWhenPlayerWinsWhenScoreSatisfied() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris wins by getting to 6 points first.");
    }


    @Test
    void observerSentInfoWhenCantMoveWhenScoreConditionMet() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        Coordinate from2 = new CoordinateImpl(5, 6);
        Coordinate to2 = new CoordinateImpl(5,7);
        conflict.move(from2, to2);
        assertEquals(observer.getInbox().get(1), "Invalid move from Pat, the game is already over and Chris already won.");
    }


    @Test
    void observerSentInfoWhenPlayer2ScoreGreaterThanPlayer1AndTurnLimitUp() {
        turnLimitMorePieces.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        GameObserverImpl observer = new GameObserverImpl();
        turnLimitMorePieces.addObserver(observer);
        turnLimitMorePieces.move(from, to);
        Coordinate from2 = new CoordinateImpl(1, 1);
        Coordinate to2 = new CoordinateImpl(2,1);
        turnLimitMorePieces.move(from2, to2);
        assertEquals(observer.getInbox().get(0), "Pat wins by getting the highest score by time the turn limit was up.");
    }


    @Test
    void observerSentInfoWhenPlayer1ScoreGreaterThanPlayer2AndTurnLimitUp() {
        turnLimitMorePieces.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameObserverImpl observer = new GameObserverImpl();
        turnLimitMorePieces.addObserver(observer);
        turnLimitMorePieces.move(from, to);
        Coordinate from2 = new CoordinateImpl(1, 1);
        Coordinate to2 = new CoordinateImpl(1,2);
        turnLimitMorePieces.move(from2, to2);
        assertEquals(observer.getInbox().get(0), "Chris wins by getting the highest score by time the turn limit was up.");
    }


    @Test
    void observerSentInfoWhenSquareUnblockCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,7);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because they tried to move to a block location");


    }


    @Test
    void observerSentInfoWhenCantLandOnPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,11);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because there already exists a piece at (10, 11) and there is no point conflict allowed.");


    }


    @Test
    public void observerSentInfoWhenMovePieceInvalidFromLocation() {
        Coordinate from = new CoordinateImpl(10,2);
        Coordinate to = new CoordinateImpl(2,5);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because a valid path to (2, 5) could not be found.");
    }


    @Test
    void observerSentInfoWhenNotYourTurn() {
        Coordinate from = new CoordinateImpl(30, 30);
        Coordinate to = new CoordinateImpl(31, 30);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because it is not their turn");
    }


    @Test
    void observerSentInfoWhenCantChangeDirection() {
        Coordinate from = new CoordinateImpl(23, 5);
        Coordinate to = new CoordinateImpl(25,4);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because a valid path to (25, 4) could not be found.");


    }
    @Test
    public void observerSentInfoWhenNotEnoughDistance() {
        Coordinate from = new CoordinateImpl(2,2);
        Coordinate to = new CoordinateImpl(2,30);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because their piece did not have enough distance to make it to (2, 30)");
    }







    @Test
    void squareOrthogonalFrogJump() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) manager;
        Coordinate from = new CoordinateImpl(5,4);
        Coordinate to = new CoordinateImpl(6,6);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 17);
    }

    @Test
    void correctCoordinates() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) manager;
        Coordinate from = new CoordinateImpl(4,4);
        Coordinate to = new CoordinateImpl(6,6);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 2);
    }

    @Test
    void correctCoordinates2() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) manager;
        Coordinate from = new CoordinateImpl(4,2);
        Coordinate to = new CoordinateImpl(6,6);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 7);
    }

    @Test
    void brokenTestFixed() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) manager;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(6,6);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 8);
    }


    @Test
    void test1() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) manager;
        Coordinate from = new CoordinateImpl(7,4);
        Coordinate to = new CoordinateImpl(2,5);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 7);
    }




    @Test
    void flyJump() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) manager;
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(8,4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 7);
    }

    @Test
    void unblock() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test7;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(8,4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 10);
    }

    @Test
    void unblock5() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test7;
        Coordinate from = new CoordinateImpl(9,4);
        Coordinate to = new CoordinateImpl(12,4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
    }

    @Test
    void unblock6() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test7;
        Coordinate from = new CoordinateImpl(9,4);
        Coordinate to = new CoordinateImpl(11,4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
    }
    @Test
    void unblock3() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test7;
        Coordinate from = new CoordinateImpl(9,3);
        Coordinate to = new CoordinateImpl(11,3);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 2);
    }


    @Test
    void unblock8() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test7;
        Coordinate from = new CoordinateImpl(9,5);
        Coordinate to = new CoordinateImpl(12,5);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
    }


    //these are actually blocks and not players in this test
    @Test
    void unblock2() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test8;
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(7,5);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 6);
    }

    @Test
    void unblock4() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) infX;
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(7,5);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 6);
    }

    @Test
    void unblock10() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) infY;
        Coordinate from = new CoordinateImpl(3,1);
        Coordinate to = new CoordinateImpl(6,7);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 6);
    }

    @Test
    void hex() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex;
        Coordinate from = new CoordinateImpl(1,-1);
        Coordinate to = new CoordinateImpl(-1,1);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 2);
    }


    @Test
    void hex1() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex;
        Coordinate from = new CoordinateImpl(1,-2);
        Coordinate to = new CoordinateImpl(-2,1);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.getPath().contains(new CoordinateImpl(-1, 0)) && gsi.getPath().contains(new CoordinateImpl(0, -1)));
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 4);
    }

    @Test
    void hex2() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex;
        Coordinate from = new CoordinateImpl(0,-3);
        Coordinate to = new CoordinateImpl(-2,2);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 5);
    }

    @Test
    void turnLimit() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) turnLimit2;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(2,2);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 1);
        assertEquals(egmi.getTurnNumber(), 1);
        Coordinate from2 = new CoordinateImpl(10,10);
        Coordinate to2 = new CoordinateImpl(9,9);
        GameStatusImpl gsi2 = (GameStatusImpl) egmi.move(from2, to2);
        assertTrue(gsi2.isValidMove());
        assertEquals(gsi2.getPathSize(), 1);
        assertEquals(egmi.getTurnNumber(), 2);
        Coordinate from3 = new CoordinateImpl(2,2);
        Coordinate to3 = new CoordinateImpl(3,3);
        GameStatusImpl gsi3 = (GameStatusImpl) egmi.move(from3, to3);
        assertTrue(gsi3.isValidMove());
        assertEquals(gsi3.getPathSize(), 1);
        assertEquals(egmi.getTurnNumber(), 2);
        Coordinate from4 = new CoordinateImpl(9,9);
        Coordinate to4 = new CoordinateImpl(8,8);
        GameStatusImpl gsi4 = (GameStatusImpl) egmi.move(from4, to4);
        assertTrue(gsi4.isValidMove());
        assertEquals(gsi4.getPathSize(), 1);
        assertEquals(egmi.getTurnNumber(), 3);
        Coordinate from5 = new CoordinateImpl(3,3);
        Coordinate to5 = new CoordinateImpl(4,4);
        GameStatusImpl gsi5 = (GameStatusImpl) egmi.move(from5, to5);
        assertFalse(gsi5.isValidMove());
        assertEquals(egmi.getTurnNumber(), 3);
    }

    @Test
    void score() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) score;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(2,2);
        assertEquals(egmi.getPlayer1Score(), 0);
        assertEquals(egmi.getPlayer2Score(), 0);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 1);
        assertEquals(egmi.getPlayer1Score(), 1);
        assertNull(egmi.getPieceAt(to));
        Coordinate from2 = new CoordinateImpl(10,10);
        Coordinate to2 = new CoordinateImpl(9,9);
        GameStatusImpl gsi2 = (GameStatusImpl) egmi.move(from2, to2);
        assertTrue(gsi2.isValidMove());
        assertEquals(gsi2.getPathSize(), 1);
        assertEquals(egmi.getPlayer2Score(), 9);
        assertNull(egmi.getPieceAt(to2));
        Coordinate from3 = new CoordinateImpl(5,5);
        Coordinate to3 = new CoordinateImpl(4,4);
        GameStatusImpl gsi3 = (GameStatusImpl) egmi.move(from3, to3);
        assertTrue(gsi3.isValidMove());
        assertEquals(gsi3.getPathSize(), 1);
        assertEquals(egmi.getPlayer1Score(), 2);
        assertNull(egmi.getPieceAt(to));
        Coordinate from4 = new CoordinateImpl(8,8);
        Coordinate to4 = new CoordinateImpl(9,9);
        GameStatusImpl gsi4 = (GameStatusImpl) egmi.move(from4, to4);
        assertTrue(gsi4.isValidMove());
        assertEquals(gsi4.getPathSize(), 1);
        assertEquals(egmi.getPlayer2Score(), 18);
        assertNull(egmi.getPieceAt(to));
        //System.out.println(gsi4.getMoveResult());
        Coordinate from5 = new CoordinateImpl(1,2);
        Coordinate to5 = new CoordinateImpl(1,3);
        GameStatusImpl gsi5 = (GameStatusImpl) egmi.move(from5, to5);
        assertFalse(gsi5.isValidMove());
        //System.out.println(gsi5.getMoveResult());

    }



    @Test
    void jumpOverExitLandOnBlock() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test9;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(4,1);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
    }

    @Test
    void hex3() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex1;
        Coordinate from = new CoordinateImpl(0,0);
        Coordinate to = new CoordinateImpl(0,4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 4);
    }

    @Test
    void hex4() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex1;
        Coordinate from = new CoordinateImpl(0,0);
        Coordinate to = new CoordinateImpl(0,-4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
    }

    @Test
    void hex5() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex1;
        Coordinate from = new CoordinateImpl(-1,0);
        Coordinate to = new CoordinateImpl(-2,2);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
    }



    @Test
    void hexScore() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) hex1;
        Coordinate from = new CoordinateImpl(-1,0);
        Coordinate to = new CoordinateImpl(-1,1);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getPathSize(), 1);
        assertEquals(egmi.getPlayer1Score(), 5);
        assertNull(egmi.getPieceAt(to));
        assertNull(egmi.getPieceAt(from));
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.NONE);
        Coordinate from2 = new CoordinateImpl(-1,2);
        Coordinate to2 = new CoordinateImpl(-1,1);
        GameStatusImpl gsi2 = (GameStatusImpl) egmi.move(from2, to2);
        assertTrue(gsi2.isValidMove());
        assertEquals(gsi2.getPathSize(), 1);
        assertEquals(egmi.getPlayer2Score(), 9);
        assertNull(egmi.getPieceAt(to2));
        assertNull(egmi.getPieceAt(from2));
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.NONE);
        Coordinate from3 = new CoordinateImpl(0,0);
        Coordinate to3 = new CoordinateImpl(-1,1);
        GameStatusImpl gsi3 = (GameStatusImpl) egmi.move(from3, to3);
        assertTrue(gsi3.isValidMove());
        assertEquals(gsi3.getPathSize(), 1);
        assertEquals(egmi.getPlayer1Score(), 14);
        assertNull(egmi.getPieceAt(to3));
        assertNull(egmi.getPieceAt(from3));
        assertEquals(gsi3.getMoveResult(), GameStatus.MoveResult.WIN);
        Coordinate from4 = new CoordinateImpl(-2,2);
        Coordinate to4 = new CoordinateImpl(-1,1);
        GameStatusImpl gsi4 = (GameStatusImpl) egmi.move(from4, to4);
        assertFalse(gsi4.isValidMove());
        assertEquals(egmi.getPlayer2Score(), 9);
        assertNull(egmi.getPieceAt(to4));
        assertNotNull(egmi.getPieceAt(from4));
        assertEquals(gsi4.getMoveResult(), GameStatus.MoveResult.LOSE);
        Coordinate from5 = new CoordinateImpl(-2,2);
        Coordinate to5 = new CoordinateImpl(-1,1);
        GameStatusImpl gsi5 = (GameStatusImpl) egmi.move(from5, to5);
        assertFalse(gsi5.isValidMove());
        assertEquals(egmi.getPlayer1Score(), 14);
        assertNull(egmi.getPieceAt(to4));
        assertNotNull(egmi.getPieceAt(from4));
        assertEquals(gsi4.getMoveResult(), GameStatus.MoveResult.LOSE);

    }



    @Test
    void hex10() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test7;
        for(Map.Entry<Coordinate, EscapePiece> entry : egmi.getPieces().entrySet()) {
            // System.out.println("("+entry.getKey()+", "+entry.getValue()+")");
            //System.out.println(entry.getValue().getPlayer());
        }
    }

    @Test
    void hex11() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test9;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(2,1);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(egmi.getPlayer1Score(), 1);
        assertNull(egmi.getPieceAt(to));
        assertNull(egmi.getPieceAt(from));
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);

    }

    @Test
    void pointConflict() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) pointConflict;
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(2,2);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());
        EscapePieceImpl epi = (EscapePieceImpl) egmi.getPieceAt(to);
        assertEquals(epi.getValue(), 2);

    }


    @Test
    void pointConflictOwnPiece() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) pointConflict;
        Coordinate from = new CoordinateImpl(3,3);
        Coordinate to = new CoordinateImpl(4,4);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertFalse(gsi.isValidMove());
        EscapePieceImpl epi = (EscapePieceImpl) egmi.getPieceAt(to);
        assertEquals(epi.getValue(), 3);

    }

    @Test
    void testMove() {
        EscapeGameManagerImpl egmi = (EscapeGameManagerImpl) test10;
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(6,5);
        assertEquals(from.getRow(), 1);
        GameStatusImpl gsi = (GameStatusImpl) egmi.move(from, to);
        assertTrue(gsi.isValidMove());

    }


    // Other tests for ignoring getters and setters to help me better understand my code coverage
    // because I didn't know any way to exclude getters and setters from code coverage.
    @Test
    public void boundsGetXMax() {
        assertEquals(squareFinite.getBounds().getxMax(), 40);
    }

    @Test
    public void boundsGetYMax() {
        assertEquals(squareFinite.getBounds().getyMax(), 40);
    }
    @Test
    public void managerGetXMax() {
        assertEquals(squareFinite.getxMax(), 40);
    }

    @Test
    public void managerGetYMax() {
        assertEquals(squareFinite.getyMax(), 40);
    }

    @Test
    public void managerGetCoordType() {
        assertEquals(squareFinite.getCoordinateType(), Coordinate.CoordinateType.SQUARE);
    }

    @Test
    public void pieceToString() {
        assertEquals(squareFinite.getPieceAt(new CoordinateImpl(2, 2)).toString(), "HORSE");
    }

    @Test
    public void observerNotifyThrowable() {
        GameObserver observer = new GameObserverImpl();
        observer.notify("Test", new EscapeException("Test"));
    }

    @Test
    public void testFinalLocation() {
        Coordinate from = new CoordinateImpl(2,2);
        Coordinate to = new CoordinateImpl(2,30);
        assertNull(squareFinite.move(from, to).finalLocation());
    }

    @Test
    public void testPointConflict() {
        PointConflict pc = new PointConflict();
        assertNotNull(pc);
    }

    @Test
    public void testRuleID() {
        Rule rule = new RuleImpl(Rule.RuleID.TURN_LIMIT);
        assertEquals(rule.getId(), Rule.RuleID.TURN_LIMIT);
    }

    @Test
    public void testRuleValue() {
        Rule rule = new RuleImpl(Rule.RuleID.TURN_LIMIT);
        assertEquals(rule.getIntValue(), 0);
    }

    @Test
    public void testRuleSetValue() {
        RuleImpl rule = new RuleImpl(Rule.RuleID.TURN_LIMIT);
        rule.setValue(5);
        assertEquals(rule.getIntValue(), 5);
    }

    @Test
    public void coordinateEqualsSameCoordinate() {
        Coordinate coordinate = new CoordinateImpl(1, 1);
        assertEquals(coordinate, coordinate);
    }

    @Test
    public void otherObjectDoesNotEqualCoordinate() {
        Coordinate coordinate = new CoordinateImpl(1, 1);
        String string = "Test";
        assertNotEquals(coordinate, string);
    }

}

