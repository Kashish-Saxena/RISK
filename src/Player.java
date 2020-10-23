import java.util.List;
import java.util.Observable;

public class Player extends Observable {

    private final String name;
    private List<Territory> ownedTerritories;
    private List<Continent> ownedContinents;
    private int gameStanding;
    private TurnPhase turnPhase;

    //Constructor
    public Player(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void printTerritories(){

    }

    public List<Territory> getTerritories(){
        return this.ownedTerritories;
    }

    public List<Territory> getAttackableTerritories(){
        return null;
    }

    public void addTerritory(Territory territory){

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


}
