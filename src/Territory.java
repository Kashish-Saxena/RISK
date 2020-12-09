import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Territory class represents a location in a continent. It includes the
 * number of armies in the territory and the owner of the territory. It also
 * contains methods to get adjacent territories and adjacent enemy territories
 * and to manipulate the number of armies in the territory.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 23, 2020
 */
public class Territory {

    private String name;
    private Player owner;
    private int numArmies;
    private List<Territory> adjacentTerritories;

    private int xPos;
    private int yPos;

    /**
     * Constructor of the Territory class. It initializes the field values and adds
     * the territory to the territory map.
     */
    public Territory(String name, int xPos, int yPos){
        this.name = name;
        this.numArmies = 0;
        this.adjacentTerritories = new ArrayList<>();
        this.xPos = xPos;
        this.yPos = yPos;
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

    public int getXPos(){
        return this.xPos;
    }

    public int getYPos(){
        return this.yPos;
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
     * Returns a list of friendly territories that are adjacent to this territory.
     * @return A list of adjacent enemy territories.
     */
    public List<Territory> getAdjacentFriendlyTerritories(){
        ArrayList<Territory> adjacentFriendlyTerritories = new ArrayList<>();
        for (Territory adj : this.adjacentTerritories) {
            if (adj.getOwner() == this.owner) {
                adjacentFriendlyTerritories.add(adj);
            }
        }
        return adjacentFriendlyTerritories;
    }

    /**
     * Updates the list of adjacent territories.
     * @param adjacentTerritories The new list of adjacent territories.
     */
    public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
        this.adjacentTerritories = adjacentTerritories;
    }
}
