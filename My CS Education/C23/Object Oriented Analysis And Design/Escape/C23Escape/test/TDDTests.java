package escape.tests;


import escape.EscapeGameManager;
import escape.impl.boardinformation.board.Bounds;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.impl.gamemanager.GameObserverImpl;
import escape.impl.gamemanager.GameStatusImpl;
import escape.impl.movement.movevalidation.MoveValidator;
import escape.impl.movement.movevalidation.ValidateCoordinateMove;
import escape.builder.EscapeGameBuilder;
import escape.required.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.AssertJUnit;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;


public class TDDTests {


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
    private EscapeGameManager<? extends Coordinate> testingEGM = null;
    private EscapeGameManager EGMrelease2;
    private EscapeGameManager testingEGMInfinite;
    private EscapeGameManagerImpl egmi;
    private EscapeGameManagerImpl egmiInfinite;
    private GameStatus gameStatus;
    private MoveValidator moveValidatorFinite;
    private MoveValidator moveValidatorInfinite;
    private Bounds validateCoordinateBounds;
    private ValidateCoordinateMove inputValidator;



    @BeforeEach
    void setup() throws Exception {
        testingEGM = new EscapeGameBuilder("configurations/test1.egc").makeGameManager();
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
        EGMrelease2 = new EscapeGameBuilder("configurations/test2.egc").makeGameManager();
        testingEGMInfinite = new EscapeGameBuilder("configurations/test3.egc").makeGameManager();
        egmi = (EscapeGameManagerImpl) EGMrelease2;
        egmiInfinite = (EscapeGameManagerImpl) testingEGMInfinite;
        gameStatus = new GameStatusImpl();
        moveValidatorFinite = new MoveValidator(egmi.getBoard(), egmi.getPlayerInformation(), egmi.getRules(), egmi.getTurnNumber(), gameStatus);
        moveValidatorInfinite = new MoveValidator(egmiInfinite.getBoard(), egmiInfinite.getPlayerInformation(), egmiInfinite.getRules(), egmiInfinite.getTurnNumber(), gameStatus);
        validateCoordinateBounds = new Bounds(10, 10);
        inputValidator = new ValidateCoordinateMove(validateCoordinateBounds);


    }


    /*********************************** Final tests ***********************************/


    //Task 1
    @Test
    public void makeGameManagerNotNull(){
        EscapeGameManager escapeGameManager = null;
        try{
            escapeGameManager = new EscapeGameBuilder("configurations/test1.egc").makeGameManager();
        } catch (Exception e){
            fail("Exception from builder: " + e.getMessage());
        }
        assertNotNull(escapeGameManager);
    }

    //Task 2
    @Test
    public void makeCoordinateNotNull() throws Exception {

        EscapeGameManager egm = new EscapeGameBuilder("configurations/test1.egc").makeGameManager();
        Coordinate coordinate = egm.makeCoordinate(0, 0);
        assertNotNull(coordinate);

    }

    //Task 3
    @Test
    public void testCoordinateGetRowNotNull() throws Exception {

        Coordinate coordinate = testingEGM.makeCoordinate(0, 0);
        assertNotNull(coordinate.getRow());

    }

    //Task 4
    @Test
    public void testCoordinateGetColumnNotNull() throws Exception {

        Coordinate coordinate = testingEGM.makeCoordinate(0, 0);
        assertNotNull(coordinate.getColumn());

    }

    //Task 5
    @Test
    public void testCoordinateNotInBounds() throws Exception {

        Coordinate coordinate = testingEGM.makeCoordinate(-100, -200);
        assertNotNull(coordinate);

    }

    //Task 6
    @Test
    public void testCoordinateGetRowCorrectValue() throws Exception {

        Coordinate coordinate = testingEGM.makeCoordinate(0, 0);
        assertEquals(coordinate.getRow(), 0);

    }

    //Task 7
    @Test
    public void testCoordinateGetColumnCorrectValue() throws Exception {

        Coordinate coordinate = testingEGM.makeCoordinate(0, 0);
        assertEquals(coordinate.getColumn(), 0);

    }

    //Task 8
    @Test
    public void getPlayersNotNull() throws Exception {

        EscapeGameManagerImpl testingEGMI = (EscapeGameManagerImpl) testingEGM;
        String[] players = testingEGMI.getPlayers();
        assertNotNull(players);

    }

    //Task 9
    @Test
    public void getPlayersCorrectNames() throws Exception {

        EscapeGameManagerImpl testingEGMI = (EscapeGameManagerImpl) testingEGM;
        String[] players = testingEGMI.getPlayers();
        assertEquals(players[0], "Chris");
        assertEquals(players[1], "Pat");

    }

