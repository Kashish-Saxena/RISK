/**
 * The RiskEventPlayer class extends the RiskEvent class and gets the updated game standing of a player once they
 * lose their last territory and updates the RiskFrame GUI state.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 9, 2020
 */
public class RiskEventPlayer extends RiskEvent {

    private Player player;

    /**
     * Constructor of the RiskEventPlayer class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param player A player.
     */
    public RiskEventPlayer(RiskGame rg, TurnPhase phase, Player currentPlayer, Player player) {
        super(rg, phase, currentPlayer);
        this.player = player;
    }

    /**
     * Returns the player.
     * @return thee player.
     */
    public Player getPlayer() {
        return player;
    }
}
