package escape.impl.movement.movevalidation.rules;

import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.impl.gamemanager.GameStatusImpl;
import escape.impl.boardinformation.playerandpiece.PlayerInformation;
import escape.required.GameStatus;
import escape.required.Rule;

import java.util.Map;

// Manager for managing score and turn limit rules of the game
public class RuleHandler {
    private final PlayerInformation playerInformation;
    private final Map<Rule.RuleID, Integer> rules;
    private final int turnNumber;
    private final GameStatusImpl gameStatus;
    private boolean gameWon;

    /**
     * Constructor for the RuleHandler
     * @param playerInformation locations of the players
     * @param rules the rules of the game
     * @param turnNumber the turn number of the game
     * @param gameStatus the gamestatus of the game
     */
    public RuleHandler(PlayerInformation playerInformation, Map<Rule.RuleID, Integer> rules,
                       int turnNumber, GameStatusImpl gameStatus){
        this.playerInformation = playerInformation;
        this.rules = rules;
        this.turnNumber = turnNumber;
        this.gameStatus = gameStatus;
    }

    /**
     * Manages score and turn limit rules
     */
    public void handleRules(){
        if(rules.containsKey(Rule.RuleID.SCORE)) {
            handleScoreRule();
        }
        if (rules.containsKey(Rule.RuleID.TURN_LIMIT)) {
            handleTurnLimitRule();
        }

    }

    /**
     * Handles the score rule by determining if there is a player who reached the score
     */
    private void handleScoreRule() {
        if (playerInformation.getPlayer1Score() >= rules.get(Rule.RuleID.SCORE) ||
                playerInformation.getPlayer2Score() >= rules.get(Rule.RuleID.SCORE)) {
            EscapeGameManagerImpl.addObserverMessage(playerInformation.getCurrentPlayer() + " wins by getting to " + rules.get(Rule.RuleID.SCORE) + " points first.");
            gameStatus.setMoveResult(GameStatus.MoveResult.WIN);
        }
    }

    /**
     * Handles the turn limit rule by determining if the game has reached the turn limit
     * and if someone has won or draw
     */
    private void handleTurnLimitRule(){

        int turnLimit = rules.get(Rule.RuleID.TURN_LIMIT);

        if (turnNumber == turnLimit && playerInformation.getCurrentPlayer().equals(playerInformation.getPlayers()[1])) {
            if (playerInformation.getPlayer2Score() > playerInformation.getPlayer1Score()) {
                EscapeGameManagerImpl.addObserverMessage(playerInformation.getPlayers()[1] + " wins by getting the highest score by time the turn limit was up.");
                gameStatus.setMoveResult(GameStatus.MoveResult.WIN);
            }
            else if (playerInformation.getPlayer2Score() < playerInformation.getPlayer1Score()) {
                EscapeGameManagerImpl.addObserverMessage(playerInformation.getPlayers()[0] + " wins by getting the highest score by time the turn limit was up.");
                gameStatus.setMoveResult(GameStatus.MoveResult.LOSE);
            } else {
                EscapeGameManagerImpl.addObserverMessage("The game was a draw because each player had the same score by time the turn limit was up.");
                gameStatus.setMoveResult(GameStatus.MoveResult.DRAW);
            }
            this.gameWon = true;
            return;
        }
        this.gameWon = false;
        return;
    }

    public boolean getGetWon(){
        return this.gameWon;
    }

}
