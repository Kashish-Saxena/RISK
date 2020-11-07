import java.util.*;

public class Map {

    private static java.util.Map<String, Territory> territoryMap = new HashMap<>();
    private static java.util.Map<Territory, Continent> territoryContinentMap = new HashMap<>();

    public Map() {

    }

    /**
     * Instantiate territories, continents and set adjacent territories connections.
     */
    public void createMap(){

    }

    /**
     * Returns a territory from an input string representation of a territory.
     * @param input A string representation of a territory.
     * @return A territory associated with the input territory.
     */
    public static Territory getTerritoryFromString(String input) {
        return territoryMap.get(input);
    }

    /**
     *  Returns a territory from the territory map given an index.
     * @param index The input index required to get a territory from the territory map.
     * @return A territory from the territory map.
     */
    public static Territory getTerritoryFromIndex(int index) {
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(territoryMap.keySet());
        return territoryMap.get(keys.get(index));
    }

    /**
     * Returns the number of territories in the territory map.
     * @return The size of the territory map.
     */
    public static int numTerritories() {
        return territoryMap.size();
    }
}

