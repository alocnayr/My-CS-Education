/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Copyright ©2016-2023 Gary F. Pollice
 *******************************************************************************/

package escape.builder;

import econfig.*;
import escape.*;
import escape.impl.boardinformation.board.BoardInformation;
import escape.impl.boardinformation.board.Bounds;
import escape.impl.boardinformation.coordinate.CoordinateImpl;
import escape.impl.boardinformation.playerandpiece.EscapePieceImpl;
import escape.impl.boardinformation.playerandpiece.PlayerInformation;
import escape.impl.gamemanager.EscapeGameManagerImpl;
import escape.required.*;
import org.antlr.v4.runtime.*;
import escape.required.Coordinate.*;

import javax.xml.bind.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class builds an instance of an EscapeGameManager from a configuration
 * file (.egc). This uses the EscapeConfigurator for XML to turn the .egc file
 * into a valid XML string that is then unmarshalled into an EscapeGameInitializer
 * file.
 *
 * The Escape Configuration Tool JAR file is required for this class to compile and
 * run correctly. The file also requires the ANTLR 4 JAR file. Both of these JAR files are
 * in the lib directory of this IntelliJ project.
 *
 * NOTE: The Escape Configuration Tool was built with Java 17.
 * 
 * MODIFIABLE: YES
 * MOVEABLE: NO
 * REQUIRED: YES
 * 
 * You must change this class to be able to get the data from the configurtion
 * file and implement the makeGameManager() method. You may not change the signature
 * of that method or the constructor for this class. You can change the file any 
 * other way that you need to.
 * 
 * You don't have to use the EscapeGameInitializer object if
 * you have a way that better suits your design and capabilities. Don't go down
 * a rathole, however, in order to use something different. This implementation
 * works and will not take much time to modify the EscapeGameInitializer to create
 * your game instance. Just creating the game instance should take as little time
 * as possible to implement.
 */
public class EscapeGameBuilder
{
    private final EscapeGameInitializer gameInitializer;
    
    /**
     * The constructor takes a file that points to the Escape game
     * configuration file. It should get the necessary information 
     * to be ready to create the game manager specified by the configuration
     * file and other configuration files that it links to.
     * @param fileName the file for the Escape game configuration file (.egc).
     * @throws Exception on any errors
     */
    public EscapeGameBuilder(String fileName) throws Exception
    {
    	String xmlConfiguration = getXmlConfiguration(fileName);
    	// Uncomment the next instruction if you want to see the XML
    	// System.err.println(xmlConfiguration);
        gameInitializer = unmarshalXml(xmlConfiguration);
    }

	/**
	 * Take the .egc file contents and turn it into XML.
	 * @param fileName the input configuration (.egc) file
	 * @return the XML data needed to 
	 * @throws IOException
	 */
	private String getXmlConfiguration(String fileName) throws IOException
	{
		EscapeConfigurator configurator = new EscapeConfigurator();
    	return configurator.makeConfiguration(CharStreams.fromFileName(fileName));
	}

