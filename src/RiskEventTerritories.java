public class RiskEventTerritories extends RiskEvent{

    private Territory territoryFrom;
    private Territory territoryTo;

    public RiskEventTerritories(RiskGame rg, TurnPhase phase, Player currentPlayer, Territory territoryFrom, Territory territoryTo) {
        super(rg, phase, currentPlayer);
        this.territoryFrom = territoryFrom;
        this.territoryTo = territoryTo;
    }

    public Territory getTerritoryFrom() {
        return territoryFrom;
    }

    public Territory getTerritoryTo() {
        return territoryTo;
    }
}
