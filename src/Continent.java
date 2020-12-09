import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * The Continent class represents a continent in the world map. It
 * contains a method to get the continent associated with an input territory.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 23, 2020
 */
public class Continent implements Serializable {

    private String name;
    private List<Territory> territories;
    private int xPos;
    private int yPos;
    private Color color;
    private int value; //how many extra armies player gets to place during deploy if they own the continent

    /**
     * Constructor of the Continent class. It initializes the field values and maps
     * the continent to all the territories in the list of territories.
     * @param name Name of the continent.

     * @param xPos x coordinate of the Continent.
     * @param yPos y coordinate of the Continent.
     * @param color Color of the Continent.
     * @param value Number of extra armies player gets to place during deploy if they own the continent.
     */
    public Continent(String name, int xPos, int yPos, Color color, int value){
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
        this.value = value;
    }

    /**
     * Returns a string representation of the continent.
     * @return Name of the continent as a string.
     */
    public String toString(){
        return this.name;
    }

    /**
     * Sets the territories of the continent.
     * * @param territories A list of territories associated with the continent.
     */
    public void setTerritories(List<Territory> territories){
        this.territories = territories;
    }

    /**
     * Returns a list of territories that are a part of the continent.
     * @return A list of associated territories.
     */
    public List<Territory> getTerritories(){
        return this.territories;
    }

    /**
     * Returns the x coordinate of the Continent.
     * @return The x coordinate of the Continent.
     */
    public int getXPos(){
        return this.xPos;
    }

    /**
     * Returns the y coordinate of the Continent.
     * @return The y coordinate of the Continent.
     */
    public int getYPos(){
        return this.yPos;
    }

    /**
     * Returns the color of the Continent.
     * @return The color of the Continent.
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Returns the number of extra armies player gets to place during deploy if they own the continent.
     * @return The number of extra armies player gets to place during deploy if they own the continent.
     */
    public int getValue() {
        return this.value;
    }
}
