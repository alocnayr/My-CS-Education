package escape.impl.gamemanager;
import escape.EscapeGameManager;
import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.board.Bounds;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.boardinformation.playerandpiece.PlayerInformation;
import escape.impl.movement.movemanager.MoveManager;
import escape.impl.movement.movevalidation.MoveValidator;
import escape.required.*;
import escape.required.Coordinate.*;

import java.util.*;

//This class is responsible for managing the Escape game
public class EscapeGameManagerImpl implements EscapeGameManager {

    private final BoardInformation board;
    private final PlayerInformation playerInformation;
    private final ArrayList<GameObserver> observers;
    private final Map<Rule.RuleID, Integer> rules;
    private int turnNumber;
    private boolean gameWon;
    private static Queue<String> observerMessages;

    /**
     *
     * @param board the Escape game board
     * @param playerInformation the player information of the game
     * @param rules the rules of the game
     */
    public EscapeGameManagerImpl (BoardInformation board, PlayerInformation playerInformation, Map<Rule.RuleID, Integer> rules){

        this.board = board;
        this.playerInformation = playerInformation;
        this.rules = new HashMap<>(rules);
        this.observers = new ArrayList<GameObserver>();
        this.turnNumber = 1;
        this.gameWon = false;
        observerMessages = new LinkedList<>();
    }

    /**
     * This method manages the movement of a piece
     * @param from starting location
     * @param to ending location
     * @return the GameStatus from moving a piece
     */
    @Override
    public GameStatus move(Coordinate from, Coordinate to) {
        GameStatusImpl gameStatus = new GameStatusImpl();
        MoveValidator moveValidator = new MoveValidator(board, playerInformation, rules, turnNumber, gameStatus);
        moveValidator.isValidMove(from, to);

        if(!gameWon && gameStatus.isValidMove()) {
            MoveManager moveManager = new MoveManager(board, playerInformation, rules, turnNumber, gameStatus);
            moveManager.move(from, to);
            turnNumber = moveManager.getTurnNumber();
            this.gameWon = moveManager.getGameWon();
            switchPlayers();
        }
        else{
            gameStatus.setIsValidMove(false);
            gameStatus.setMoveResult(GameStatus.MoveResult.LOSE);
            gameWon = true;
        }

        if(!observerMessages.isEmpty()){
            for(String message : observerMessages){
                notifyObservers(message);
            }
            gameStatus.setIsMoreInformation(true);
        }
        observerMessages.clear();
        return gameStatus;
    }

    /**
     * Static method for other classes to add messages to send to observers
     * when needed
     * @param message the message
     */
    public static void addObserverMessage(String message){
        observerMessages.add(message);
    }

    /**
     * Returns a coordinate of the appropriate type.
     * @param x the x component
     * @param y the y component
     * @return the coordinate
     */
    @Override
    public Coordinate makeCoordinate(int x, int y) {
        return new CoordinateImpl(x, y);
    }

    /**
     * Method for adding an observer for game notifications
     * @param observer the observer being added
     * @return the observer added or null if the observer is null
     */
    @Override
    public GameObserver addObserver(GameObserver observer) {
        if(observer != null){
            observers.add(observer);
            return observer;
        }
        return null;
    }

    /**
     * Method for removing an observer from the game
     * @param observer the observer
     * @return the observer that was removed or null if it had not previously been registered
     */
    @Override
    public GameObserver removeObserver(GameObserver observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
            return observer;
        }
        return null;
    }

    /**
     * Switches the current players and is used after each valid move to switch the current player
     */
    public void switchPlayers() {
        if(playerInformation.getCurrentPlayer().equals(playerInformation.getPlayers()[0])){
            playerInformation.setCurrentPlayer(playerInformation.getPlayers()[1]);
        }
        else{
            playerInformation.setCurrentPlayer(playerInformation.getPlayers()[0]);
        }
    }

    /**
     * Method for finding the piece at a location
     * @param coordinate the location of the piece
     * @return the piece at that location
     */
    @Override
    public EscapePiece getPieceAt(Coordinate coordinate){
        return board.getPieceLocations().get(coordinate);
    }

    /**
     * Method for notifying the observers of the state of the game
     * @param message the message sent to the observers
     */
    public void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.notify(message);
        }
    }

    /**
     * Method for notifying the observers of the state of the game
     * @param message the message sent to the observers
     * @param cause the exception
     */
    public void notifyObservers(String message, Throwable cause) {
        for (GameObserver observer : observers) {
            observer.notify(message, cause);
        }
    }


    /**
     * Removes the piece at a location
     * @param coordinate the location of the piece
     */
    public void removePiece(Coordinate coordinate){
        this.board.getPieceLocations().remove(coordinate);
    }



    //Getters and setters

    public Map<Coordinate, EscapePiece> getPieces(){
        return this.board.getPieceLocations();
    }
    public int getxMax(){
        return this.board.getBounds().getxMax();
    }
    public int getyMax(){
        return this.board.getBounds().getyMax();
    }
    public Map<Rule.RuleID, Integer> getRules() {
        return this.rules;
    }
    public CoordinateType getCoordinateType(){
        return this.board.getCoordinateType();
    }

    public String[] getPlayers(){
        return this.playerInformation.getPlayers();
    }
    public String getCurrentPlayer() {
        return this.playerInformation.getCurrentPlayer();
    }
    public int getTurnNumber(){
        return this.turnNumber;
    }
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public Map<Coordinate, LocationType> getLocations(){
        return this.board.getBlockExitLocations();
    }
    public int getPlayer1Score(){
        return this.playerInformation.getPlayer1Score();
    }
    public int getPlayer2Score(){
        return this.playerInformation.getPlayer2Score();
    }
    public PlayerInformation getPlayerInformation(){
        return this.playerInformation;
    }
    public Bounds getBounds(){
        return this.board.getBounds();
    }

    public BoardInformation getBoard() {
        return this.board;
    }

}