/**
 * The RiskEventTerritories class extends the RiskEvent class and is the superclass for RiskEventDiceResults.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 9, 2020
 */

public class RiskEventTerritories extends RiskEvent{

    private Territory territoryFrom;
    private Territory territoryTo;

    /**
     * Constructor of the RiskEventTerritories class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param territoryFrom The territory to attack from.
     * @param territoryTo The territory to attack.
     */
    public RiskEventTerritories(RiskGame rg, TurnPhase phase, Player currentPlayer, Territory territoryFrom, Territory territoryTo) {
        super(rg, phase, currentPlayer);
        this.territoryFrom = territoryFrom;
        this.territoryTo = territoryTo;
    }

    /**
     * Returns the territory to attack from.
     * @return The territory to attack from.
     */
    public Territory getTerritoryFrom() {
        return territoryFrom;
    }

    /**
     * Returns the territory to attack.
     * @return the territory to attack.
     */
    public Territory getTerritoryTo() {
        return territoryTo;
    }
}
