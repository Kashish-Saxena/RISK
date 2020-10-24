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

    public String toStringTerritories(){
        String s = "";
        for (Territory t : ownedTerritories) {
            s += t.toString() + "\n";
        }
        return s;
    }

    public List<Territory> getTerritories(){
        return this.ownedTerritories;
    }

    //todo, maybe rename to getNeighbor territories
    public List<Territory> getAttackableTerritories(){
        List<Territory> attackableTerritories = new ArrayList<>();
        for (Territory ownedTerritory : ownedTerritories){
            if (ownedTerritory.getArmies() > 2 && !ownedTerritory.getAdjacentEnemyTerritories().isEmpty()) //TODO: define constant instead of using 2
                attackableTerritories.add(ownedTerritory);
        }
        return attackableTerritories;
    }

    public void addTerritory(Territory territory, Continent parentContinent){
        this.ownedTerritories.add(territory);
        if (this.ownedTerritories.containsAll(parentContinent.getTerritories())) {
            this.addContinent(parentContinent);
        }
    }

    public void removeTerritory(Territory territory, Continent parentContinent){
        if (ownedTerritories.size() == 1 && ownedTerritories.remove(territory))
            notifyObservers();

        this.removeContinent(parentContinent);
    }

    public List<Continent> getContinents(){
        return this.ownedContinents;
    }

    public void addContinent(Continent continent){
        this.ownedContinents.add(continent);
    }

    public void removeContinent(Continent continent){
        this.ownedContinents.remove(continent);
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
        return !this.getAttackableTerritories().isEmpty();
    }

    public void handleBattle(BattleEvent battle){
        if (this == battle.getDefender()) {
            this.removeTerritory(battle.getTerritory(), battle.getContinent());
        }
        else if (this == battle.getAttacker()) {
            this.addTerritory(battle.getTerritory(), battle.getContinent());
        }
    }

    public void setArmiesToPlace(int num){
        this.armiesToPlace = num;
    }

    public int getArmiesToPlace(){
        return this.armiesToPlace;
    }
}
