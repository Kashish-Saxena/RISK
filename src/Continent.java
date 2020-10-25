import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Continent class represents a continent in the world map. It
 * contains a method to get the continent associated with an input territory.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version October 25, 2020
 */
public class Continent {

    private String name;
    private List<Territory> territories;
    private static Map<Territory, Continent> territoryContinentMap = new HashMap<>();

    /**
     * Constructor of the Continent class. It initializes the field values and maps
     * the continent to all the territories in the list of territories.
     * @param name Name of the continent.
     * @param territories A list of territories associated with the continent.
     */
    public Continent(String name, List<Territory> territories){
        this.name = name;
        this.territories = territories;
        for (Territory t : territories) {
            territoryContinentMap.put(t, this);
        }
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
     * Returns a continent from the territory-continent map given an input territory.
     * @param childTerritory The input territory to get the associated continent.
     * @return The continent associated with the input territory.
     */
    public static Continent getContinentFromTerritory(Territory childTerritory) {
        return territoryContinentMap.get(childTerritory);
    }
}