    //Task 10
    @Test
    public void getCoordinateTypeNotNull() throws Exception {

        EscapeGameManagerImpl testingEGMI = (EscapeGameManagerImpl) testingEGM;
        Coordinate.CoordinateType coordinateType = testingEGMI.getCoordinateType();
        assertNotNull(coordinateType);

    }

    //Task 11
    @Test
    public void getCoordinateTypeCorrectAnswer() throws Exception {

        EscapeGameManagerImpl testingEGMI = (EscapeGameManagerImpl) testingEGM;
        Coordinate.CoordinateType coordinateType = testingEGMI.getCoordinateType();
        assertEquals(coordinateType, Coordinate.CoordinateType.SQUARE);

    }

    //Task 12
    @Test
    void coordEqualsSameCoord() {
        Coordinate coord = new CoordinateImpl(1, 2);
        assertEquals(coord, coord);
    }

    //Task 13
    @Test
    void coordEqualsDifferentObjects() {
        Coordinate coord = new CoordinateImpl(1, 2);
        String string = "Hi";
        Assertions.assertNotEquals(coord, string);
    }


    /********************** Movement Tests Release 2 *************************/


    //Task 14
    @Test
    public void testCurrentPlayer() {
        AssertJUnit.assertEquals(egmi.getCurrentPlayer(), "Chris");
    }


    //Task 15
    @Test
    public void movePieceInvalidFromLocation() {
        Coordinate from = new CoordinateImpl(100,2);
        Coordinate to = new CoordinateImpl(2,5);
        assertFalse(inputValidator.validInput(from, to));
    }


