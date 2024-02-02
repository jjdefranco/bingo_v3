/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package net.codejava.businesslogic;

import java.util.ArrayList;
import net.codejava.model.BingoCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author joe_d
 */
public class TestBingoManager {
    
    public TestBingoManager() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    // Start the game from a READY state. 
    public void testStartGameGood() {
        BingoGameManager manager = new BingoGameManager();
        try {
            // Manager starts in a READY state.  No exception thrown
            manager.startGame();
            assertTrue(manager.getGameState()==BingoGameManager.GameState.PLAYING, "Wrong state after starting");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Start the game from a PLAYING state. 
    public void testStartGameBad() {
        BingoGameManager manager = new BingoGameManager();
        try {
            // Manager starts in a READY state.  No exception thrown
            manager.startGame();
            // Next call will throw exception
            assertThrows(Exception.class, ()->{manager.startGame();});
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Quit can happen any time.  
    public void testQuit() {
        BingoGameManager manager = new BingoGameManager();
        try {
            manager.quitGame();
            assertTrue(manager.getGameState()==BingoGameManager.GameState.READY, "Wrong state after quiting 1");
            
            manager.startGame();
            manager.quitGame();
            assertTrue(manager.getGameState()==BingoGameManager.GameState.READY, "Wrong state after quiting 2");
       }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testGenerateCardRandom() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            int[] numbers = card.getCardNumbers();
            boolean[] markedNumbers = card.getMarkedNumbers();

            // Since it's random, we can only verify the length of the array
            assertTrue(numbers.length==25, "Invalid length of number array.");

            // No numbers should be marked.
            for (int i=0; i<25; i++)  {
                 assertTrue(markedNumbers[i]==false, "Invalid boolean at spot " + i);
            }
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }
    
    @Test
    public void testGenerateCardSpecified() {
        BingoGameManager manager = new BingoGameManager();
        manager.setTestCard("10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70");
        try {
            BingoCard card = manager.generateCard();
            int[] numbers = card.getCardNumbers();
            assertTrue(numbers[0]==10, "First element should have been 10");
            card = manager.generateCard();
            numbers = card.getCardNumbers();
            assertTrue((numbers[0]!=10 && numbers[1]!=9), "Card should be from test string");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    public void testGenerateNumberRandomAndSpecified() {
        BingoGameManager manager = new BingoGameManager();
        try {
            manager.setTestCard("10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70");
            BingoCard card = manager.generateCard();
            manager.startGame();
            manager.setNumberSequence("10,9");
            int  nextNumber = manager.generateNumber();
            assertTrue(nextNumber==10, "Invalid");
            nextNumber = manager.generateNumber();
            assertTrue(nextNumber==9, "Invalid");
            nextNumber = manager.generateNumber();
            assertTrue(nextNumber!=8, "Invalid");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
     }

    @Test
    // Tests that the game manager keeps track of the called numbers. 
    public void testCalledNumbers() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            manager.startGame();
            ArrayList<Integer> calledNumbers = manager.getCalledNumbers();
            assertTrue(calledNumbers!=null && calledNumbers.isEmpty(), "Length should be 0");
            int  num1 = manager.generateNumber();
            int  num2 = manager.generateNumber();
            int  num3 = manager.generateNumber();
            calledNumbers = manager.getCalledNumbers();
            assertTrue(calledNumbers!=null && calledNumbers.size()==3, "Length should be 3");
            assertTrue(num1==calledNumbers.get(0), "Invalid");
            assertTrue(num2==calledNumbers.get(1), "Invalid");
            assertTrue(num3==calledNumbers.get(2), "Invalid");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests valid bingo for first row. 
    public void testValidateBingoRow1() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 0,5,10,15,20), "Cannot Validate first row bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown: " + ex.getMessage());
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoRow2() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 1,6,11,16,21), "Cannot Validate second row bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoRow3() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 2,7,12,17,22), "Cannot Validate third row bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoRow4() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 3,8,13,18,23), "Cannot Validate fourth row bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoRow5() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 4,9,14,19,24), "Cannot Validate fifth row bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoCol1() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 0,1,2,3,4), "Cannot Validate first column bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoCol2() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 5,6,7,8,9), "Cannot Validate second column bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoCol3() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 10,11,12,13,14), "Cannot Validate third column bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoCol4() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 15,16,17,18,19), "Cannot Validate first column bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoCol5() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 20,21,22,23,24), "Cannot Validate fifth column bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoDiag1() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 0,6,12,18,24), "Cannot Validate first diagonal bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests the card generated from random numbers. 
    public void testValidateBingoDiag2() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            assertTrue(testCard(manager, card, 4,8,12,16,20), "Cannot Validate first diagonal bingo");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests you need the called numbers to match the marked line. 
    public void testInValidateBingoMissedCall() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
                int[] numbers = card.getCardNumbers();
            String s = String.format("%d,%d,%d,%d,%d", numbers[0],numbers[1],numbers[2],numbers[3],numbers[10]);
            
            // Start the game and call those numbers
            manager.startGame();
            manager.setNumberSequence(s);
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            
            // Mark the line on the card
            boolean[] markedNumbers = card.getMarkedNumbers();
            markedNumbers[0] = true;
            markedNumbers[1] = true;
            markedNumbers[2] = true;
            markedNumbers[3] = true;
            markedNumbers[4] = true;
            assertFalse(manager.validateBingo(card), "Should not have Bingo without called number");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests you need to mark the line and not just the called numbers. 
    public void testInValidateBingoMissedMark() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
                int[] numbers = card.getCardNumbers();
            String s = String.format("%d,%d,%d,%d,%d", numbers[0],numbers[1],numbers[2],numbers[3],numbers[5]);
            
            // Start the game and call those numbers
            manager.startGame();
            manager.setNumberSequence(s);
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            
            // Mark the line on the card
            boolean[] markedNumbers = card.getMarkedNumbers();
            markedNumbers[0] = true;
            markedNumbers[1] = true;
            markedNumbers[2] = true;
            markedNumbers[3] = true;
            markedNumbers[14] = true;
            assertFalse(manager.validateBingo(card), "Should not have Bingo without marked line");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    @Test
    // Tests you need to mark the line and not just the called numbers. 
    public void testInValidateBingoNoCalledorMarkedNumbers() {
        BingoGameManager manager = new BingoGameManager();
        try {
            BingoCard card = manager.generateCard();
            manager.startGame();
            assertFalse(manager.validateBingo(card), "Should not have Bingo without marked line");
        }
        catch (Exception ex)  {
            assertTrue(false, "Unexpected exception thrown");
        }
    }

    // Forces game to call the numbers provided and returns true if the bingo card 
    private Boolean testCard(BingoGameManager manager, BingoCard card, int n1, int n2, int n3, int n4, int n5) throws Exception {
        try {
            // Set the game up to call the requested line.
            int[] numbers = card.getCardNumbers();
            String s = String.format("%d,%d,%d,%d,%d", numbers[n1],numbers[n2],numbers[n3],numbers[n4],numbers[n5]);
            
            // Start the game and call those numbers
            manager.startGame();
            manager.setNumberSequence(s);
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            manager.generateNumber();
            
            // Mark the line on the card
            boolean[] markedNumbers = card.getMarkedNumbers();
            markedNumbers[n1] = true;
            markedNumbers[n2] = true;
            markedNumbers[n3] = true;
            markedNumbers[n4] = true;
            markedNumbers[n5] = true;
           
            // Validate the card
            return manager.validateBingo(card);
        }
        catch (Exception ex)  {
            throw ex;
        }
    }
}
