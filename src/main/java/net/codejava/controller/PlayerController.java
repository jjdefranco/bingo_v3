/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.controller;

import net.codejava.businesslogic.BingoGameManager;
import net.codejava.model.BingoCard;
import net.codejava.model.ResponsePackage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the Spring Boot controller.  It:
 * <ul><li>Passes through the request to the Business Layer. It has no game logic in this layer.</li>
 * <li>Formats the {@link ResponsePackage} based on if the call was successful and/or returned any data </li></ul>
 * 
 * @author joe_d
 */
@RestController
public class PlayerController {

        final private BingoGameManager gameManager = new BingoGameManager();
        
        @GetMapping("/player/startgame")
	public ResponsePackage startGame() {
            ResponsePackage response = new ResponsePackage();
            try {
                gameManager.startGame();
                response.setValid(true);
            }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
            return response;
	}

        @GetMapping("/player/generate")
	public ResponsePackage generateCard() {
            ResponsePackage response = new ResponsePackage();
            try {
                BingoCard card = gameManager.generateCard();
                response.setData(card);
                response.setValid(true);
           }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
                
            return response;
 	}

        @PostMapping("/player/bingo")
	public ResponsePackage callBingo(@RequestBody BingoCard card) {
            ResponsePackage response = new ResponsePackage();
            try {
                if (card == null)
                    throw new Exception("Unexpected null BingoCard.");
                if (!gameManager.validateBingo(card))
                    throw new Exception("Unfortunately, you do not have Bingo.");
                response.setValid(true);
            }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
            return response;
	}

        @PostMapping("/player/quitgame")
	public ResponsePackage quitGame(@RequestBody BingoCard card) {
            ResponsePackage response = new ResponsePackage();
            try {
                if (card != null)  {
                    card.reset();
                    response.setData(card);
                }
                gameManager.quitGame();
                response.setValid(true);
            }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
            return response;
	}

        @GetMapping("/player/nextnumber")
	public ResponsePackage nextNumber() {
            ResponsePackage response = new ResponsePackage();
            try {
                int nextNumber = gameManager.generateNumber();
                response.setData(nextNumber);
                response.setValid(true);
            }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
            return response;
	}

        /**
         * Used by testers to control what the next generated card will look like.
         * @param card a comma separate list of 25 valid bingo card numbers.<br>
         * example:  http://localhost:8080/player/settestcard?card=10,9,8,7,6,29,28,27,26,25,44,43,42,41,40,59,58,57,56,55,74,73,72,71,70
         * @return generic response package with valid set to true. 
         */
        @GetMapping("/player/settestcard")
	public ResponsePackage setTestCard(@RequestParam(value = "card") String card) {
            ResponsePackage response = new ResponsePackage();
            try {
                gameManager.setTestCard(card);
                response.setValid(true);
            }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
            return response;
	}

        /**
         * Used by testers to control what the next generated card will look like.
         * @param card a comma separate list of 25 valid bingo card numbers.<br>
         * example: http://localhost:8080/player/settestnumbers?card=10,9 
         * @return generic response package with valid set to true. 
         */
        @GetMapping("/player/settestnumbers")
	public ResponsePackage setTestNumbers(@RequestParam(value = "card") String card) {
            ResponsePackage response = new ResponsePackage();
            try {
                gameManager.setNumberSequence(card);
                response.setValid(true);
            }
            catch (Exception ex)
            {
                response.setValid(false);
                response.setErrorMessage(ex.getMessage());
            }
            return response;
	}

}
