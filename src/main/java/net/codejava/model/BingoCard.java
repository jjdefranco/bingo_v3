/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.model;

/**
 * A Bingo card has 2 components:<ul><li> the 25 numbers that represent the card </li>
 * <li>the state of each number of the card that indicate that number has been called or not.</li></ul>
 * The numbers of the card are represented of an array of 25 integers, with the first 5 being the numbers 
 * under the 'B', the next 5 are the numbers under the 'I', etc. The array should always have 25 numbers.<p>
 * The state of the numbers have been called are represented as an array of booleans, where the 
 * nth index of the array represents if the nth number has been called (true) or not (false)
 * @author joe_d
 */
public class BingoCard {
    private int[] cardNumbers;
    private boolean[] markedNumbers; 
    
    public BingoCard()
    {
        cardNumbers = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
        reset();
    }

    public boolean[] getMarkedNumbers() {
        return markedNumbers;
    }

    public void setMarkedNumbers(boolean[] markedNumbers) {
        this.markedNumbers = markedNumbers;
    }

    public int[] getCardNumbers() {
        return cardNumbers;
    }

    public void setCardNumbers(int[] cardNumbers) {
        this.cardNumbers = cardNumbers;
    }
    
    /**
     * Unmarks all numbers on the card.
     */
    public void reset()  {
        markedNumbers = new boolean[] { false,false,false,false,false,
                                        false,false,false,false,false,
                                        false,false,false,false,false,
                                        false,false,false,false,false,
                                        false,false,false,false,false};
        
    }
            
}
