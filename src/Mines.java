
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maria
 */
public class Mines {

    // Fileds of class Mines
    private int sizeOfMatrix;
    private int noOfMines;
    private ArrayList<int[]> mineCoordinates;   // The ArrayList will have a length of noOfMines

    // Constructor for class Mines
    public Mines(int sizeOfMatrix, int noOfMines) {

        this.sizeOfMatrix = sizeOfMatrix;
        this.noOfMines = noOfMines;
        this.mineCoordinates = new ArrayList<int[]>();
        
        // Generate UNIQUE random coordinates for mines excluding coordinatesExculedFromBegging
        int[] newMine = this.generateRandomCoordinates();
        int counter = 0;
        while (counter < this.noOfMines) {
            if (!this.contains(this.mineCoordinates, newMine)) {

                this.mineCoordinates.add(newMine);
                counter++;
                newMine = this.generateRandomCoordinates();
            }
            newMine = this.generateRandomCoordinates();
        }
    }

    // --------------------- Methods -------------------------------------------

    // This is for constructor.
    // generateRandomCoordinates: Generate random pairs of coordinates.
    // These are likely to be mines.
    private int[] generateRandomCoordinates() {
        int[] coordinates = new int[2];
        Random random = new Random();
        coordinates[0] = random.nextInt(this.sizeOfMatrix);
        coordinates[1] = random.nextInt(this.sizeOfMatrix);
        return coordinates;
    }

    // This is for constructor.
    // Checks whether newMine is in coordinatesExcluded.
    // (I cannot use the regular method ArrayList.contains for my case so I create mine.)
    private boolean contains(ArrayList<int[]> coordinatesExcluded, int[] newMine) {

        boolean contains = false;
        for (int i = 0; i < coordinatesExcluded.size(); i++) {
            int[] mine = coordinatesExcluded.get(i);
            if (mine[0] == newMine[0] && mine[1] == newMine[1]) {
                contains = true;
            }
        }
        return contains;
    }
    /* isInBounds: Checks whether the coordinates i,j are in bounds for a specified
     * size of a square matrix.
     * Comment: Ek prwtis fainetai to orisma sizeOfMatrix tis methodou na einai peritto
     * afou iparxei kai san pedio tis klassis, alla sti pragmatikotita tha ksanakalesw ti
     * methodo kai stin ShowButtonContentActionListener kai xreiazetai na to dilwsw rita.
     */
    public boolean isInBounds(int i, int j, int sizeOfMatrix) {
        return (i >= 0 && i < sizeOfMatrix && j >= 0 && j < sizeOfMatrix);
    }

    // This method is also used by ShowButtonContentActionListener
    // isMine: Checks whether the coordinates i,j is a mine.
    public boolean isMine(int i, int j) {
        int[] u = {i, j};
        return (this.contains(this.mineCoordinates, u));
    }
}



