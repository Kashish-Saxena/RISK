import java.util.List;

public class RiskEventChooseTerritory extends RiskEvent {
    private List<Territory> enabledTerritories;

    public RiskEventChooseTerritory(RiskGame rg, TurnPhase phase, Player currentPlayer, List<Territory> enabledTerritories) {
        super(rg, phase, currentPlayer);
        this.enabledTerritories = enabledTerritories;
    }

    public List<Territory> getEnabledTerritories() {
        return enabledTerritories;
    }
}
