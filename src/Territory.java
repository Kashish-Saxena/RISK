import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Territory class represents a location in a continent. It includes the
 * number of armies in the territory and the owner of the territory. It also
 * contains methods to get adjacent territories and adjacent enemy territories
 * and to manipulate the number of armies in the territory.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version October 25, 2020
 */
public class Territory {

    private String name;
    private Player owner;
    private int numArmies;
    private List<Territory> adjacentTerritories;
    private static Map<String, Territory> territoryMap = new HashMap<>();

    /**
     * Constructor of the Territory class. It initializes the field values and adds
     * the territory to the territory map.
     */
    public Territory(String name){
        this.name = name;
        this.numArmies = 0;
        this.adjacentTerritories = new ArrayList<>();
        territoryMap.put(name.toLowerCase(), this);
    }

    /**
     * Returns the name of the territory.
     * @return The name of the territory.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the string representation of the name of the territory along with the number
     * of armies present in the territory.
     * @return The string representation of the territory.
     */
    public String toString(){
        //does not include Player or Continent so we can choose which to group Territories by
        return name + ": " + numArmies + " armies";
    }

    /**
     * Sets the owner of the territory.
     * @param owner A player that is the owner of the territory
     */
    public void setOwner(Player owner){
        this.owner = owner;
    }

    /**
     * Returns the owner of the territory.
     * @return A player that is the owner of the territory.
     */
    public Player getOwner(){
        return this.owner;
    }

    /**
     * Sets the number of armies in the territory.
     * @param numArmies The updated number of armies in the territory.
     */
    public void setArmies(int numArmies){
        this.numArmies = numArmies;
    }

    /**
     * Subtracts the input number of armies from the current number of armies
     * in the territory.
     *
     * @param numArmies The number of armies to be removed.
     */
    public void subtractArmies(int numArmies){
        this.numArmies -= numArmies;
    }

    /**
     * Adds the input number of armies to the current number of armies
     * in the territory.
     *
     * @param numArmies The number of armies to be added.
     */
    public void addArmies(int numArmies){
        this.numArmies += numArmies;
    }

    /**
     * Returns the number of armies in the territory.
     * @return the number of armies in the territory.
     */
    public int getArmies(){
        return this.numArmies;
    }

    /**
     * Returns a list of territories that are adjacent to this territory.
     * @return A list of adjacent territories
     */
    public List<Territory> getAdjacentTerritories(){
        return this.adjacentTerritories;
    }

    /**
     * Returns a list of enemy territories that are adjacent to this territory.
     * @return A list of adjacent enemy territories.
     */
    public List<Territory> getAdjacentEnemyTerritories(){
        ArrayList<Territory> adjacentEnemyTerritories = new ArrayList<>();
        for (Territory adj : this.adjacentTerritories) {
            if (adj.getOwner() != this.owner) {
                adjacentEnemyTerritories.add(adj);
            }
        }

        return adjacentEnemyTerritories;
    }

    /**
     * Updates the list of adjacent territories.
     * @param adjacentTerritories The new list of adjacent territories.
     */
    public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
        this.adjacentTerritories = adjacentTerritories;
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
