package escape.impl.gamemanager;

import escape.required.*;

import java.util.List;

//This class is for gathering information about the current game and the most recent move
public class GameStatusImpl implements GameStatus {
    private MoveResult gameResult = MoveResult.NONE;
    private boolean isValidMove = false;
    private boolean isMoreInformation = false;
    private List<Coordinate> path;
    private int pathSize;
    private CombatResult combatResult = CombatResult.NONE;

    public GameStatusImpl(){

    }

    //return true if found a path with correct distance
    @Override
    public boolean isValidMove() {
        return this.isValidMove;
    }

    // return true if information sent to observers
    @Override
    public boolean isMoreInformation() {
        return this.isMoreInformation;
    }

    //Return MoveResult of the move attempted (win, loss, draw)
    @Override
    public MoveResult getMoveResult() {
        return this.gameResult;
    }

    //return null
    @Override
    public Coordinate finalLocation() {
        return null;
    }

    // return the combat result (attacker, defender, or draw)
    @Override
    public CombatResult getCombatResult() {
        return this.combatResult;
    }

    public void setIsValidMove(boolean validMove){
        this.isValidMove = validMove;
    }
    public void setIsMoreInformation(boolean isMoreInformation){
        this.isMoreInformation = isMoreInformation;
    }
    public void setPath(List<Coordinate> path){this.path = path;}
    public List<Coordinate> getPath(){return this.path;}
    public void setMoveResult(MoveResult result){
        this.gameResult = result;
    }

    public int getPathSize(){
        return this.pathSize;
    }

    public void setPathSize(int size){
        this.pathSize = size;
    }
    public void setCombatResult(CombatResult result){
        this.combatResult = result;
    }
}

