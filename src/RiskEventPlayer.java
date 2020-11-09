public class RiskEventPlayer extends RiskEvent {

    private Player player;

    public RiskEventPlayer(RiskGame rg, TurnPhase phase, Player currentPlayer, Player player) {
        super(rg, phase, currentPlayer);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
