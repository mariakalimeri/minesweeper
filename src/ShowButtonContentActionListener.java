
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maria
 */
public class ShowButtonContentActionListener extends MouseAdapter implements ActionListener {

    private Mines mines;
    private JButton[][] buttonMatrix;
    private int xCoordinate;     // xCoordinate of the button clicked
    private int yCoordinate;     // yCoordinate of the button clicked
    private ImageIcon mineIcon;
    private JLabel minesCountDown;
    private int noOfMines;
    

    public ShowButtonContentActionListener(Mines mines, JButton[][] buttonMatrix, JLabel minesCountDown, int noOfMines) {
        this.mines = mines;
        this.buttonMatrix = buttonMatrix;
        this.mineIcon = new ImageIcon("Bomb.png");
        this.minesCountDown = minesCountDown;
        this.noOfMines = noOfMines;
    }

    public void actionPerformed(ActionEvent event) {



        this.xCoordinate = java.lang.Integer.parseInt(event.getActionCommand().substring(0, 1));
        this.yCoordinate = java.lang.Integer.parseInt(event.getActionCommand().substring(1));


        if (this.mines.isMine(this.xCoordinate, this.yCoordinate)) {   // If it is mine, show all mines and unable all buttons


            for (int i = 0; i < this.buttonMatrix.length; i++) {
                for (int j = 0; j < this.buttonMatrix.length; j++) {

                    this.buttonMatrix[i][j].setEnabled(false);

                    int x = java.lang.Integer.parseInt(this.buttonMatrix[i][j].getActionCommand().substring(0, 1));
                    int y = java.lang.Integer.parseInt(this.buttonMatrix[i][j].getActionCommand().substring(1));

                    if (this.mines.isMine(x, y)) {
                        this.buttonMatrix[i][j].setIcon(this.mineIcon);
                        this.buttonMatrix[i][j].setBackground(Color.DARK_GRAY);
                    }
                }
            }
        } else if (this.calculateMinesAround(this.xCoordinate, this.yCoordinate) == 0) {  // If it has no mines around, open several
            this.revealNeighborButtons(this.xCoordinate, this.yCoordinate);
        
        } else {                                                                       // Else show buttonContent
            this.revealSingleButton(this.xCoordinate, this.yCoordinate);
        }
    }

    // For this class only.
    // calculateMinesAround: Calculate the No of mines around button i,j
    // This number will be reaveled when button i,j is clicked
    private int calculateMinesAround(int i, int j) {

        int minesAround = 0;
        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {

                int x = i + m;
                int y = j + n;

                if (this.mines.isInBounds(x, y, this.buttonMatrix.length)) {
                    if (this.mines.isMine(x, y)) {
                        minesAround++;
                    }
                }
            }
        }
        return minesAround;
    }

    // For this class only.
    // revealSingleButton: When a button is click that is not a mine
    // we see the content of the button and it is set unabled
    private void revealSingleButton(int i, int j) {

        int buttonContent = this.calculateMinesAround(i, j);
        //System.out.println("revealSingleButton -> Button has: " + buttonContent + "mines around");

        this.buttonMatrix[i][j].setEnabled(false);
        this.buttonMatrix[i][j].setBackground(Color.WHITE);

        if (buttonContent == 0) {
            this.buttonMatrix[i][j].setText(" ");
        } else {
            this.buttonMatrix[i][j].setText("" + buttonContent);
            this.buttonMatrix[i][j].setFont(new Font("sansserif", Font.BOLD, 30));
        }
    }

    // For this class only.
    // revealNeighborButtons: When i,j button is click and has no mines around
    // several neighbors will be revealed
    // This method calls itself so to reveal multiple empty buttons and buttons around them.
    private void revealNeighborButtons(int i, int j) {

        for (int m = -1; m <= 1; m++) {
            for (int n = -1; n <= 1; n++) {

                int x = i + m;
                int y = j + n;

                if (this.mines.isInBounds(x, y, this.buttonMatrix.length) && this.buttonMatrix[x][y].isEnabled()) {
                    this.revealSingleButton(x, y);
                    if (this.calculateMinesAround(x, y) == 0) {
                        //System.out.println("revealNeighBourButtons -> Empty cell around is: " + x + "" + y);
                        this.revealNeighborButtons(x, y);
                    }
                }
            }
        }
    }

     // Mouse right click  Listener
    public void mouseClicked(MouseEvent evt) {

        JButton button = (JButton) evt.getSource();
        if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0 && button.isEnabled()) {
            if (!button.getText().equals("!")) {
                button.setText("!");
                button.setFont(new Font("sansserif", Font.BOLD, 40));
                this.noOfMines--;
                this.minesCountDown.setText(this.noOfMines+ " Mines");
                this.minesCountDown.setFont(new Font("sansserif", Font.BOLD, 18));
            } else {
                button.setText("");
                this.noOfMines++;
                this.minesCountDown.setText(this.noOfMines+ " Mines");
                this.minesCountDown.setFont(new Font("sansserif", Font.BOLD, 18));
            }
        }
    }
}

