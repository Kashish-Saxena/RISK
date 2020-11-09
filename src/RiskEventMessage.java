public class RiskEventMessage extends RiskEvent{
    private String message;

    public RiskEventMessage(RiskGame rg, TurnPhase phase, Player currentPlayer, String message) {
        super(rg, phase, currentPlayer);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
