var myApp = angular.module('spicyApp1',[]);

myApp.constant ('CONST', {
        'WELCOME_MESSAGE': 'Welcome to the Bingo Game!',
        'STATE_START': 'START',
        'STATE_PLAYING': 'PLAYING'
        });

myApp.controller('Player', ['CONST', '$scope', '$http', function(CONST, $scope, $http) {

   $scope.bingoCard = {};
   $scope.gameStatus = CONST.STATE_START;  // START or PLAYING
   $scope.message = CONST.WELCOME_MESSAGE;

    // Call the server to generate another bingo card
    $scope.generate = function() {
        console.log('Generate card');
        if ($scope.message !== CONST.WELCOME_MESSAGE)
            $scope.message = '';
        $http.get('http://localhost:8080/player/generate').
            then(function(response) {
                console.log(response.data);
                if (response.data !== undefined) {
                    if (response.data.valid)
                        $scope.bingoCard = response.data.data;
                    else
                        $scope.message = response.data.errorMessage;
                }
            });
    };

    // Call the server and let it know the game has started.
    // Change button state to show it's in playing mode
    $scope.startGame = function() {
        console.log('Start the game');
        $scope.message = '';
        $scope.gameStatus = CONST.STATE_PLAYING;
         $http.get('http://localhost:8080/player/startgame').
            then(function(response) {
                console.log(response.data);
                if (response.data !== undefined && !response.data.valid)
                    $scope.message = response.data.errorMessage;
            });
    };

    // Get the next number from server
    $scope.callNumber = function() {
        console.log('Call Number');
        $scope.message = '';
        $http.get('http://localhost:8080/player/nextnumber').
            then(function(response) {
                console.log(response.data);
                if (response.data !== undefined) {
                    if (!response.data.valid)
                        $scope.message = response.data.errorMessage;
                    else {
                        $scope.message = 'Next Number:  ' + $scope.formatNumber(response.data.data);
                    }
                }
            });
    };

    // Call the server and let it know the game has ended.
    // Change button state to show it's in start mode
    $scope.quit = function() {
        console.log('Quit Game');
        $scope.message = '';
        $scope.gameStatus = CONST.STATE_START;
        $http.post('http://localhost:8080/player/quitgame', $scope.bingoCard).
            then(function(response) {
                console.log(response.data);
                if (response.data !== undefined && !response.data.valid)
                    $scope.message = response.data.errorMessage;
                else
                    $scope.bingoCard = response.data.data;
                
            }, function(error) {
                alert(error.message);
            });
    };

    // When refreshing the page, it starts in READY state but server
    // may be in PLAYING STATE.  This will put the server in READY
    // state and then generate a card, which has to be done synchronously
    // Change button state to show it's in start mode
    $scope.quitAndGenerate = function() {
        console.log('Quit Game');
        $scope.gameStatus = CONST.STATE_START;
        $http.post('http://localhost:8080/player/quitgame',{}).
            then(function(response) {
                console.log(response.data);
                if (response.data !== undefined && !response.data.valid) 
                    $scope.message = response.data.errorMessage;
                else  {
                    $scope.generate();
                }
                
            }, function(error) {
                alert(error.message);
            });
    };

    // Call the server and let it know the player has reached bingo.
    $scope.bingo = function() {
        console.log('Call Bingo');
        $http.post('http://localhost:8080/player/bingo',$scope.bingoCard).
            then(function(response) {
                console.log(response.data);
                if (response.data !== undefined && !response.data.valid) 
                    $scope.message = response.data.errorMessage;
                else  {
                    $scope.message = 'Congratulations, you win!!';
                }
            }, function(error) {
                alert(error.data.error);
            });
    };
    
    // Update the server with the latest card state
    // This will trigger the visual of the card (see setNumberClass())
    $scope.markNumber = function(index) {
        console.log('Mark Number: ' + index);
        if ($scope.gameStatus !== CONST.STATE_START && index >= 0 && index <= 24 && index !== 12){
            $scope.bingoCard.markedNumbers[index] =  true;
        }
    };
 
    // Change visual of the card after a number is selected
    // by changing the element's class
    $scope.setNumberClass = function(index) {
        if ($scope.bingoCard !== undefined && index >= 0 && index <= 24 && index !==12){
            return $scope.bingoCard.markedNumbers[index] ? 'selectedNumber' : 'unselectedNumber';
        }
        return "";
    };
 
    // Change visual of the buttons based on game status
    // by changing the element's class
    $scope.setButtonClass = function(buttonIndex) {
        if ($scope.gameStatus === 'START' && (buttonIndex === 1 || buttonIndex === 2))
                return 'btnEnabled';
        else if ($scope.gameStatus === 'PLAYING' && (buttonIndex === 3 || buttonIndex === 4 || buttonIndex === 5))
                return 'btnEnabled';
        return 'btnDisabled';
    };

    $scope.httpCall = function(url) {
        $http.get('http://localhost:8080/player/generate').
            then(function(response) {
                $scope.rawResponse = response.data;
                return response.data;
            });
    };
    
    $scope.formatNumber = function(number) {
        var letterArr = ['B','I','N','G','O' ];
        var letter = Math.floor(number/15);
        if (letter >= 0 && letter <= 4)
            return letterArr[letter] + '-' + number;
        return '?'+number;
        
    };
    
    // Put game in initial state and generate initial card.
    // Because the calls are asynch, they must be combined into 1
    $scope.bingoCard = $scope.quitAndGenerate();
      
}]);



