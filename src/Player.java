import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * The player class creates and instantiates player objects of the game. It includes the
 * lists of territories and continents owned by the player, the player's game standing and the number of armies left to
 * place. It also contains methods to get attackable territories and handle the battle.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 23, 2020
 */
public class Player extends Observable {

    private final String name;
    private List<Territory> ownedTerritories;
    private List<Continent> ownedContinents;
    private int gameStanding;
    private int armiesToPlace;

    /**
     * Constructor of the Player class. It initializes all the field values.
     *
     * @param name Name of the player.
     * @param game RiskGame.
     */
    public Player(String name, RiskGame game){
        this.name = name;
        this.ownedTerritories = new ArrayList<>();
        this.ownedContinents = new ArrayList<>();
        this.gameStanding = 0;
        this.addObserver(game);
    }

    /**
     * Returns the name of the Player.
     * @return The name of the Player.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns a string representation of the territories owned by the player.
     * @return A string representation of the territories owned by the player.
     */
    public String toStringTerritories(){
        String s = "";
        for (Territory t : ownedTerritories) {
            s += t.toString() + "\n";
        }
        return s;
    }

    /**
     * Returns a list of all territories owned by the player.
     * @return A list of territories owned by the player.
     */
    public List<Territory> getTerritories(){
        return this.ownedTerritories;
    }

    /**
     * Returns a list of territories that can be attacked by the players.
     * @return a list of attackable territories.
     */
    public List<Territory> getAttackableTerritories(){
        List<Territory> attackableTerritories = new ArrayList<>();
        for (Territory ownedTerritory : ownedTerritories){
            if (ownedTerritory.getArmies() >= RiskGame.MIN_ARMY_ATTACK && !ownedTerritory.getAdjacentEnemyTerritories().isEmpty())
                attackableTerritories.add(ownedTerritory);
        }
        return attackableTerritories;
    }

    /**
     * Adds an input territory to the list of owned territories. It also adds the continent associated with this territory
     * to the list of owned continents if it now owns all the territories in that continent.
     * @param territory The territory to be added to the list of owned territories.
     */
    public void addTerritory(Territory territory){
        Continent parentContinent = RiskMap.getContinentFromTerritory(territory);
        this.ownedTerritories.add(territory);
        territory.setOwner(this);

        if (this.ownedTerritories.containsAll(parentContinent.getTerritories())) {
            this.addContinent(parentContinent);
        }
    }

    /**
     * Removes the input territory from the list of owned territories. It notifies the observers
     * when the player has no owned territories left.
     * @param territory The territory to be removed.
     */
    public void removeTerritory(Territory territory){
        ownedTerritories.remove(territory);

        //if this Player just died, notify Game to trigger its update() method
        if (ownedTerritories.isEmpty()) {
            setChanged();
            notifyObservers();
        }

        this.removeContinent(RiskMap.getContinentFromTerritory(territory));
    }

    /**
     *  Returns a list of continents owned by the player.
     * @return A list of owned continents.
     */
    public List<Continent> getContinents(){
        return this.ownedContinents;
    }

    /**
     * Adds an input continent to the list of continents owned by the player.
     * @param continent The continent to be added to the owned continents list.
     */
    private void addContinent(Continent continent){
        this.ownedContinents.add(continent);
    }

    /**
     * Removes the input continent from the list of continents owned by the player.
     * @param continent The continent to be removed from the owned continents list.
     */
    private void removeContinent(Continent continent){
        this.ownedContinents.remove(continent);
    }

    /**
     * Updates the game standing of the player.
     * @param placing The new game standing of the player.
     */
    public void setGameStanding(int placing){
        this.gameStanding = placing;
    }

    /**
     * Returns the game standing of the player.
     * @return The game standing of the player.
     */
    public int getGameStanding(){
        return this.gameStanding;
    }

    /**
     * Determine if the player is allowed to attack.
     * @return True if the player can attack, false otherwise.
     */
    public boolean canAttack(){
        return !this.getAttackableTerritories().isEmpty();
    }

    /**
     * Updates the number of armies left to place.
     * @param num The updated number of armies left to place.
     */
    public void setArmiesToPlace(int num){
        this.armiesToPlace = num;
    }

    /**
     * Returns the number of armies left to place.
     * @return The number of armies left to place.
     */
    public int getArmiesToPlace(){
        return this.armiesToPlace;
    }

    /**
     * Returns if the player is an AI player.
     * @return if the player is an AI player.
     */
    public boolean isAI() {
        return false;
    }
}
