import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Territory {

    private String name;
    private Player owner;
    private int numArmies;
    private List<Territory> adjacentTerritories;
    private static Map<String, Territory> territoryMap = new HashMap<>();

    //Constructor
    public Territory(String name){
        this.name = name;
        this.numArmies = 0;
        this.adjacentTerritories = new ArrayList<>();
        territoryMap.put(name.toLowerCase(), this);
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        //does not include Player or Continent so we can choose which to group Territories by
        return name + ": " + numArmies + " armies";
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public Player getOwner(){
        return this.owner;
    }

    public void setArmies(int numArmies){
        this.numArmies = numArmies;
    }

    public void subtractArmies(int numArmies){
        this.numArmies -= numArmies;
    }

    public void addArmies(int numArmies){
        this.numArmies += numArmies;
    }

    public int getArmies(){
        return this.numArmies;
    }

    public List<Territory> getAdjacentTerritories(){
        return this.adjacentTerritories;
    }

    public List<Territory> getAdjacentEnemyTerritories(){
        ArrayList<Territory> adjacentEnemyTerritories = new ArrayList<>();
        for (Territory adj : this.adjacentTerritories) {
            if (adj.getOwner() != this.owner) {
                adjacentEnemyTerritories.add(adj);
            }
        }

        return adjacentEnemyTerritories;
    }

    public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
        this.adjacentTerritories = adjacentTerritories;
    }

    public static Territory getTerritoryFromString(String input) {
        return territoryMap.get(input);
    }

    public static Territory getTerritoryFromIndex(int index) {
        ArrayList<String> keys = new ArrayList<>();
        keys.addAll(territoryMap.keySet());
        return territoryMap.get(keys.get(index));
    }

    public static int numTerritories() {
        return territoryMap.size();
    }
}
