/**
 * The RiskEventSingleTerritory class extends the RiskEvent class and gets the updated territory.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 23, 2020
 */

public class RiskEventSingleTerritory extends RiskEvent{

    private Territory territory;

    /**
     * Constructor of the RiskEventSingleTerritory class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param territory A territory.
     */
    public RiskEventSingleTerritory(RiskGame rg, TurnPhase phase, Player currentPlayer, Territory territory) {
        super(rg, phase, currentPlayer);
        this.territory = territory;
    }

    /**
     * Returns the territory object.
     * @return The territory object.
     */
    public Territory getTerritory() {
        return territory;
    }
}
