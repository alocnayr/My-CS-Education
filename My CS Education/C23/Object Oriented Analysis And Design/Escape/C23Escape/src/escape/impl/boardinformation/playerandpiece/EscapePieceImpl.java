package escape.impl.boardinformation.playerandpiece;

import escape.required.EscapePiece;
import escape.required.PieceAttribute;

import java.util.HashSet;
import java.util.Set;

// This class is an implementation of the EscapePiece interface
public class EscapePieceImpl implements EscapePiece {

    private final PieceName pieceName;
    private final PieceAttribute[] pieceAttributes;
    private final String player;
    private final MovementPattern movementPattern;
    private int value;
    Set<PieceAttributeID> movementAttributes;

    /**
     *
     * @param pieceName the identifying name of the piece (snail, bird, frog, ...)
     * @param pieceAttributes the attributes of the piece (distance, value, ...)
     * @param player the name of the player owning the piece
     * @param movementPattern the movement pattern of the piece (omni, diagonal, linear, or orthogonal)
     */
    public EscapePieceImpl(PieceName pieceName, PieceAttribute[] pieceAttributes, String player, MovementPattern movementPattern){
        this.pieceName = pieceName;
        this.pieceAttributes = pieceAttributes;
        this.player = player;
        this.movementPattern = movementPattern;
        this.value = 1;
        this.movementAttributes = new HashSet<>();
        setValue();
        setMovementAttributes();
    }

    @Override
    public PieceName getName()
    {
        return this.pieceName;
    }

    /**
     * @return the owning player
     */
    @Override
    public String getPlayer()
    {
        return this.player;
    }

    public PieceAttribute[] getAttribute(){
        return this.pieceAttributes;
    }
    public MovementPattern getMovementPattern() {return this.movementPattern;}
    
    @Override
    public String toString(){
        return this.pieceName.toString();
    }

    public void setValue(){
        for(PieceAttribute attribute: pieceAttributes){
            if(attribute.getId() == PieceAttributeID.VALUE){
                this.value = attribute.getValue();
            }
        }
    }
    public void changeValue(int value){
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

    public void setMovementAttributes(){
        for(PieceAttribute attribute: pieceAttributes){
            if(attribute.getId() != PieceAttributeID.DISTANCE && attribute.getId() != PieceAttributeID.VALUE && attribute.getId() != null){
                movementAttributes.add(attribute.getId());
            }
        }
    }

    public int getDistance(){
        for(PieceAttribute attribute: this.getAttribute()){
            if(attribute.getId() == EscapePiece.PieceAttributeID.DISTANCE){
                return attribute.getValue();
            }
        }
        return 1;
    }

    public Set<PieceAttributeID> getMovementAttributes(){
        return this.movementAttributes;
    }

}
