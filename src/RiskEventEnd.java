import java.util.List;

public class RiskEventEnd extends RiskEvent{
    private List<Player> players;

    public RiskEventEnd(RiskGame rg, TurnPhase phase, Player currentPlayer, List<Player> players) {
        super(rg, phase, currentPlayer);
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
