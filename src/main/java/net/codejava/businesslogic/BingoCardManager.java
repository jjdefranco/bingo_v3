/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.businesslogic;

import java.util.ArrayList;
import java.util.Random;
import net.codejava.model.BingoCard;

/**
 *  This manager contains all the business logic for the Bingo Card.
 * 
 * @author joe_d
 */
public class BingoCardManager {
    final Random randomNumberGenerator = new Random();
    String testCard = null;

    /**
     * Will generate a new random {@link BingoCard}. The card will not have any marked numbers.<p>
     * If the system has been provided with a test card via {@link setTestCard} method, it will 
     * use those numbers instead and then remove those test numbers.
     * 
     * @return the newly generated {@link BingoCard}
     */
    public BingoCard generateCard() {
        BingoCard bingoCard = new BingoCard();
        int[] newNumbers = generateRandomCard();
        bingoCard.setCardNumbers(newNumbers);
        return bingoCard;
    }
    
     /**
     * Used by testers to control the generated numbers of a test card. Once provided,
     * the supplied numbers will be used next time the card has been generated but only once.<p>
     * example:  "10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70"
     * @param testCardNumbers A list of 25 numbers, separated by a comma  
     */
    public void setTestCard(String testCardNumbers) {
        testCard = testCardNumbers;
    }
    
    /**
     * Generates a list of valid numbers, either randomly or using the last
     * test card provided.
     * @return an array of 25 integers 
     */
    private int[] generateRandomCard() {
        
        // Use testNumbers if they have been provided.
        // Only use them once
        if (testCard != null) {
            String[] arrOfStr = testCard.split(",", 25);
            if (arrOfStr != null && arrOfStr.length == 25) {
                int[] arr = new int[25];
                for (int i=0; i<25; i++)
                    arr[i] = Integer.parseInt(arrOfStr[i]);
                testCard = null;
                return arr;
            }
        }
        
        // Generate an array of 25 valid numbers randomly
        // Numbers must be unique.
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i=0; i< 5; i++) {// each letter
            int min = i*15+1;
            int max = min+15;
            for (int j=0; j< 5; j++) { // want 5 numbers per letter
                boolean foundOne = false;
                int nextNumber = 0;
                while (!foundOne) {
                    nextNumber = randomNumberGenerator.nextInt(min, max);
                    foundOne = !numbers.contains(nextNumber);
                }
                numbers.add(nextNumber);
            }
        }

        int[] numberArr = numbers.stream().mapToInt(Integer::intValue).toArray();
        return numberArr;
    }
     
}
