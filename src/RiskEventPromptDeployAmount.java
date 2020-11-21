public class RiskEventPromptDeployAmount extends RiskEvent {

    private int maxDeployAmount;

    public RiskEventPromptDeployAmount(RiskGame rg, TurnPhase phase, Player currentPlayer, int maxDeployAmount) {
        super(rg, phase, currentPlayer);
        this.maxDeployAmount = maxDeployAmount;
    }

    public int getMaxDeployAmount() {
        return maxDeployAmount;
    }
}
