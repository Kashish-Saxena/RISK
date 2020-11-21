import java.util.List;

public class RiskEventBounds extends RiskEvent {

    private int minChoice;
    private int maxChoice;

    public RiskEventBounds(RiskGame rg, TurnPhase phase, Player currentPlayer, int minChoice, int maxChoice) {
        super(rg, phase, currentPlayer);
        this.minChoice = minChoice;
        this.maxChoice = maxChoice;
    }

    public int getMinChoice() {
        return minChoice;
    }

    public int getMaxChoice() {
        return maxChoice;
    }
}
