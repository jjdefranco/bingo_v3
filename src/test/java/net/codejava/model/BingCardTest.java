/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package net.codejava.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author joede
 */
public class BingCardTest {
    
    public BingCardTest() {
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
    public void testBingoCardGetterAndSetter()  {
        BingoCard card = new BingoCard();
        int[] myint = new int[]{25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
        boolean[] mybool = new boolean[]{true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true};
        card.setCardNumbers(myint);
        card.setMarkedNumbers(mybool);
        int[] actualint = card.getCardNumbers();
        boolean[] actualbool = card.getMarkedNumbers();
        for (int i=0; i<25; i++)  {
            assertTrue(myint[i]==actualint[i], "Invalid integer at spot " + i);
            assertTrue(mybool[i]==actualbool[i], "Invalid boolean at spot " + i);
        }
    }
            
    @Test
    public void testBingoCardReset()  {
        BingoCard card = new BingoCard();
        int[] myint = new int[]{25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
        boolean[] mybool = new boolean[]{true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true,false,true};
        card.setCardNumbers(myint);
        card.setMarkedNumbers(mybool);
        card.reset();
        int[] actualint = card.getCardNumbers();
        boolean[] actualbool = card.getMarkedNumbers();
        for (int i=0; i<25; i++)  {
            assertTrue(myint[i]==actualint[i], "Invalid integer at spot " + i);
            assertTrue(actualbool[i]==false, "Invalid boolean at spot " + i);
        }
        
    }
            
}