    //Task 16
    @Test
    public void moveToSamePlaceLocation() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,2);
        assertFalse(inputValidator.validInput(from, to));
    }

    //Task 17
    @Test
    public void currentPlayerNoChange() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,2);
        inputValidator.validInput(from, to);
        assertEquals(egmi.getCurrentPlayer(), "Chris");
    }

    //Task 18
    @Test
    public void moveToOtherPieceLocation() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,1);
        assertFalse(moveValidatorFinite.isValidMove(from, to));
    }

    //Task 19
    @Test
    public void pieceDidntChangeLocation() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,1);
        moveValidatorFinite.isValidMove(from, to);
        assertNotNull(egmi.getPieceAt(from));
    }

    //Task 20
    @Test
    public void movingPieceThatDoesNotBelongToCurrentPlayerTest() {
        Coordinate from = new CoordinateImpl(1,1);
        Coordinate to = new CoordinateImpl(2,1);
        assertFalse(moveValidatorFinite.isValidMove(from, to));
    }

    //Task 21
    @Test
    public void moveToLocationNotOnBoard() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(100,100);
        assertFalse(moveValidatorFinite.isValidMove(from, to));
    }

    //Task 22
    @Test
    public void toLocationOnBoardInfiniteBoard() {

        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(-10,-10);
        assertTrue(egmiInfinite.move(from, to).isValidMove());

    }

    //Task 23
    @Test
    public void moveUpOneBlock() {

        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,3);
        assertNotNull(egmi.move(from, to));
    }

    //Task 24
    @Test
    public void verifyChangeInCurrentPlayerAfterMove() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,3);
        assertEquals(egmi.getCurrentPlayer(), "Chris");
        egmi.move(from, to);
        assertEquals(egmi.getCurrentPlayer(), "Pat");
    }

    //Task 25
    @Test
    public void verifyChangeInPieceLocation() {
        Coordinate from = new CoordinateImpl(1,2);
        Coordinate to = new CoordinateImpl(1,3);
        egmi.move(from, to);
        assertNull(egmi.getPieceAt(from));
        assertNotNull(egmi.getPieceAt(to));
    }

    //Task 26
    @Test
    public void verifyOrthogonalMovementNorth() {
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(10,11);
        assertTrue(egmi.move(from, to).isValidMove());

    }

    //Task 27
    @Test
    public void verifyOrthogonalMovementSouth() {
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(10,9);
        assertTrue(egmi.move(from, to).isValidMove());
    }

    //Task 28
    @Test
    public void verifyOrthogonalMovementEast() {
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(11,10);
        assertTrue(egmi.move(from, to).isValidMove());

    }

    //Task 29
    @Test
    public void verifyOrthogonalMovementWest() {
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(9,10);
        assertTrue(egmi.move(from, to).isValidMove());
    }

    //Task 30
    @Test
    public void verifyOrthogonalMovementMakeATurn() {
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(11, 11);
        GameStatusImpl status = (GameStatusImpl) egmi.move(from, to);
        assertTrue(status.isValidMove());
        assertTrue(status.getPath().contains(new CoordinateImpl(10,11)) || status.getPath().contains(new CoordinateImpl(11,10)));

    }

    //Task 31
    @Test
    public void verifyOrthogonalMovementCantMoveDiagonally() {
        Coordinate from = new CoordinateImpl(30,30);
        Coordinate to = new CoordinateImpl(31,31);
        GameStatusImpl status = (GameStatusImpl) egmiInfinite.move(from, to);
        assertTrue(status.isValidMove());
        //If path is size 1 then it move diagonally
        assertEquals(status.getPathSize(), 2);

    }

    //Task 32
    @Test
    public void verifyDiagonalMovementNorthEast() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate to = new CoordinateImpl(13,13);
        assertTrue(egmi.move(from, to).isValidMove());

    }

    //Task 33
    @Test
    public void verifyDiagonalMovementNorthWest() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate to = new CoordinateImpl(11,13);
        assertTrue(egmi.move(from, to).isValidMove());
    }

    //Task 34
    @Test
    public void verifyDiagonalMovementSouthEast() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate to = new CoordinateImpl(13,11);
        assertTrue(egmi.move(from, to).isValidMove());
    }

    //Task 35
    @Test
    public void verifyDiagonalMovementSouthWest() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate to = new CoordinateImpl(11,11);
        assertTrue(egmi.move(from, to).isValidMove());

    }

    //Task 36
    @Test
    public void verifyDiagonalMovementInvalidMovementNorth() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate north = new CoordinateImpl(12,13);
        assertFalse(egmi.move(from, north).isValidMove());
    }

    //Task 37
    @Test
    public void verifyDiagonalMovementInvalidMovementSouth() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate south = new CoordinateImpl(12,11);
        assertFalse(egmi.move(from, south).isValidMove());
    }

    //Task 38
    @Test
    public void verifyDiagonalMovementInvalidMovementEast() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate east = new CoordinateImpl(13,12);
        assertFalse(egmi.move(from, east).isValidMove());
    }

    //Task 39
    @Test
    public void verifyDiagonalMovementInvalidMovementWest() {
        Coordinate from = new CoordinateImpl(12,12);
        Coordinate west = new CoordinateImpl(13,16);
        assertFalse(egmi.move(from, west).isValidMove());
    }

    //Task 40
    @Test
    public void verifyDiagonalMovementTurn() {
        Coordinate from = new CoordinateImpl(14,16);
        Coordinate to = new CoordinateImpl(14,18);
        GameStatusImpl status = (GameStatusImpl) egmiInfinite.move(from, to);
        assertTrue(status.isValidMove());
        assertTrue(status.getPath().contains(new CoordinateImpl(13,17))||status.getPath().contains(new CoordinateImpl(15,17)));

    }

    //Task 41
    @Test
    public void verifyLinearMoveNorth() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(2,11);
        assertTrue(egmi.move(from, to).isValidMove());

    }
    //Task 42
    @Test
    public void verifyLinearMoveSouth() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(2,9);
        assertTrue(egmi.move(from, to).isValidMove());

    }
    //Task 43
    @Test
    public void verifyLinearMoveEast() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(3,10);
        assertTrue(egmi.move(from, to).isValidMove());

    }
    //Task 44
    @Test
    public void verifyLinearMoveWest() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(1,10);
        assertTrue(egmi.move(from, to).isValidMove());

    }

    //Task 45
    @Test
    public void verifyLinearMoveNorthEast() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(3,11);
        assertTrue(egmi.move(from, to).isValidMove());

    }
    //Task 46
    @Test
    public void verifyLinearMoveNorthWest() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(1,11);
        assertTrue(egmi.move(from, to).isValidMove());

    }
    //Task 47
    @Test
    public void verifyLinearMoveSouthEast() {
        Coordinate from = new CoordinateImpl(2,10);;
        Coordinate to = new CoordinateImpl(3,9);
        assertTrue(egmi.move(from, to).isValidMove());

    }
    //Task 48
    @Test
    public void verifyLinearMoveSouthWest() {
        Coordinate from = new CoordinateImpl(2,10);
        Coordinate to = new CoordinateImpl(1,9);
        assertTrue(egmi.move(from, to).isValidMove());

    }

    //Task 49
    @Test
    public void verifyLinearCantChangeDirection() {
        Coordinate from = new CoordinateImpl(5,11);
        //has to change direction because there is a piece in the way
        assertNotNull(egmi.getPieceAt(new CoordinateImpl(5, 12)));
        //(5,11) , (6, 12) , (5, 13)
        Coordinate to = new CoordinateImpl(5,13);
        assertFalse(egmi.move(from, to).isValidMove());

    }

    //Task 50
    @Test
    //since omni is just a combination of diagonal and orthogonal (the same neighbors as both of those combined),
    //just verify that it can move orthogonally and diagonally in the same movement
    public void verifyOmniMovement() {
        Coordinate from = new CoordinateImpl(19,19);
        Coordinate to = new CoordinateImpl(20,17);
        GameStatusImpl status = (GameStatusImpl) egmi.move(from, to);
        assertTrue(status.isValidMove());
        //diagonally
        assertTrue(status.getPath().contains(new CoordinateImpl(20,18)));
        //orthogonal
        assertTrue(status.getPath().contains(new CoordinateImpl(20,17)));
    }

    //Task 51
    @Test
    public void cantMoveWithoutFlyAndTestDistance() {
        Coordinate from = new CoordinateImpl(16,17);
        Coordinate to = new CoordinateImpl(18,18);
        //verify that there's a piece in the way of the path
        assertNotNull(egmi.getPieceAt(new CoordinateImpl(17, 17)));
        assertNotNull(egmi.getPieceAt(new CoordinateImpl(17, 18)));
        // orthogonal piece only has distance 3 and can't complete the move without flying over piece at (17,18) or (17, 17)
        GameStatusImpl status = (GameStatusImpl) egmi.move(from, to);
        assertFalse(egmi.move(from, to).isValidMove());
    }

    //Task 52
    @Test
    public void moveWithFly() {
        Coordinate from = new CoordinateImpl(16,16);
        Coordinate to = new CoordinateImpl(18,18);
        //verify that there's a piece in the way of the path
        assertNotNull(egmi.getPieceAt(new CoordinateImpl(17, 17)));
        // linear piece only has distance 3 and can't complete the move without jumping over piece at (17,17)
        // in this case it has jump, so the jump should be able to be completed
        GameStatusImpl status = (GameStatusImpl) egmi.move(from, to);
        assertTrue(status.isValidMove());
    }

    //Task 53
    @Test
    public void verifyGetMoveResultReturnNone() {
        Coordinate from = new CoordinateImpl(16,16);
        Coordinate to = new CoordinateImpl(18,18);
        GameStatus moveWithFlyTestStatus = egmi.move(from, to);
        assertEquals(moveWithFlyTestStatus.getMoveResult(), GameStatus.MoveResult.NONE);

    }


    /*********************** Final Release Tests *****************************/

    //Task 54
    @Test
    void testGetPieceAtValid() {
        assertNotNull(squareFinite.getPieceAt(new CoordinateImpl(30, 30)));
    }
    //Task 55
    @Test
    void testGetPieceAtInvalid() {
        assertNull(squareFinite.getPieceAt(new CoordinateImpl(35, 33)));
    }
    //Task 56
    @Test
    void testGetPieceNameValid() {
        assertEquals(squareFinite.getPieceAt(new CoordinateImpl(3, 3)).getName(), EscapePiece.PieceName.SNAIL);
    }

    //Task 57
    @Test
    void testGetPlayerName() {
        assertEquals(squareFinite.getPieceAt(new CoordinateImpl(3, 3)).getPlayer(), "Chris");
    }

    //Task 58
    @Test
    void pieceDefaultValue() {
        EscapePieceImpl currentPiece = (EscapePieceImpl) squareFinite.getPieceAt(new CoordinateImpl(2, 2));
        assertEquals(currentPiece.getValue(), 1);
    }

    //Task 59
    @Test
    void pieceCorrectInputValue() {
        EscapePieceImpl currentPiece = (EscapePieceImpl) squareFinite.getPieceAt(new CoordinateImpl(3, 3));
        assertEquals(currentPiece.getValue(), 5);
    }

    //Task 60
    @Test
    void hexLinearNorth() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate north = new CoordinateImpl(2, 3);
        assertTrue(hexFinite.move(from, north).isValidMove());
    }
    //Task 61
    @Test
    void hexLinearSouth() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate south = new CoordinateImpl(2,1);
        assertTrue(hexFinite.move(from, south).isValidMove());
    }
    //Task 62
    @Test
    void hexLinearEast() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate east = new CoordinateImpl(3,2);
        assertTrue(hexFinite.move(from, east).isValidMove());
    }
    //Task 63
    @Test
    void hexLinearWest() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate west = new CoordinateImpl(1,2);
        assertTrue(hexFinite.move(from, west).isValidMove());
    }
    //Task 64
    @Test
    void hexLinearNorthwest() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate northWest = new CoordinateImpl(1,3);
        assertTrue(hexFinite.move(from, northWest).isValidMove());
    }
    //Task 65
    @Test
    void hexLinearSoutheast() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate southEast = new CoordinateImpl(3,1);
        assertTrue(hexFinite.move(from, southEast).isValidMove());
    }
    //Task 66
    @Test
    void hexLinearCantMoveSouthwest() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate southWest = new CoordinateImpl(1,1);
        //Can't move down to (1, 1) without changing direction -> southwest is not a direct linear move
        assertFalse(hexFinite.move(from, southWest).isValidMove());
    }

    //Task 67
    @Test
    void hexLinearCantMoveNortheast() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate southWest = new CoordinateImpl(3,3);
        //Can't move up to (3, 3) without changing direction -> northeast is not a direct linear move
        assertFalse(hexFinite.move(from, southWest).isValidMove());
    }
    //Task 68
    @Test
    void hexLinearFiniteBoardCantMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    //Task 69
    @Test
    void hexLinearFiniteBoardCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-1000000000,2);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    //Task 70
    @Test
    void hexLinearFiniteBoardCantMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    //Task 71
    @Test
    void hexLinearFiniteBoardCantMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-1000000000);
        assertFalse(hexFinite.move(from, to).isValidMove());
    }

    //Task 72
    @Test
    void hexLinearInfiniteXCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }

    //Task 73
    @Test
    void hexLinearInfiniteXCanMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-2,2);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }

    //Task 74
    @Test
    void hexLinearInfiniteXCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }

    //Task 75
    @Test
    void hexLinearInfiniteXCantMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-20000);
        assertFalse(hexInfX.move(from, to).isValidMove());
    }

    //Task 76
    @Test
    void hexLinearInfiniteYCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }

    //Task 77
    @Test
    void hexLinearInfiniteYCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-200000,2);
        assertFalse(hexInfY.move(from, to).isValidMove());
    }

    //Task 78
    @Test
    void hexLinearInfiniteYCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfX.move(from, to).isValidMove());
    }


    //Task 79
    @Test
    void hexLinearInfiniteYCantMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-2);
        assertTrue(hexInfY.move(from, to).isValidMove());
    }

    //Task 80
    @Test
    void hexLinearInfiniteCanMoveToRow0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(0,2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }


    //Task 81
    @Test
    void hexLinearInfiniteCanMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-2,2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }

    //Task 82
    @Test
    void hexLinearInfiniteCanMoveToColumn0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,0);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }

    //Task 83
    @Test
    void hexLinearInfiniteCanMoveToColumnLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,-2);
        assertTrue(hexInfinite.move(from, to).isValidMove());
    }

    //Task 84
    @Test
    void hexOmniNorth() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate north = new CoordinateImpl(3, 4);
        assertTrue(hexFinite.move(from, north).isValidMove());
    }
    //Task 85
    @Test
    void hexOmniSouth() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate south = new CoordinateImpl(3,2);
        assertTrue(hexFinite.move(from, south).isValidMove());
    }
    //Task 86
    @Test
    void hexOmniEast() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate east = new CoordinateImpl(4,3);
        assertTrue(hexFinite.move(from, east).isValidMove());
    }
    //Task 87
    @Test
    void hexOmniWest() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate west = new CoordinateImpl(2,3);
        assertTrue(hexFinite.move(from, west).isValidMove());
    }
    //Task 88
    @Test
    void hexOmniNorthwest() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate northWest = new CoordinateImpl(2,4);
        assertTrue(hexFinite.move(from, northWest).isValidMove());
    }
    //Task 89
    @Test
    void hexOmniSoutheast() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate southEast = new CoordinateImpl(4,2);
        assertTrue(hexFinite.move(from, southEast).isValidMove());
    }

    //Task 90
    @Test
    void hexOmniChangeDirection() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3,1);
        assertTrue(hexFinite.move(from, to).isValidMove());
    }
    //Task 91
    @Test
    void unblockMoveThroughBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,8);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }

    //Task 92
    @Test
    void unblockCanMovePastMultipleBlocks() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 5) and (6, 4)
        Coordinate to = new CoordinateImpl(6,3);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }

    //Task 93
    @Test
    void unblockCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,7);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 94
    @Test
    void unblockCantMoveThroughOtherPlayer() {
        Coordinate from = new CoordinateImpl(6, 6);
        //player at (5, 6)
        Coordinate to = new CoordinateImpl(4,6);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertFalse(gsi.getPath().contains(new CoordinateImpl(5, 6)));
    }

    //Task 95
    @Test
    void unblockCantMoveThroughExitNotFinalLocation() {
        Coordinate from = new CoordinateImpl(6, 6);
        //exit at (7, 6)
        Coordinate to = new CoordinateImpl(8,6);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertFalse(gsi.getPath().contains(new CoordinateImpl(7, 6)));
    }

    //Task 96
    @Test
    void unblockExitFinalLocation() {
        Coordinate from = new CoordinateImpl(6, 6);
        //exit at (7, 6)
        Coordinate to = new CoordinateImpl(7,6);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
    }

    //Task 97
    @Test
    void flyCanMoveOverBlock() {
        Coordinate from = new CoordinateImpl(10, 10);
        //block at (10, 9)
        Coordinate to = new CoordinateImpl(10,8);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(10, 9)));
    }

    //Task 98
    @Test
    void flyCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(10, 10);
        //block at (10, 9)
        Coordinate to = new CoordinateImpl(10,9);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 99
    @Test
    void flyCanMoveOverPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,12);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(10, 11)));
    }

    //Task 100
    @Test
    void flyCantLandOnPlayer() {
        Coordinate from = new CoordinateImpl(10, 10);
        //piece at (10, 11)
        Coordinate to = new CoordinateImpl(10,11);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 101
    @Test
    void flyCanMoveOverExit() {
        Coordinate from = new CoordinateImpl(10, 10);
        //exit at (9, 10)
        Coordinate to = new CoordinateImpl(8,10);
        GameStatusImpl gsi = (GameStatusImpl) squareFinite.move(from, to);
        assertTrue(gsi.isValidMove());
        assertTrue(gsi.getPath().contains(new CoordinateImpl(9, 10)));
    }

    //Task 102
    @Test
    void omniJumpMoveNormally() {
        Coordinate from = new CoordinateImpl(14, 3);
        Coordinate north = new CoordinateImpl(14,4);
        assertTrue(squareFinite.move(from, north).isValidMove());
    }

    //Task 103
    @Test
    void jumpCantJumpOverBlock() {
        Coordinate from = new CoordinateImpl(18, 3);
        Coordinate to = new CoordinateImpl(18,5);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 104
    @Test
    void jumpOverPiece() {
        Coordinate from = new CoordinateImpl(20, 2);
        Coordinate to = new CoordinateImpl(20,4);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }

    //Task 105
    @Test
    void jumpOverMultiplePiecesInARow() {
        Coordinate from = new CoordinateImpl(22, 2);
        Coordinate to = new CoordinateImpl(25,2);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 106
    @Test
    void jumpCantChangeDirection() {
        Coordinate from = new CoordinateImpl(23, 5);
        Coordinate to = new CoordinateImpl(25,4);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 107
    @Test
    void jumpOverPieceLandOnBlock() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(31,3);
        assertFalse(squareFinite.move(from, to).isValidMove());
    }

    //Task 108
    @Test
    void squareJumpOverExitLandOnClear() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(28,5);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }

    //Task 109
    @Test
    void squareJumpOverExitLandOnExit() {
        Coordinate from = new CoordinateImpl(28, 3);
        Coordinate to = new CoordinateImpl(28,1);
        assertTrue(squareFinite.move(from, to).isValidMove());
    }

    //Task 110
    @Test
    void correctTurnLimit() {
        assertEquals(turnLimit.getRules().get(Rule.RuleID.TURN_LIMIT), 2);
    }

    //Task 111
    @Test
    void correctSetTurnNumber() {
        assertEquals(turnLimit.getTurnNumber(), 1);
        turnLimit.setTurnNumber(3);
        assertEquals(turnLimit.getTurnNumber(), 3);
    }

    //Task 112
    @Test
    void cantMovePastTurnLimit() {
        turnLimit.setTurnNumber(3);
        assertEquals(turnLimit.getTurnNumber(), 3);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        assertFalse(turnLimit.move(from, to).isValidMove());
    }

    //Task 113
    @Test
    void removeAllPiecesBeforeTurnLimit() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameStatusImpl gsi = (GameStatusImpl) turnLimit.move(from, to);
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    //Task 114
    @Test
    void cantMoveAfterPlayerRemovedAllPieces() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameStatusImpl gsi = (GameStatusImpl) turnLimit.move(from, to);
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
        Coordinate from2 = new CoordinateImpl(3, 3);
        Coordinate to2 = new CoordinateImpl(2,1);
        GameStatusImpl gsi2 = (GameStatusImpl) turnLimit.move(from2, to2);
        assertFalse(gsi2.isValidMove());
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.LOSE);
    }

    //Task 115
    @Test
    void turnLimitDraw() {
        turnLimit.setTurnNumber(2);
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,3);
        GameStatusImpl gsi = (GameStatusImpl) turnLimit.move(from, to);
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.NONE);
        Coordinate from2 = new CoordinateImpl(3, 3);
        Coordinate to2 = new CoordinateImpl(3,4);
        GameStatusImpl gsi2 = (GameStatusImpl) turnLimit.move(from2, to2);
        assertEquals(gsi2.getMoveResult(), GameStatus.MoveResult.DRAW);
    }

    //Task 116
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

    //Task 117
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

    //Task 118
    @Test
    void otherPlayer2CantMove() {
        cantMove.removePiece(new CoordinateImpl(8, 8));
        Coordinate from = new CoordinateImpl(1, 1);
        Coordinate to = new CoordinateImpl(1,2);
        GameStatusImpl gsi = (GameStatusImpl) cantMove.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    //Task 119
    @Test
    void otherPlayer1CantMove() {
        //make player 1 move so that it can be player 2 turn
        Coordinate p1From = new CoordinateImpl(1, 1);
        Coordinate p1To = new CoordinateImpl(1,2);
        cantMove.move(p1From, p1To);
        cantMove.removePiece(new CoordinateImpl(1, 2));

        Coordinate p2From = new CoordinateImpl(8, 8);
        Coordinate p2To = new CoordinateImpl(8,9);
        GameStatusImpl gsiP2 = (GameStatusImpl) cantMove.move(p2From, p2To);
        assertEquals(gsiP2.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    //Task 120
    @Test
    void pointConflictMoveToOtherPieceLocation() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        assertTrue(conflict.move(from, to).isValidMove());
    }

    //Task 121
    @Test
    void pointConflictRemovePieceAtStartingLocation() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertTrue(gsi.isValidMove());
        assertNull(conflict.getPieceAt(from));
    }


    //Task 122
    @Test
    void pointConflictRemovesPieceWithLowerValue() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        conflict.move(from, to);
        assertEquals(conflict.getPieceAt(to).getPlayer(), "Chris");
    }

    //Task 123
    @Test
    void pointConflictSetCorrectWinningPieceValue() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        conflict.move(from, to);
        EscapePieceImpl epi = (EscapePieceImpl) conflict.getPieceAt(to);
        assertEquals(epi.getValue(), 1);
    }
    //Task 124
    @Test
    void pointConflictWithPieceOfEqualValue() {
        Coordinate from = new CoordinateImpl(4, 4);
        Coordinate to = new CoordinateImpl(4,5);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(from));
        assertNull(conflict.getPieceAt(to));
    }

    //Task 125
    @Test
    void pointConflictWithMovingPieceLosing() {
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5,6);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(from));
        assertEquals(conflict.getPieceAt(to).getPlayer(), "Pat");
    }

    //Task 126
    @Test
    void pointConflictAttackerWins() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertEquals(gsi.getCombatResult(), GameStatus.CombatResult.ATTACKER);
    }

    //Task 127
    @Test
    void pointConflictDefenderWins() {
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5,6);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertEquals(gsi.getCombatResult(), GameStatus.CombatResult.DEFENDER);
    }

    //Task 128
    @Test
    void pointConflictDraw() {
        Coordinate from = new CoordinateImpl(4, 4);
        Coordinate to = new CoordinateImpl(4,5);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertEquals(gsi.getCombatResult(), GameStatus.CombatResult.DRAW);
    }

    //Task 129
    @Test
    void pointConflictWithOwnPiece(){
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(10,9);
        assertFalse(conflict.move(from, to).isValidMove());
    }

    //Task 130
    @Test
    void pointConflictCantLandOnOtherPieceNotFinalLocation(){
        Coordinate from = new CoordinateImpl(10, 10);
        Coordinate to = new CoordinateImpl(10,13);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertFalse(gsi.isValidMove());
    }

    //Task 131
    @Test
    void pointConflictCanLandOnOtherPieceFinalLocation(){
        Coordinate from = new CoordinateImpl(10, 10);
        Coordinate to = new CoordinateImpl(10,12);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertTrue(gsi.isValidMove());
    }

    //Task 132
    @Test
    void testValidMoveToExitLocation() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        assertTrue(conflict.move(from, to).isValidMove());
    }

    //Task 133
    @Test
    void pieceRemovalWhenMoveToExitLocationAtFromLocation() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(from));
    }

    //Task 134
    @Test
    void noPieceAtExitLocationWhenMovingThere() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        conflict.move(from, to);
        assertNull(conflict.getPieceAt(to));
    }

    //Task 135
    @Test
    void movingToExitAddsPlayerScore() {
        Coordinate from = new CoordinateImpl(6, 6);
        Coordinate to = new CoordinateImpl(6,7);
        conflict.move(from, to);
        assertEquals(conflict.getPlayer1Score(), 3);
    }

    //Task 136
    @Test
    void playerWinsWhenScoreSatisfied() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertTrue(gsi.isValidMove());
        assertEquals(gsi.getMoveResult(), GameStatus.MoveResult.WIN);
    }

    //Task 137
    @Test
    void cantMoveWhenScoreConditionMet() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        assertTrue(conflict.move(from, to).isValidMove());
        Coordinate from2 = new CoordinateImpl(5, 6);
        Coordinate to2 = new CoordinateImpl(5,7);
        assertFalse(conflict.move(from2, to2).isValidMove());
    }


    //Task 138
    @Test
    void testIsMoreInformation() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3, 3);
        conflict.addObserver( new GameObserverImpl());
        GameStatusImpl gsi = (GameStatusImpl) conflict.move(from, to);
        assertFalse(gsi.isValidMove());
        assertTrue(gsi.isMoreInformation());
    }

    //Task 139
    @Test
    public void testAddObserver() {
        GameObserverImpl observer = new GameObserverImpl();
        assertNotNull(squareFinite.addObserver(observer));
    }

    //Task 140
    @Test
    public void testRemoveObserver() {
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        assertNotNull(squareFinite.removeObserver(observer));
    }

    //Task 141
    @Test
    public void testAddObserverNull() {
        GameObserverImpl observer = null;
        assertNull(squareFinite.addObserver(observer));
    }

    //Task 142
    @Test
    public void testRemoveObserverNull() {
        GameObserverImpl observer = new GameObserverImpl();
        assertNull(squareFinite.removeObserver(observer));
    }

    //Task 143
    @Test
    public void notifyObserver() {
        GameObserverImpl observer = new GameObserverImpl();
        observer.notify("Test");
        assertNotNull(observer.getInbox());
    }

    //Task 144
    @Test
    void observerInfoSentWhenMoveToSameLocation() {
        Coordinate from = new CoordinateImpl(3, 3);
        Coordinate to = new CoordinateImpl(3, 3);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because they tried to move to the same location");
    }

    //Task 145
    @Test
    void observerInfoSentWhenInfiniteYCantMoveToRowLessThan0() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(-200000,2);
        GameObserverImpl observer = new GameObserverImpl();
        hexInfY.addObserver(observer);
        hexInfY.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because they tried to move to a location off the board");


    }
    //Task 146
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

    //Task 147
    @Test
    void observerSentInfoWhenRemoveAllPiecesBeforeTurnLimit() {
        Coordinate from = new CoordinateImpl(2, 2);
        Coordinate to = new CoordinateImpl(2,1);
        GameObserverImpl observer = new GameObserverImpl();
        turnLimit.addObserver(observer);
        turnLimit.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris has no pieces left. They win!");
    }

    //Task 148
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

    //Task 149
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

    //Task 150
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

    //Task 151
    @Test
    void observerSentInfoWhenPointConflictAttackerWins() {
        Coordinate from = new CoordinateImpl(1, 2);
        Coordinate to = new CoordinateImpl(1,1);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris's HORSE attacked Pat's DOG at (1, 1) and won.");
    }

    //Task 152
    @Test
    void observerSentInfoWhenPointConflictDefenderWins() {
        Coordinate from = new CoordinateImpl(5, 5);
        Coordinate to = new CoordinateImpl(5,6);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris's HORSE attacked Pat's SNAIL at (5, 6) and lost.");
    }
    //Task 153
    @Test
    void observerSentInfoWhenPointConflictDraw() {
        Coordinate from = new CoordinateImpl(4, 4);
        Coordinate to = new CoordinateImpl(4,5);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris's SNAIL attacked Pat's SNAIL at (4, 5) and the conflict was a draw.");
    }

    //Task 154
    @Test
    void observerSentInfoWhenPlayerWinsWhenScoreSatisfied() {
        Coordinate from = new CoordinateImpl(8, 8);
        Coordinate to = new CoordinateImpl(8,9);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Chris wins by getting to 6 points first.");
    }

    //Task 155
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

    //Task 156
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

    //Task 157
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

    //Task 158
    @Test
    void observerSentInfoWhenUnblockCantLandOnBlock() {
        Coordinate from = new CoordinateImpl(6, 6);
        //block at (6, 7)
        Coordinate to = new CoordinateImpl(6,7);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because they tried to move to a block location");


    }

    //Task 159
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

    //Task 160
    @Test
    public void observerSentInfoWhenMovePieceInvalidFromLocation() {
        Coordinate from = new CoordinateImpl(10,2);
        Coordinate to = new CoordinateImpl(2,5);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because a valid path to (2, 5) could not be found.");
    }

    //Task 161
    @Test
    void observerSentInfoWhenNotYourTurn() {
        Coordinate from = new CoordinateImpl(30, 30);
        Coordinate to = new CoordinateImpl(31, 30);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because it is not their turn");
    }

    //Task 162
    @Test
    void observerSentInfoWhenCantChangeDirection() {
        Coordinate from = new CoordinateImpl(23, 5);
        Coordinate to = new CoordinateImpl(25,4);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because a valid path to (25, 4) could not be found.");


    }

    //Task 163
    @Test
    public void observerSentInfoWhenNotEnoughDistance() {
        Coordinate from = new CoordinateImpl(2,2);
        Coordinate to = new CoordinateImpl(2,30);
        GameObserverImpl observer = new GameObserverImpl();
        squareFinite.addObserver(observer);
        squareFinite.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because their piece did not have enough distance to make it to (2, 30)");
    }

    //Task 164
    @Test
    void observerSentInfoWhenPointConflictWithSelf(){
        Coordinate from = new CoordinateImpl(10,10);
        Coordinate to = new CoordinateImpl(10,9);
        GameObserverImpl observer = new GameObserverImpl();
        conflict.addObserver(observer);
        conflict.move(from, to);
        assertEquals(observer.getInbox().get(0), "Invalid move: Chris loses because there already exists a piece at (10, 9) and you can't have a point conflict with your own player.");
    }


}