	/**
	 * Unmarshal the XML into an EscapeGameInitializer object.
	 * @param xmlConfiguration
	 * @throws JAXBException
	 */
	private EscapeGameInitializer unmarshalXml(String xmlConfiguration) throws JAXBException
	{
		JAXBContext contextObj = JAXBContext.newInstance(EscapeGameInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        return (EscapeGameInitializer)mub.unmarshal(
            	new StreamSource(new StringReader(xmlConfiguration)));
	}
	
	/**
	 * Getter for the gameInitializer. Can be used to examine it after the builder
	 * creates it.
	 * @return the gameInitializer
	 */
	public EscapeGameInitializer getGameInitializer()
	{
		return gameInitializer;
	}
    
    /***********************************************************************
     * Once the EscapeGameIniializer is constructed, this method creates the
     * EscapeGameManager instance. You use the gameInitializer object to get
	 * all of the information you need to create your game.
     * @return the game instance
     ***********************************************************************/
    public EscapeGameManager makeGameManager()
    {

		BoardInformation board = new BoardInformation(getGameBounds(), getPieceLocations(), getBlockExitLocations(), getCoordinateType(), getPointConflict());
		PlayerInformation playerInformation = new PlayerInformation(getPlayers(), getPlayers()[0], 0, 0);

		EscapeGameManagerImpl manager = new EscapeGameManagerImpl(board, playerInformation, getRules());

		return manager;
    }

	/**
	 * Gets the rules
	 * @return a map of the ruleID and value from the gameInitializer
	 */
	private Map<Rule.RuleID, Integer> getRules(){

		RuleDescriptor[] initializerRules = gameInitializer.getRules();
		Map<Rule.RuleID, Integer> rules = new HashMap<>();
		if(initializerRules != null) {
			for (RuleDescriptor ruleDescriptor : initializerRules) {
				Rule.RuleID id = ruleDescriptor.getID();
				int value = ruleDescriptor.getRuleValue();
				rules.put(id, value);
			}
		}
		return rules;
	}

	/**
	 * Gets the piece locations
	 * @return a map linking the pieces and their locations
	 */
	private Map<Coordinate, EscapePiece> getPieceLocations() {
		Map<Coordinate, EscapePiece> pieceLocations = new HashMap<>();
		for (int i = 0; i < gameInitializer.getLocationInitializers().length; i++) {
			initializePiece(pieceLocations, gameInitializer.getLocationInitializers()[i]);
		}
		return pieceLocations;
	}

	/**
	 * @return true if the exists the point conflict rule
	 */
	private boolean getPointConflict(){

		RuleDescriptor[] initializerRules = gameInitializer.getRules();
		if(initializerRules != null) {
			for (RuleDescriptor ruleDescriptor : initializerRules) {
				if(ruleDescriptor.getID() == Rule.RuleID.POINT_CONFLICT){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the block exit locations
	 * @return a map containing the block and exit LocationTypes and their location
	 */
	private Map<Coordinate, LocationType> getBlockExitLocations() {
		Map<Coordinate, LocationType> blockExitLocations = new HashMap<>();
		for (int i = 0; i < gameInitializer.getLocationInitializers().length; i++) {
			initializeLocation(blockExitLocations, gameInitializer.getLocationInitializers()[i]);
		}
		return blockExitLocations;
	}

	/**
	 * Initializes the block and exit location
	 * @param blockExitLocations a map containing the block and exit LocationTypes and their location
	 * @param initializer the LocationInitializer
	 */
	private void initializeLocation(Map<Coordinate, LocationType> blockExitLocations, LocationInitializer initializer) {
		LocationType locationType = initializer.getLocationType();
		int x = initializer.getX();
		int y = initializer.getY();
		Coordinate coordinate = new CoordinateImpl(x, y);
		blockExitLocations.put(coordinate, locationType);
	}

	/**
	 * Initializes the pieces and their locations
	 * @param pieceLocations a map of the pieces and their locations
	 * @param initializer the LocationInitializer
	 */
	private void initializePiece(Map<Coordinate, EscapePiece> pieceLocations, LocationInitializer initializer) {
		EscapePiece.PieceName pieceName = initializer.getPieceName();
		String playerName = initializer.getPlayer();
		if (pieceName != null && playerName != null) {
			int x = initializer.getX();
			int y = initializer.getY();
			Coordinate pieceLocation = new CoordinateImpl(x, y);
			PieceTypeDescriptor[] descriptors = gameInitializer.getPieceTypes();
			EscapePiece.MovementPattern movementPattern = null;
			PieceAttribute[] attributes = null;
			for (PieceTypeDescriptor descriptor : descriptors) {
				if (descriptor.getPieceName() == pieceName) {
					movementPattern = descriptor.getMovementPattern();
					attributes = descriptor.getAttributes();
					break;
				}
			}
			EscapePiece newPiece = new EscapePieceImpl(pieceName, attributes, playerName, movementPattern);
			pieceLocations.put(pieceLocation, newPiece);
		}
	}


	private Bounds getGameBounds(){
		int xMax = gameInitializer.getxMax();
		int yMax = gameInitializer.getyMax();
		return new Bounds(xMax, yMax);
	}

	private CoordinateType getCoordinateType(){
		return gameInitializer.getCoordinateType();
	}

	private String[] getPlayers(){
		return gameInitializer.getPlayers();
	}
}
