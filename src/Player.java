import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Player extends Observable {

    private final String name;
    private List<Territory> ownedTerritories;
    private List<Continent> ownedContinents;
    private int gameStanding;
    private int armiesToPlace;
    private TurnPhase turnPhase;

    //Constructor
    public Player(String name){
        this.name = name;
        this.ownedTerritories = new ArrayList<Territory>();
        this.ownedContinents = new ArrayList<Continent>();
    }

    public String getName(){
        return this.name;
    }

    public void printTerritories(){

    }

    public List<Territory> getTerritories(){
        return this.ownedTerritories;
    }

    //todo, maybe rename to getNeighbor territories
    public List<Territory> getAttackableTerritories(){
        return null;
    }

    public void addTerritory(Territory territory){
        this.ownedTerritories.add(territory);
    }

    public void removeTerritory(Territory territory){

    }

    public List<Continent> getContinents(){
        return null;
    }

    public void addContinent(Continent continent){

    }

    public void removeContinent(Continent continent){

    }

    public void setGameStanding(int placing){
        this.gameStanding = placing;
    }

    public int getGameStanding(){
        return this.gameStanding;
    }

    public void setTurnPhase(TurnPhase phase){
        this.turnPhase = phase;
    }

    public TurnPhase getTurnPhase(){
        return this.turnPhase;
    }

    public boolean canAttack(){
        return true;
    }

    public void handleBattle(BattleEvent battle){

    }

    public void setArmiesToPlace(int num){
        this.armiesToPlace = num;
    }

    public int getArmiesToPlace(){
        return this.armiesToPlace;
    }


}
