/*
 * Implementation of a DiceRoller.
 *
 * Copyright © 2023, Gary F. Pollice
 */
import java.util.Random;

//The purpose of this class is to roll any number of dice >= 1
//with any number of sides >= 2 and get their total face values
public class DiceRollerImpl implements DiceRoller {

	//need to keep track of number of dice there are and the number of sides
	//we can keep track of all values on each side and get the sum of all values based on number of sides
	//need to also keep track of whether the dice has been rolled
	//lets create variables for all of these and initialize them to some value

	private int totalNumberOfDice = -1;
	private int totalNumberOfDiceSides = -1;
	private boolean isDiceRolled = false;
	private int sumOfDiceSides = 0;
	private int[] allValuesOfDice;

	/**
	 * Only/default constructor
	 * Constructor to set values of private var and 
	 * for dice roller that takes in number of sides and number of sides
	 * @param numberOfSides - number of sides the dice has
	 * @param numberOfDice - number of dice we want to use
	 */
	public DiceRollerImpl(int numberOfSides, int numberOfDice) {

		//cant have less than 1 dice, throw an error
		if(numberOfDice < 1){
			throw new DiceException("invalid dice count");
		}

		//cant have a dice with less than 2 sides, throw an error
		if(numberOfSides < 2){
			throw new DiceException("invalid number of dice sides");
		}


		this.totalNumberOfDice = numberOfDice;
		this.totalNumberOfDiceSides = numberOfSides;

		//allocate size of array to keep track of all dice values with size = number of dice
		//since each dice is going to have one value per roll
		allValuesOfDice = new int[totalNumberOfDice];

	}

	@Override
	/**
	 * Method for rolling all dice 
	 * 
	 * @return the sum of the numbers that the dice landed on
	 */
	public int roll() {

		Random randValue = new Random();

		//iterate through all the dice and randomly generate a number from 1 to totalNumberOfDiceSides
		for(int i=0; i<totalNumberOfDice; i++){

			//get rand value from 1 - totalNumberOfDice and store that value in allValues array 
			allValuesOfDice[i] = randValue.nextInt(totalNumberOfDiceSides) + 1;
			sumOfDiceSides = sumOfDiceSides + allValuesOfDice[i];
		}

		isDiceRolled = true;

		return sumOfDiceSides;
	}

	@Override
	/**
	 * @return the sum of values of all dice rolled
	 */
	public int getDiceTotal() {
		//if the dice have been rolled, return sumOfDiceSides
		if(isDiceRolled){
			return sumOfDiceSides;
		}
		//if the dice haven't been rolled, return -1
		else{
			return -1;
		}
	}

	@Override
	/**
	 * @return the total number of dice
	 */
	public int getDiceCount() {
		return totalNumberOfDice;
	}

	@Override
	/**
	 * The purpose of this method is to return the value of a specific
	 * dice called dieNumber corresponding to the face it landed on
	 * when rolling. Throw an error in the cases where the dice hasn't been rolled
	 * or invalid dice number (< 1 or > totalNumerOfDice)
	 * 
	 * @param dieNumber - the number of the dice rolled
	 * @return the value on the selected die
	 * @throws DiceException if the die number is invalid or if the die was never rolled
	 */
	public int getDieValue(int dieNumber) throws DiceException {

		//errors thrown for invalid dieNumber or if the dice haven't been rolled
		if(dieNumber < 1){
			throw new DiceException("die number has be be greater than 1");
		}
		if(dieNumber > totalNumberOfDice){
			throw new DiceException("dieNumber exceeds total number of dice");
		}
		if(!isDiceRolled){
			throw new DiceException("dice have not been rolled");
		}
		return allValuesOfDice[dieNumber - 1];
	}

}
