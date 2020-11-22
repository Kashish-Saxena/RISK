

public class RiskEventSingleTerritory extends RiskEvent{

    private Territory territory;

    public RiskEventSingleTerritory(RiskGame rg, TurnPhase phase, Player currentPlayer, Territory territory) {
        super(rg, phase, currentPlayer);
        this.territory = territory;
    }

    public Territory getTerritory() {
        return territory;
    }
}
