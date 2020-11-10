import java.util.List;

/**
 * The RiskEventEnd class extends the RiskEvent class and gets the updated list of players remaining in the game once
 * the game is over and updates the RiskFrame GUI state.
 *
 * @author David Sciola - 101082459 and Kevin Quach - 101115704
 * @version November 9, 2020
 */

public class RiskEventEnd extends RiskEvent{
    private List<Player> players;

    /**
     * Constructor of the RiskEventEnd class. It initializes all the field values.
     * @param rg The RiskGame class.
     * @param phase The TurnPhase of the player.
     * @param currentPlayer The current player.
     * @param players A list of players in the game.
     */
    public RiskEventEnd(RiskGame rg, TurnPhase phase, Player currentPlayer, List<Player> players) {
        super(rg, phase, currentPlayer);
        this.players = players;
    }

    /**
     * Returns the list of players in the game.
     * @return the list of players in the game.
     */
    public List<Player> getPlayers() {
        return players;
    }
}
