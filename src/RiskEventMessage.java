/**
 * The RiskEventMessage class extends the RiskEvent class and gets the updated message to display.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 23, 2020
 */

public class RiskEventMessage extends RiskEvent{

//    private int totalDeployAmount;
//    private int AmountFromTerritories;
//    private int AmountFromContinents;

    private String message;

    /**
     * Constructor of the RiskEventMessage class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param message The updated message.
     */
    public RiskEventMessage(RiskGame rg, TurnPhase phase, Player currentPlayer, String message) {
        super(rg, phase, currentPlayer);
        this.message = message;
    }

    /**
     * Returns the message.
     * @return The message string.
     */
    public String getMessage(){
        return this.message;
    }
}
