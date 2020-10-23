import java.util.List;

public class Territory {

    private String name;
    private Player owner;
    private int numArmies;
    private List<Territory> adjacentTerritories;

    //Constructor
    public Territory(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return null;
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
        return null;
    }

    public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
        this.adjacentTerritories = adjacentTerritories;
    }
}
