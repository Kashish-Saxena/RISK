import java.util.*;

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
        List<Territory> attackableTerritories = new ArrayList<>();
        for (int i = 0; i < ownedTerritories.size(); i++){
            if (ownedTerritories.get(i).getArmies()>2 && ownedTerritories.get(i).getAdjacentEnemyTerritories() != null)
                attackableTerritories.add(ownedTerritories.get(i));
        }
        return attackableTerritories;
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
        if (this == battle.getDefender()) {
            this.removeTerritory(battle.getTerritory());
            this.removeContinent(battle.getContinent());
        }
        else if (this == battle.getAttacker()) {
            this.addTerritory(battle.getTerritory());

            if (this.ownedTerritories.containsAll(battle.getContinent().getTerritories())) {
                this.addContinent(battle.getContinent());
            }
        }
    }
}
