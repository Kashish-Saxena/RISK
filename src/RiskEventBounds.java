import java.util.List;

/**
 * The RiskEventBounds class extends the RiskEventClass and gets the numerical bounds of whatever the player is
 * requested to input (the minimum and maximum number of dice to throw or troops to move).
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 9, 2020
 */
public class RiskEventBounds extends RiskEvent {

    private int minChoice;
    private int maxChoice;

    /**
     * Constructor of the RiskEventBounds class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param minChoice The minimum acceptable integer input.
     * @param maxChoice The maximum acceptable integer input.
     */
    public RiskEventBounds(RiskGame rg, TurnPhase phase, Player currentPlayer, int minChoice, int maxChoice) {
        super(rg, phase, currentPlayer);
        this.minChoice = minChoice;
        this.maxChoice = maxChoice;
    }

    /**
     * Returns the minimum acceptable integer input.
     * @return The minimum acceptable integer input.
     */
    public int getMinChoice() {
        return minChoice;
    }

    /**
     * Returns the maximum acceptable integer input.
     * @return The maximum acceptable integer input.
     */
    public int getMaxChoice() {
        return maxChoice;
    }
}
