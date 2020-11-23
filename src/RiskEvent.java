import java.util.EventObject;

/**
 * The RiskEvent class is the superclass of other classes such as RiskEventBounds, RiskEventChooseTerritory,
 * RiskEventEnd, RiskEventTerritories and RiskEventPlayer which are responsible for updating several parts of
 * the GUI game when the model changes.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 23, 2020
 */

public abstract class RiskEvent extends EventObject {
    private TurnPhase phase;
    private Player currentPlayer;

    /**
     * Constructor of the RiskEvent class. It initializes all the field values.
     *
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @throws IllegalArgumentException if source is null.
     */
    public RiskEvent(RiskGame rg, TurnPhase phase, Player currentPlayer) {
        super(rg);
        this.phase = phase;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the TurnPhase of the Player.
     * @return Returns the TurnPhase of the Player.
     */
    public TurnPhase getPhase() {
        return phase;
    }

    /**
     * Returns the current Player.
     * @return the current Player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
