
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * MineSweeperGUI class is responsible for the graphical user interface of
 * the game
 */
/**
 * Tuesday 22/02/2011
 * @author maria
 */
public class GUI extends JFrame implements ActionListener {

    // Fields (Components) for objects of class MineSweeperGUI
    
    private int sizeOfMatrix;
    private int noOfMines;
    private JButton[][] buttonMatrix;
    private JButton exitButton;
    private Mines mines;
    private JLabel minesCountDown;

    // Constructor for objects of class MineSweeperGUI
    // Constructs a square matrix (sizeOfMatrix*sizeOfMatrix) of buttons and distributes
    // randomly, mines (#=noOfMines) around the matrix positions
    public GUI(int sizeOfMatrix, int noOfMines) {
        super();
        super.setTitle("Minesweeper");
        super.setSize(600, 600);
        super.setLocation(300, 100);

        // Initialize fields

        this.sizeOfMatrix = sizeOfMatrix;
        this.noOfMines = noOfMines;
        this.buttonMatrix = new JButton[this.sizeOfMatrix][this.sizeOfMatrix];
        this.exitButton = new JButton("Quit");
        this.mines = new Mines(this.sizeOfMatrix, this.noOfMines);
        this.minesCountDown = new JLabel(this.noOfMines+ " Mines");
        this.minesCountDown.setFont(new Font("sansserif", Font.BOLD, 18));
        //this.mines.printDetails();

        // Initialize buttons
        initializeButtons();

        // Create a panel for the main buttons of the game
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.white));
        buttonsPanel.setLayout(new GridLayout(this.sizeOfMatrix, this.sizeOfMatrix));
        // Add the main buttons of the game on the buttonsPanel
        for (int i = 0; i < this.sizeOfMatrix; i++) {
            for (int j = 0; j < this.sizeOfMatrix; j++) {
                buttonsPanel.add(buttonMatrix[i][j]);
            }
        }

        // Create a secondary panel for the exit button and add the exit button
        JPanel exitButtonPanel = new JPanel();
        exitButtonPanel.setLayout(new GridLayout(1, 9));
        //exitButtonPanel.setLayout(new BorderLayout());
        exitButtonPanel.setBorder(BorderFactory.createMatteBorder(10, 200, 20, 200, Color.white));
        exitButtonPanel.add(this.minesCountDown);
        exitButtonPanel.add(this.exitButton);

        // Create container and add the two panels
        Container content = super.getContentPane();
        content.add(buttonsPanel);
        content.add(exitButtonPanel, BorderLayout.SOUTH);

        super.setVisible(true);
    }

    // Methods
    private void initializeButtons() {

        // Construct buttons
        for (int i = 0; i < this.sizeOfMatrix; i++) {
            for (int j = 0; j < this.sizeOfMatrix; j++) {
                this.buttonMatrix[i][j] = new JButton();
            }
        }

        // Associate an actionCommand with each button.
        // The actionCommand will be the button coordinate in a String representation
        // Whenever I will be needing the int values of the coordinates, I will be using:
        // int x = java.lang.Integer.parseInt(button.getActionCommand().substring(0, 1)) and
        // int y = java.lang.Integer.parseInt(button.getActionCommand().substring(0))
        for (int i = 0; i < this.sizeOfMatrix; i++) {
            for (int j = 0; j < this.sizeOfMatrix; j++) {
                this.buttonMatrix[i][j].setActionCommand("" + i + "" + j);
            }
        }
        this.exitButton.setActionCommand("EXIT");


        // Associate an actionListener with each button.
        ActionListener actionListener = new ShowButtonContentActionListener(this.mines, this.buttonMatrix, this.minesCountDown, this.noOfMines);
        MouseAdapter mouseListener = new ShowButtonContentActionListener(this.mines, this.buttonMatrix, this.minesCountDown, this.noOfMines);
        for (int i = 0; i < this.sizeOfMatrix; i++) {
                for (int j = 0; j < this.sizeOfMatrix; j++) {
                    this.buttonMatrix[i][j].addActionListener(this);            // some sort of gameEmgine Listener
                    this.buttonMatrix[i][j].addActionListener(actionListener);  // ShowButtonContentActionListener
                    this.buttonMatrix[i][j].addMouseListener(mouseListener);    // Only for the right click and the flags
                }
            }
        this.exitButton.addActionListener(this);
    }

    // implement actionPerformed method of ActionListener
    // This is basically the gameEngine ActionListener.
    public void actionPerformed(ActionEvent event) {

        // Find the coordinates of the button clicked
        String command = event.getActionCommand();

        // Ask gameEngine what is the status
        GameEngine instance = GameEngine.getInstance();
        int gameStatus = instance.gameStatus(this.mines, this.buttonMatrix, this.noOfMines);
        //System.out.println("gameStatus is: "+gameStatus);

        if (command.equals("EXIT")) {
            System.exit(0);
        } if (gameStatus == 2) {  // If game is lost
            int answer = JOptionPane.showOptionDialog(this, "Sorry, you lost. Start new game?", "Game Over", JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (answer == JOptionPane.YES_OPTION) {
                this.dispose();
                GUI mineSweeper = new GUI(this.sizeOfMatrix, this.noOfMines);
            } else {
                System.exit(0);
            }

        } else if(gameStatus == 3) {   // If game is won
            int answer = JOptionPane.showOptionDialog(this, "Congratulations, you won! Play again?", "Game Over", JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, null, null);
            if (answer == JOptionPane.YES_OPTION) {
                this.dispose();
                GUI mineSweeper = new GUI(this.sizeOfMatrix, this.noOfMines);
            } else {
                System.exit(0);
            }
        }
        else if (gameStatus == 1){
            // The game is in progress. This actionListener does nothing.
        }
    }
}

