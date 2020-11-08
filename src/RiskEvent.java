import java.util.EventObject;

public class RiskEvent extends EventObject {

    private Territory territoryFrom;
    private Territory territoryTo;

    public RiskEvent(RiskGame rg, Territory territoryFrom, Territory territoryTo) {
        super(rg);
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
