/**
 * The RiskEventContinent class extends the RiskEvent class and gets the updated continent that is owned by the player
 * after an attack and updates the RiskFrame GUI state.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 9, 2020
 */

public class RiskEventContinent extends RiskEvent{

    private Continent continentTo;

    /**
     * Constructor of the RiskEventContinent class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param continentTo The continent owned.
     */
    public RiskEventContinent(RiskGame rg, TurnPhase phase, Player currentPlayer, Continent continentTo) {
        super(rg, phase, currentPlayer);
        this.continentTo = continentTo;
    }

    /**
     *  Return the continent owned.
     * @return the continent owned.
     */
    public Continent getContinentTo() {
        return continentTo;
    }
}
