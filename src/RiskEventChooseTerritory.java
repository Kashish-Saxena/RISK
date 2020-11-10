import java.util.List;

/**
 * The RiskEventChooseTerritory class extends the RiskEventClass and gets the updated list of attackable territories
 * from the model and updates the RiskFrame GUI state.
 *
 * @author David Sciola - 101082459, Kevin Quach - 101115704 and Kashish Saxena - 101107204
 * @version November 9, 2020
 */

public class RiskEventChooseTerritory extends RiskEvent {
    private List<Territory> enabledTerritories;

    /**
     * Constructor of the RiskEventChooseTerritory class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param enabledTerritories The enabled territories.
     */
    public RiskEventChooseTerritory(RiskGame rg, TurnPhase phase, Player currentPlayer, List<Territory> enabledTerritories) {
        super(rg, phase, currentPlayer);
        this.enabledTerritories = enabledTerritories;
    }

    /**
     * Returns the enabled territories.
     * @return The enabled territories.
     */
    public List<Territory> getEnabledTerritories() {
        return enabledTerritories;
    }
}
