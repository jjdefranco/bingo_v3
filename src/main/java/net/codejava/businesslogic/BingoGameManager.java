/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.businesslogic;

import java.util.ArrayList;
import java.util.Random;
import net.codejava.model.BingoCard;

/**
 * Contains the full logic of the entire Bingo game.<p> 
 * Tracks the state of the game and prevents any calls to other members unless 
 * the game is in the correct state.
 * @author joe_d
 */
public class BingoGameManager {
    
    private ArrayList<Integer> calledNumbers;
    final Random randomNumberGenerator = new Random();
    public enum GameState { READY, PLAYING };
    private GameState gameState = GameState.READY;

    public GameState getGameState() {
        return gameState;
    }
    
    final private BingoCardManager cardManager = new BingoCardManager();
    private int[] testBingoNumbers = null;
    private int testBingoNumberIndex = 0;

    /**
     * Moves the game from the READY state to the PLAYING state.<br>
     * Clears any called numbers from a previous game. 
     * @throws Exception if the game was already in the READY state. 
     */    
    public void startGame() throws Exception  {
        if (gameState != GameState.READY)
            throw new Exception("Canot start game when game is already in progress");
        gameState = GameState.PLAYING;
        calledNumbers = new ArrayList<>();
        testBingoNumberIndex = 0;
        testBingoNumbers = null;
    }
    
    /**
     * Moves the game into the READY state.<br>  
     * Does not check on existing state because a client may need to restart
     * the game from a fresh browser refresh.
     */
    public void quitGame()  {
        gameState = GameState.READY;
    }

    /**
     * Generates a new {@link BingoCard}.  Can only be called if the game is in the READY state.
     * @return the new BingoCard
     * @throws Exception if the game is not in the READY state. 
     */
    public BingoCard generateCard() throws Exception  {
        if (gameState != GameState.READY)
            throw new Exception("Canot generate Card when game has already started");
        BingoCard card = cardManager.generateCard();
        return card;
    }

    /**
     * Generates the next valid number (1-75) that has not already been called.<br>
     * If the system has been supplied a list of test numbers, it will use the next number
     * from that list.  Otherwise, it will be generated randomly. 
     * @return the next valid number
     * @throws Exception if the game is not in the PLAYING state. 
     */
    public int generateNumber() throws Exception  {
        int nextNumber;
        
        if (gameState != GameState.PLAYING)
            throw new Exception("Canot generate Number when game has not started");
        
        // Use test numbers if they are provided
        if (testBingoNumbers != null && testBingoNumberIndex < testBingoNumbers.length) {
            nextNumber = testBingoNumbers[testBingoNumberIndex++];
            if (testBingoNumberIndex >= testBingoNumbers.length) {
                testBingoNumberIndex = 0;
                testBingoNumbers = null;
            }
        }
        
        // Otherwise, generate a random valid number
        else
            nextNumber = this.generateRandomNumber();
        
        // Keep track of called numbers
        calledNumbers.add(nextNumber);
        return nextNumber;
    }
    
    /**
     * Generates a random number.
     * @return the next number
     */
    private int generateRandomNumber() {

        boolean foundOne = false;
        int nextNumber = 0;
        while (!foundOne) {
            nextNumber = randomNumberGenerator.nextInt(1, 75);
            foundOne = !calledNumbers.contains(nextNumber);
        }
        return nextNumber;
    }

    public ArrayList<Integer> getCalledNumbers() {
        return calledNumbers;
    }
    
     
    /**
     * Used by testers to control the generated numbers of a test card. Once provided,
     * the supplied numbers will be used next time the card has been generated but only once.<p>
     * example:  "10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70"
     * @param testCardNumbers A list of 25 numbers, separated by a comma  
     */
    public void setTestCard(String testCardNumbers) {
        cardManager.setTestCard(testCardNumbers);
    }
    
     /**
     * Used by testers to control the numbers generated once the game has been set. Once provided,
     * the supplied numbers will be called next until the list has been completed.  
     * Once completed, the list will be discarded.<p>
     * example:  "10,9,8"
     * @param testCardNumbers A list of numbers, separated by a comma  
     */
    public void setNumberSequence(String testCardNumbers) {
        // Convert to array of integers
        if (testCardNumbers != null) {
            String[] arrOfStr = testCardNumbers.split(",");
            if (arrOfStr != null) {
                testBingoNumbers = new int[arrOfStr.length];
                for (int i=0; i<arrOfStr.length; i++)
                    testBingoNumbers[i] = Integer.parseInt(arrOfStr[i]);
                testBingoNumberIndex = 0;
            }
        }
    }
    
    public boolean validateBingo(BingoCard card) {
        if (calledNumbers==null || calledNumbers.size()<4)
            return false;
        
        // Check rows
        if (checkLine(card,0,5,10,15,20))
            return true;
        if (checkLine(card,1,6,11,16,21))
            return true;
        if (checkLine(card,2,7,12,17,22))
            return true;
        if (checkLine(card,3,8,13,18,23))
            return true;
        if (checkLine(card,4,9,14,19,24))
            return true;
        
        // Check columns
        if (checkLine(card,0,1,2,3,4))
            return true;
        if (checkLine(card,5,6,7,8,9))
            return true;
        if (checkLine(card,10,11,12,13,14))
            return true;
        if (checkLine(card,15,16,17,18,19))
            return true;
        if (checkLine(card,20,21,22,23,24))
            return true;
        
        // Check diagonals
        if (checkLine(card,0,6,12,18,24))
            return true;
        if (checkLine(card,4,8,12,16,20))
            return true;
        
        return false; 
    }
    
    private boolean checkLine(BingoCard card, int n1, int n2, int n3, int n4, int n5) {
        int[] numbers = card.getCardNumbers();
        boolean[] markedNumbers = card.getMarkedNumbers();
        // If middle number is a free marker, only check other numbers
        if (n3 == 12) {
        return (markedNumbers[n1] && markedNumbers[n2] && markedNumbers[n4] && markedNumbers[n5] &&
                calledNumbers.contains(numbers[n1]) && 
                calledNumbers.contains(numbers[n2]) && 
                calledNumbers.contains(numbers[n4]) && 
                calledNumbers.contains(numbers[n5]));
        }
        
        // Check all 5 numbers
        return (markedNumbers[n1] && markedNumbers[n2] && markedNumbers[n3] && markedNumbers[n4] && markedNumbers[n5] &&
                calledNumbers.contains(numbers[n1]) && 
                calledNumbers.contains(numbers[n2]) && 
                calledNumbers.contains(numbers[n3]) && 
                calledNumbers.contains(numbers[n4]) && 
                calledNumbers.contains(numbers[n5]));
    }
   
}
