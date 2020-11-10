import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Continent class represents a continent in the world map. It
 * contains a method to get the continent associated with an input territory.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 9, 2020
 */
public class Continent {

    private String name;
    private List<Territory> territories;
    private int xPos;
    private int yPos;
    private Color color;
    //private static Map<Territory, Continent> territoryContinentMap = new HashMap<>();

    /**
     * Constructor of the Continent class. It initializes the field values and maps
     * the continent to all the territories in the list of territories.
     * @param name Name of the continent.
     * @param territories A list of territories associated with the continent.
     */
    public Continent(String name, List<Territory> territories, int xPos, int yPos, Color color){
        this.name = name;
        this.territories = territories;
        this.xPos = xPos;
        this.yPos = yPos;
        this.color = color;
//        for (Territory t : territories) {
//            territoryContinentMap.put(t, this);
//        }
    }

    /**
     * Returns a string representation of the continent.
     * @return Name of the continent as a string.
     */
    public String toString(){
        return this.name;
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

//    /**
//     * Returns a continent from the territory-continent map given an input territory.
//     * @param childTerritory The input territory to get the associated continent.
//     * @return The continent associated with the input territory.
//     */
//    public static Continent getContinentFromTerritory(Territory childTerritory) {
//        return territoryContinentMap.get(childTerritory);
//    }
}
