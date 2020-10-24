import java.util.ArrayList;
import java.util.List;

public class Territory {

    private String name;
    private Player owner;
    private int numArmies;
    private List<Territory> adjacentTerritories;

    //Constructor
    public Territory(String name){
        this.name = name;
        this.numArmies = 0;
        this.adjacentTerritories = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        //does not include Player or Continent so we can choose which to group Territories by
        return name + ", " + numArmies + "armies";
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
}
