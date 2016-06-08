
import javax.swing.JButton;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maria
 */
public class GameEngine {

    // Fields of class GameEngine
    private static GameEngine instance = null;
    // gameStatus = 0 := Game has not started
    // gameStatus = 1 := Game is in progress
    // gameStatus = 2 := GameOver Lost
    // gameStatus = 3 := GameOver Won

    /* Constructor for class GameEngine.
     * Constructor is private as only one instance of objects of class GameEngine
     * should exist for each game.
     */
    private GameEngine(){}

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    // Returns the status of the game
    // gameStatus = 0 := Game has not started
    // gameStatus = 1 := Game is in progress
    // gameStatus = 2 := GameOver Lost
    // gameStatus = 3 := GameOver Won
    public int gameStatus(Mines mines,JButton[][] buttonMatrix, int noOfMines) {

        int gameStatus = 0;
        if (mines == null){
            return gameStatus = 0;  // Game has not started. Mines don't exist yet.
        }

        boolean haveNotLostYet = true;
        for (int i = 0; i < buttonMatrix.length; i++) {
            for (int j = 0; j < buttonMatrix.length; j++) {

                int x = java.lang.Integer.parseInt(buttonMatrix[i][j].getActionCommand().substring(0, 1));
                int y = java.lang.Integer.parseInt(buttonMatrix[i][j].getActionCommand().substring(1));
                if (mines.isMine(x, y) && !buttonMatrix[i][j].isEnabled()) {
                    haveNotLostYet = false;
                }
            }
        }
        if (haveNotLostYet == false) {
            return gameStatus = 2;    // Game is lost
        }

        int totalNumberOfNonMinedButtons = (buttonMatrix.length * buttonMatrix.length) - noOfMines;
        int countRevealedNonMinedButtons = 0;
        for (int i = 0; i < buttonMatrix.length; i++) {
            for (int j = 0; j < buttonMatrix.length; j++) {

                int x = java.lang.Integer.parseInt(buttonMatrix[i][j].getActionCommand().substring(0, 1));
                int y = java.lang.Integer.parseInt(buttonMatrix[i][j].getActionCommand().substring(1));

                if (!mines.isMine(x, y) && !buttonMatrix[i][j].isEnabled()) {
                    countRevealedNonMinedButtons++;
                }
            }
        }
        if (countRevealedNonMinedButtons == totalNumberOfNonMinedButtons) {
            return gameStatus = 3;   // Game won
        } else {
            return gameStatus = 1;   // Game is in progress
        }
    }
}
