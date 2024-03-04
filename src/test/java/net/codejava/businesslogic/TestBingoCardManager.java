/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package net.codejava.businesslogic;

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
public class TestBingoCardManager {
    
    public TestBingoCardManager() {
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
    // Tests the card generated from random numbers. 
    public void testGenerateCardRandom() {
        BingoCardManager manager = new BingoCardManager();
        BingoCard card = manager.generateCard();
        int[] numbers = card.getCardNumbers();
        
        // Since it's random, we can only verify the length of the array
        assertTrue(numbers.length==25, "Invalid length of number array.");
        
        // Verify first number is not repeated.
        // Since it's random, it might.  If so, generate a third time
        card = manager.generateCard();
        int[] numbers2 = card.getCardNumbers();
        if (numbers2[0] == numbers[0]) {
            card = manager.generateCard();
            numbers2 = card.getCardNumbers();
        }
        assertTrue(numbers2[0] != numbers[0], "First element shouldn't match.");
    }

    @Test
    // Tests the card generated doesn't have marked numbers. 
    public void testGenerateCardUnmarked() {
        BingoCardManager manager = new BingoCardManager();
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
    
    @Test
    public void testGenerateCardSpecified() {
        BingoCardManager manager = new BingoCardManager();
        manager.setTestCard("10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70");
        BingoCard card = manager.generateCard();
        int[] numbers = card.getCardNumbers();
        int[] expectedNumbers = new int[] {10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70};
        assertArrayEquals(expectedNumbers, numbers, "Did not assign card numbers as expected");
        card = manager.generateCard();
        numbers = card.getCardNumbers();
        assertTrue((numbers[0]!=10 && numbers[1]!=9), "Card should be from test string");
    }
}
